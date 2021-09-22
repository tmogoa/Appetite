package com.loct.appetite.models;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.loct.appetite.util.Command;

public class Dish {
    private String dishId, dishName, description, imageId;
    private boolean hasMini, hidden;
    private double price, miniPrice;
    private FoodType foodType;

    private DatabaseReference dishNodeReference;

    //constants
    public static final String DISH_NODE = "dishes";
    public static final String DISH_IMAGES = "dish_images";
    public static final String NO_ID = "";

    public Dish(String id){
        setDishId(id);
        this.dishNodeReference = FirebaseDatabase.getInstance().getReference().child(Dish.DISH_NODE);
    }

    private void fetchDish(String id){

    }

    public Dish(boolean forInsert){
        if(forInsert){
            this.dishNodeReference = FirebaseDatabase.getInstance().getReference().child(Dish.DISH_NODE);
            dishId = dishNodeReference.push().getKey();
            hidden = false;
        }
    }

    private void generateId(){
       DatabaseReference newNode = this.dishNodeReference.push();
       this.dishId = newNode.getKey();
    }

    public Dish(){

    }

    public void removeDish(){
        this.dishNodeReference.removeValue();
    }

    /**
     * Pass Dish.NO_ID string to generate a new id
     * @param command - code to be execute on succes
     */
    public void saveDish(Command command){
        writeToFirebase("dishName", dishName);
        writeToFirebase("dishId", this.dishId);
        writeToFirebase("description", description);
        writeToFirebase("imageId", imageId);
        writeToFirebase("price", price);
        writeToFirebase("miniPrice", miniPrice);
        writeToFirebase("foodType", foodType);
        writeToFirebase("hasMini", hasMini, command);

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

    private void writeToFirebase(String path, Object data, Command command){
        DatabaseReference dishRef = this.dishNodeReference.child(this.dishId);
        dishRef.child(path).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                command.execute();
            }
        });
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

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }
}
