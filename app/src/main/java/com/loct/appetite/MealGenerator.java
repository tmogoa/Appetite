package com.loct.appetite;

import java.util.Stack;

public class MealGenerator {
    Stack<FoodType> foodTypes;

    public MealGenerator(Stack<FoodType> foodTypes){
        this.foodTypes = foodTypes;
    }

    public boolean checkIfAlreadyGenerated(){
        return false;
    }

    public void generate(double budget, FoodType[] foodTypes){

    }

    private void collectDishes(FoodType[] foodTypes){
        for (FoodType foodType: foodTypes) {

        }
    }

}
