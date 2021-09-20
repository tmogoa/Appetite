package com.loct.appetite.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.loct.appetite.R;

public class NewDishFragment extends Fragment {

    private EditText dishName,
            disDescription, price, priceMini;
            
    private ImageView dishImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_dish, container, false);
    }
}