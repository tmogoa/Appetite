package com.loct.appetite.ui;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.loct.appetite.R;
import com.loct.appetite.models.Dish;
import com.loct.appetite.models.FoodType;
import com.loct.appetite.util.Command;

@SuppressWarnings("deprecation")
public class NewDishFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static final int GALLERY_REQ = 10001;

    public static final String REF_DISH_IMAGES = "dish_images";

    private StorageReference storageReference;

    private FoodType foodType = FoodType.carbohydrate;

    private EditText editDishName, editDescription, editPrice, editPriceMini;
    private TextView txtPriceMini;
    private ImageView imgAddImg;
    private CheckBox cbHasMini;
    private Spinner spinner;
    private Uri uriDishImage;
    private Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_dish, container, false);

        storageReference = FirebaseStorage.getInstance().getReference();

        txtPriceMini = v.findViewById(R.id.txt_price_mini);

        editPriceMini = v.findViewById(R.id.edit_price_mini);
        editDishName = v.findViewById(R.id.edit_dish_name);
        editDescription = v.findViewById(R.id.edit_description);
        editPrice = v.findViewById(R.id.edit_price);
        cbHasMini = v.findViewById(R.id.check_has_mini);
        imgAddImg = v.findViewById(R.id.img_add_img);
        btnSave = v.findViewById(R.id.btn_save);

        spinner = v.findViewById(R.id.spinner_food_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.food_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        cbHasMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMini();
            }
        });

        imgAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveDish();
            }
        });

        return v;
    }


    private void handleSaveDish(){
        StorageReference dishImageRef = storageReference.child(REF_DISH_IMAGES).child(uriDishImage.getLastPathSegment());

        dishImageRef.putFile(uriDishImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Dish dish = new Dish(true);
                        dish.setDishName(editDishName.getText().toString());
                        dish.setDescription(editDescription.getText().toString());
                        dish.setPrice(Double.parseDouble(editPrice.getText().toString()));
                        dish.setMiniPrice(Double.parseDouble(editPriceMini.getText().toString()));
                        dish.setHasMini(cbHasMini.isChecked());
                        dish.setImageId(uri.toString());
                        dish.setFoodType(foodType);

                        class onSuccess implements Command {
                            public void execute(){
                                Toast.makeText(requireContext(), "Dish saved", Toast.LENGTH_SHORT);
                            }
                        }

                        dish.saveDish(new onSuccess());
                    }
                });
            }
        });
    }


    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQ);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQ && resultCode == RESULT_OK){
            uriDishImage = data.getData();
            imgAddImg.setImageURI(uriDishImage);
        }
    }

    public void toggleMini() {
        int visibility = cbHasMini.isChecked() ? View.VISIBLE : View.INVISIBLE;
        txtPriceMini.setVisibility(visibility);
        editPriceMini.setVisibility(visibility);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CharSequence cs = (CharSequence) parent.getItemAtPosition(position);
        //get as string
        //cs.toString();
        switch (cs.toString()){
            case "Carbohydrate":
                foodType = FoodType.carbohydrate;
                break;
            case "Protein":
                foodType = FoodType.protein;
                break;
            case "Vitamin":
                foodType = FoodType.vitamin;
                break;
            case "Drink":
                foodType = FoodType.drink;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //doing nothing
    }
}