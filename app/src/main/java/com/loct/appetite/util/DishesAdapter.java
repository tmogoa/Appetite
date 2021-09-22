package com.loct.appetite.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loct.appetite.R;
import com.loct.appetite.models.Dish;

import java.util.List;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.ViewHolder> {

    private List<Dish> dishes;

    public  DishesAdapter(List<Dish> dishes){
        this.dishes = dishes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater li = LayoutInflater.from(context);

        View root = li.inflate(R.layout.dish_view_item, parent, false);

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dish dish =  dishes.get(position);

        //need to set image too
        //holder.imgDishDisplay.setImageDrawable();

        holder.txtDishNameDisplay.setText(dish.getDishName());
        holder.txtFoodTypeDisplay.setText(dish.getFoodType().toString());
        holder.txtDescDisplay.setText(dish.getDescription());
        holder.txtPriceDisplay.setText(Double.toString(dish.getPrice()));
        holder.txtPriceMiniDisplay.setText(Double.toString(dish.getMiniPrice()));
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgDishDisplay, imgEye, imgClose;
        public TextView txtDishNameDisplay, txtFoodTypeDisplay,
                txtDescDisplay, txtPriceDisplay, txtPriceMiniDisplay;

        public ViewHolder(View v){
            super(v);

            imgDishDisplay = v.findViewById(R.id.img_dish_display);
            txtDishNameDisplay = v.findViewById(R.id.txt_dish_name_display);
            txtFoodTypeDisplay = v.findViewById(R.id.txt_food_type_display);
            txtDescDisplay = v.findViewById(R.id.txt_desc_display);
            txtPriceDisplay = v.findViewById(R.id.txt_price_display);
            txtPriceMiniDisplay = v.findViewById(R.id.txt_price_mini_display);

        }
    }
}
