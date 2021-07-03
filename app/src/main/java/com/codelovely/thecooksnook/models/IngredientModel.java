package com.codelovely.thecooksnook.models;

import com.codelovely.thecooksnook.models.restmodels.FoodNutrient;

import java.util.ArrayList;
import java.util.List;

public class IngredientModel {

    private int fdcId;
    private String dataType;
    private String description;
    private float amountInRecipe;
    private String servingSizeUnit;
    private String category;
    private List<FoodNutrient> foodNutrientsPerOriginalServingSize;
    private List<FoodNutrient> foodNutrientsAdjustedForRecipe;

    public int getFdcId() {
        return fdcId;
    }

    public void setFdcId(int fdcId) {
        this.fdcId = fdcId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmountInRecipe() {
        return amountInRecipe;
    }

    public void setAmountInRecipe(float servingSizeAmount) {
        this.amountInRecipe = servingSizeAmount;
    }

    public String getServingSizeUnit() {
        return servingSizeUnit;
    }

    public void setServingSizeUnit(String servingSizeUnit) {
        this.servingSizeUnit = servingSizeUnit;
    }

    public void setCategory(String category) { this.category = category; }

    public String getCategory() { return category; }

    public List<FoodNutrient> getFoodNutrientsPerOriginalServingSize() {
        return foodNutrientsPerOriginalServingSize;
    }

    public void setFoodNutrientsPerOriginalServingSize(List<FoodNutrient> foodNutrientsPerOriginalServingSize) {
        this.foodNutrientsPerOriginalServingSize = foodNutrientsPerOriginalServingSize;
    }

    public List<FoodNutrient> getFoodNutrientsAdjustedForRecipe() {
        return foodNutrientsAdjustedForRecipe;
    }

    public void setFoodNutrientsAdjustedForRecipe() {
        List<FoodNutrient> nutrientsPerServing = new ArrayList<>();
        for (FoodNutrient nutrient : foodNutrientsPerOriginalServingSize) {
            FoodNutrient newNutrient = nutrient;
            float newAmount = (nutrient.getAmount() / 100) * amountInRecipe;
            newNutrient.setAmount(newAmount);
            nutrientsPerServing.add(newNutrient);
        }
        foodNutrientsAdjustedForRecipe = nutrientsPerServing;
    }
}
