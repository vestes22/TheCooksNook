package com.codelovely.thecooksnook.models;

import com.codelovely.thecooksnook.Nutrients;
import com.codelovely.thecooksnook.models.restmodels.FoodNutrient;
import com.codelovely.thecooksnook.models.restmodels.Nutrient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngredientModel {

    private int fdcId;
    private String dataType;
    private String description;
    private float amountInRecipe;
    private String servingSizeUnit;
    private String category;
    private List<FoodNutrient> foodNutrientsPerOriginalServingSize;
    private List<FoodNutrient> foodNutrientsAdjustedForRecipe;
    private Map<Integer, FoodNutrient> foodNutrientsMap;

    public IngredientModel() {
        foodNutrientsMap = new HashMap<>();

        for (Integer nutrientId : Nutrients.getNutrientIds()) {
            foodNutrientsMap.put(nutrientId, null);
        }
    }

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

        Nutrient energyNutrient = new Nutrient();

        energyNutrient.setId(1008);
        energyNutrient.setName("Energy");
        energyNutrient.setUnitName("kCal");

        int energyId = 1008;
        int energyAtwaterGeneralId = 2047;
        int energyAtwaterSpecificId = 2048;

        FoodNutrient energyValue = null;
        FoodNutrient energyAtwaterGeneralValue = null;
        FoodNutrient energyAtwaterSpecificValue = null;

        for (FoodNutrient foodNutrient : foodNutrientsPerOriginalServingSize) {
            int nutrientId = foodNutrient.getNutrient().getId();

            if (nutrientId == energyId) {
                energyValue = foodNutrient;
            }
            else if (nutrientId == energyAtwaterGeneralId) {
                energyAtwaterGeneralValue = foodNutrient;
            }

            else if (nutrientId == energyAtwaterSpecificId) {
                energyAtwaterSpecificValue = foodNutrient;
            }
            else {
                foodNutrientsMap.replace(foodNutrient.getNutrient().getId(), foodNutrient);
            }
        }

        if (energyAtwaterSpecificValue != null) {
            energyAtwaterSpecificValue.setNutrient(energyNutrient);
            energyValue = energyAtwaterSpecificValue;
        }
        else if (energyAtwaterGeneralValue != null) {
            energyAtwaterGeneralValue.setNutrient(energyNutrient);
            energyValue = energyAtwaterGeneralValue;
        }

        if (energyValue != null) {
            foodNutrientsMap.replace(energyId, energyValue);
        }
    }

    public boolean checkForMissingData() {
        for (Map.Entry foodNutrient : foodNutrientsMap.entrySet()) {
            if (foodNutrient.getValue() == null) {
                System.out.println(foodNutrient.getKey() + " is returning null." );
                return true;
            }
        }
        return false;
    }

    public List<FoodNutrient> getFoodNutrientsAdjustedForRecipe() {
        return foodNutrientsAdjustedForRecipe;
    }

    public void setFoodNutrientsAdjustedForRecipe() {
        List<FoodNutrient> nutrientsPerServing = new ArrayList<>();
        for (Map.Entry mapEntry : foodNutrientsMap.entrySet()) {
            if (mapEntry.getValue() != null) {
                FoodNutrient nutrient = (FoodNutrient) mapEntry.getValue();
                FoodNutrient newNutrient = new FoodNutrient();
                newNutrient.setAmount(nutrient.getAmount());
                newNutrient.setFdcId(nutrient.getFdcId());
                newNutrient.setId(nutrient.getId());
                newNutrient.setNutrient(nutrient.getNutrient());
                float newAmount = (newNutrient.getAmount() / 100) * amountInRecipe;
                newNutrient.setAmount(newAmount);
                nutrientsPerServing.add(newNutrient);
            }
        }
        foodNutrientsAdjustedForRecipe = nutrientsPerServing;
    }
}
