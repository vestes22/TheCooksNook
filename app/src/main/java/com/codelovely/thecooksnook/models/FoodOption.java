package com.codelovely.thecooksnook.models;

import java.util.List;

public class FoodOption {

    private int foodId;
    private String foodName;
    private List<FoodPortion> foodPortions;
    private FoodPortion selectedPortion;
    private int qty = 0;

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public void setFoodPortion(List<FoodPortion> foodPortions) {
        this.foodPortions = foodPortions;
        selectedPortion = foodPortions.get(0);
    }

    public void setSelectedPortion(FoodPortion selectedPortion) { this.selectedPortion = selectedPortion; }

    public void setQty(int qty) { this.qty = qty; }

    public int getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public List<FoodPortion> getFoodPortions() {
        return foodPortions;
    }

    public FoodPortion getSelectedPortion() { return selectedPortion; }

    public int getQty() { return qty; }

}
