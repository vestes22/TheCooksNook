package com.codelovely.thecooksnook.ClassConverters;

import com.codelovely.thecooksnook.data.entities.Recipe;
import com.codelovely.thecooksnook.models.RecipeModel;

public class RecipeConverter {

    public static RecipeModel convertToRecipeModel(Recipe recipe) {
        RecipeModel recipeModel = new RecipeModel();
        recipeModel.setCategory(recipe.getCategory());
        recipeModel.setDescription(recipe.getDescription());
        recipeModel.setName(recipe.getTitle());
        recipeModel.setNumServings(recipe.getNumServings());
        recipeModel.setInstructions(recipe.getInstructions());
        recipeModel.setId(recipe.getRecipeId());
        return recipeModel;
    }

    public static Recipe convertToRecipe(RecipeModel model) {
        Recipe recipe = new Recipe();
        recipe.setTitle(model.getName());
        recipe.setDescription(model.getDescription());
        recipe.setNumServings(model.getNumServings());
        recipe.setInstructions(model.getInstructions());
        recipe.setCategory(model.getCategory());
        return recipe;
    }
}
