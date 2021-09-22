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
    private FoodType foodType;

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

    public Dish(){

    }

    public void removeDish(){

    }

    public void saveDish(){

    }

    public void hideDish(){

    }

    public void unhideDish(){

    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public boolean isHasMini() {
        return hasMini;
    }

    public void setHasMini(boolean hasMini) {
        this.hasMini = hasMini;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMiniPrice() {
        return miniPrice;
    }

    public void setMiniPrice(double miniPrice) {
        this.miniPrice = miniPrice;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }
}
