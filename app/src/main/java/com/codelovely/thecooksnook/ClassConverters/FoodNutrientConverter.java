package com.codelovely.thecooksnook.ClassConverters;

import com.codelovely.thecooksnook.data.entities.FoodNutrient;

public class FoodNutrientConverter {

    public static FoodNutrient convertToFoodNutrient(com.codelovely.thecooksnook.models.restmodels.FoodNutrient foodNutrientModel) {
        FoodNutrient foodNutrient = new FoodNutrient();
        foodNutrient.setAmount(foodNutrientModel.getAmount());
        foodNutrient.setNutrientId(foodNutrientModel.getNutrient().getId());
        return foodNutrient;
    }

    public static com.codelovely.thecooksnook.models.restmodels.FoodNutrient convertToFoodNutrientModel(FoodNutrient foodNutrient) {
        com.codelovely.thecooksnook.models.restmodels.FoodNutrient foodNutrientModel = new com.codelovely.thecooksnook.models.restmodels.FoodNutrient();
        foodNutrientModel.setFdcId(foodNutrient.getFdcId());
        foodNutrientModel.setAmount(foodNutrient.getAmount());
        return foodNutrientModel;
    }
}
