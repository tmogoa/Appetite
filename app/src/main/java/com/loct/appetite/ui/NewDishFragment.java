package com.loct.appetite.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loct.appetite.R;
import com.loct.appetite.models.Dish;

public class NewDishFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    //use this to check when saving to db
    private boolean hasMini = false;

    private EditText editDishName, editDescription, editPrice, editPriceMini;
    private TextView txtPriceMini;
    private ImageView imgAddImg;
    private CheckBox cbHasMini;
    private Spinner spinner;
    private String foodType, imageId = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_dish, container, false);

        txtPriceMini = v.findViewById(R.id.txt_price_mini);
        editPriceMini = v.findViewById(R.id.edit_price_mini);
        cbHasMini = v.findViewById(R.id.check_has_mini);
        imgAddImg = v.findViewById(R.id.img_add_img);

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

        return v;
    }

    public void selectImage() {
        //open and select image, you may do a preview using the same view
        //we implement this later

    }

    public void toggleMini() {
        hasMini = cbHasMini.isChecked();
        txtPriceMini.setVisibility(hasMini ? View.VISIBLE : View.INVISIBLE);
        editPriceMini.setVisibility(hasMini ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CharSequence cs = (CharSequence) parent.getItemAtPosition(position);
        //get as string
        foodType = cs.toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //doing nothing
    }

    public void saveDish(View view){

        if(foodType.equals("")){
            Toast.makeText(getContext(), "Select the food type", Toast.LENGTH_SHORT).show();
            return;
        }

        Dish newDish = new Dish(Dish.NO_ID);
        newDish.setDishName(getStringFromUi(editDishName));
        newDish.setDescription(getStringFromUi(editDescription));
        newDish.setPrice(getDouble(editPrice));
        newDish.setMiniPrice((hasMini)? getDouble(editPriceMini): newDish.getPrice());
        newDish.setHasMini(hasMini);
        newDish.setFoodType(foodType);
        newDish.setImageId(Dish.DISH_ID);

        newDish.saveDish();

        //move to the next screen here

    }

    private String getStringFromUi(EditText view){
        return view.getText().toString().trim();
    }

    private Double getDouble(EditText view){
        return Double.parseDouble(getStringFromUi(view));
    }


}