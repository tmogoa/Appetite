package com.loct.appetite.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Dish {
    private String dishId, dishName, description, imageId;
    private boolean hasMini;
    private double price, miniPrice;

    private DatabaseReference dishNodeReference;
    private FirebaseAuth authenticatedUser;
    private StorageReference firebaseStorage;

    //constants
    public static final String DISH_NODE = "dishes";
    public static final String DISH_IMAGES = "dish_images";

    public Dish(FirebaseAuth authenticatedUser){

        this.authenticatedUser = authenticatedUser;
        this.firebaseStorage = FirebaseStorage.getInstance().getReference().child(Dish.DISH_IMAGES);
        this.dishNodeReference = FirebaseDatabase.getInstance().getReference().child(Dish.DISH_NODE);

    }

    public void removeDish(){

    }

    public void saveDish(){

    }

    public void hideDish(){

    }

    public void unhideDish(){

    }

}
