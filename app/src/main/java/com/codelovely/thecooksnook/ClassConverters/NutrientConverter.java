package com.codelovely.thecooksnook.ClassConverters;

import com.codelovely.thecooksnook.data.entities.Nutrient;

public class NutrientConverter {

    public static Nutrient convertToNutrient(com.codelovely.thecooksnook.models.restmodels.Nutrient nutrientModel) {
        Nutrient nutrient = new Nutrient();
        nutrient.setId(nutrientModel.getId());
        nutrient.setName(nutrientModel.getName());
        nutrient.setUnitName(nutrientModel.getUnitName());
        return nutrient;
    }

    public static com.codelovely.thecooksnook.models.restmodels.Nutrient convertToNutrientModel(Nutrient nutrient) {
        com.codelovely.thecooksnook.models.restmodels.Nutrient nutrientModel = new com.codelovely.thecooksnook.models.restmodels.Nutrient();
        nutrientModel.setId(nutrient.getId());
        nutrientModel.setName(nutrient.getName());
        nutrientModel.setUnitName(nutrient.getUnitName());

        return nutrientModel;
    }
}
