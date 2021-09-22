package com.loct.appetite.models;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Dish {
    private String dishId, dishName, description, imageId;
    private boolean hasMini, hidden;
    private double price, miniPrice;

    private String foodType;

    private DatabaseReference dishNodeReference;

    //constants
    public static final String DISH_NODE = "dishes";
    public static final String DISH_IMAGES = "dish_images";
    public static final String NO_ID = "";
    public static final String DISH_ID = "DISH_ID";

    public Dish(String id){
        setDishId(id);
        this.dishNodeReference = FirebaseDatabase.getInstance().getReference().child(Dish.DISH_NODE);
        hidden = false;
    }


    private void generateId(){
       DatabaseReference newNode = this.dishNodeReference.push();
       this.dishId = newNode.getKey();
    }

    public void removeDish(){
        this.dishNodeReference.removeValue();
    }

    /**
     * Pass Dish.NO_ID string to generate a new id
     * The Id of the dish should be set. pass Dish.NO_ID to generate a new Id for adding new dish
     */
    public void saveDish(){
        writeToFirebase("dishName", dishName);
        writeToFirebase("dishId", this.dishId);
        writeToFirebase("description", description);
        writeToFirebase("imageId", imageId);
        writeToFirebase("price", price);
        writeToFirebase("miniPrice", miniPrice);
        writeToFirebase("hidden", hidden);
        writeToFirebase("foodType", foodType);
        writeToFirebase("hasMini", hasMini);

    }

    /**
     * Make sure that the dish id is set
     */
    public void hideDish(){
        this.setHidden(true);
        writeToFirebase("hidden", hidden);
    }

    /**
     * The dish Id should be set
     */
    public void unhideDish(){
        setHidden(false);
        writeToFirebase("hidden", hidden);
    }

    public String getDishId() {
        return dishId;
    }

    /**
     * Writes to firebase on behalf of the dish class
     * @param path - the database path of the child node under the dish node
     * @param data - the object to write at the path
     */
    private void writeToFirebase(String path, Object data){
        DatabaseReference dishRef = this.dishNodeReference.child(this.dishId);
        dishRef.child(path).setValue(data);
    }

    public void setDishId(String dishId) {
        if(dishId.equals("")){
            generateId();
            return;
        }

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

    public static Dish setUpFromSnapshot(DataSnapshot ss){
        Dish dish = new Dish(ss.child("dishId").toString());
        dish.setImageId(ss.child("imageId").getValue().toString());
        dish.setDishName(ss.child("dishName").getValue().toString());
        dish.setHasMini(Boolean.parseBoolean(ss.child("hasMini").getValue().toString()));
        dish.setHidden(Boolean.parseBoolean(ss.child("hidden").getValue().toString()));
        dish.setPrice(Double.parseDouble(ss.child("price").getValue().toString()));
        dish.setMiniPrice(Double.parseDouble(ss.child("miniPrice").getValue().toString()));
        dish.setDescription(ss.child("description").getValue().toString());

        return dish;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageId() {
        return imageId;
    }

    /**
     * Sets the image id of the dish
     * @param imageId - the image id should be Dish.DISH_ID if you want the image id to be the dishs id.
     *                Ensure that you have called setId before call this method
     */
    public void setImageId(String imageId) {
        if(imageId.equals(DISH_ID)){
            this.imageId = this.dishId;
            return;
        }
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

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
}
