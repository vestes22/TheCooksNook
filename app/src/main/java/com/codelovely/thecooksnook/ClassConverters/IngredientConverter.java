package com.codelovely.thecooksnook.ClassConverters;

import com.codelovely.thecooksnook.data.entities.Ingredient;
import com.codelovely.thecooksnook.models.IngredientModel;

public class IngredientConverter {

    public static Ingredient convertToIngredient(IngredientModel model) {
        Ingredient ingredient = new Ingredient();

        ingredient.setFdcId(model.getFdcId());
        ingredient.setDataType(model.getDataType());
        ingredient.setDescription(model.getDescription());
        ingredient.setServingSizeUnit(model.getServingSizeUnit());
        ingredient.setCategory(model.getCategory());

        return ingredient;
    }

    public static IngredientModel convertToIngredientModel(Ingredient ingredient) {
        IngredientModel ingredientModel = new IngredientModel();
        ingredientModel.setFdcId(ingredient.getFdcId());
        ingredientModel.setDataType(ingredient.getDataType());
        ingredientModel.setDescription(ingredient.getDescription());
        ingredientModel.setCategory(ingredient.getCategory());
        ingredientModel.setServingSizeUnit(ingredient.getServingSizeUnit());

        return ingredientModel;
    }
}
