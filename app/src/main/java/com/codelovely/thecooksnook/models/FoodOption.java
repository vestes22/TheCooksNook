package com.codelovely.thecooksnook.models;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FoodOption {

    private int foodId;
    private String foodName;
    private List<FoodPortion> foodPortions;

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public void setFoodPortion(List<FoodPortion> foodPortions) {
        this.foodPortions = foodPortions;
    }

    public int getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public List<FoodPortion> getFoodPortions() {
        return foodPortions;
    }

}
