package com.loct.appetite.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.loct.appetite.R;
import com.loct.appetite.models.Dish;
import com.loct.appetite.models.FoodType;
import com.loct.appetite.util.DishesAdapter;

import java.util.ArrayList;
import java.util.List;

public class DishesFragment extends Fragment {

    private ImageView imgSearch;
    private EditText editDishSearch;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dishes, container, false);

        imgSearch = v.findViewById(R.id.img_search);
        editDishSearch = v.findViewById(R.id.edit_dish_search);

        setViewListeners();

        RecyclerView recyclerView = v.findViewById(R.id.recycler_dishes);

        List<Dish> dishes = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Dish dish = new Dish();
            dish.setDishName("Fish");
            dish.setFoodType(FoodType.protein);
            dish.setDescription("Animal protein, rich in omega 3. Originates from the lake.");
            dish.setPrice(200);
            dish.setMiniPrice(90);

            dishes.add(dish);
        }

        DishesAdapter dishesAdapter = new DishesAdapter(dishes);
        recyclerView.setAdapter(dishesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return v;
    }

    private void setViewListeners(){
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = editDishSearch.getVisibility();
                editDishSearch.setVisibility(visibility == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
            }
        });
    }
}