package com.codelovely.thecooksnook.models;

import com.codelovely.thecooksnook.models.restmodels.FoodNutrient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeModel {
    private int id; //
    private String name; //
    private String description;//
    private int numServings; //
    private List<IngredientModel> ingredients; //
    private List<FoodNutrient> totalRecipeNutrients;
    private List<FoodNutrient> recipeNutrientsPerServing;
    private String instructions; //
    private String category;  //

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumServings() {
        return numServings;
    }

    public void setNumServings(int numServings) {
        this.numServings = numServings;
    }

    public List<IngredientModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientModel> ingredients) {
        this.ingredients = ingredients;
    }

    public List<FoodNutrient> getTotalRecipeNutrients() {
        return totalRecipeNutrients;
    }

    /*
    Calculates sum of each nutrient for each ingredient in the recipe.
    This calculates total nutritional value for the entire recipe. Nutritional value for individual serving sizes
    is calculated in setRecipeNutrientsPerServing().
     */

    public void setTotalRecipeNutrients() {
        Map<Integer, FoodNutrient> totalRecipeNutrientsMap = new HashMap<>();
        System.out.println("Calling setTotalRecipeNutrients... ");
        for (IngredientModel ingredient : ingredients) {
            for (FoodNutrient foodNutrient : ingredient.getFoodNutrientsAdjustedForRecipe()) {
                System.out.println("Food nutrient in foodNutrientsAdjustedForRecipe: " + foodNutrient.getNutrient().getName() + "ID: " + foodNutrient.getNutrient().getId());
                // If nutrient already exists in the map, we will add the amount in this ingredient's nutrient to the current tally.
                if (totalRecipeNutrientsMap.containsKey(foodNutrient.getNutrient().getId())) {
                    FoodNutrient updatedFoodNutrient = totalRecipeNutrientsMap.get(foodNutrient.getNutrient().getId());
                    float newAmount = updatedFoodNutrient.getAmount() + foodNutrient.getAmount();
                    updatedFoodNutrient.setAmount(newAmount);
                    totalRecipeNutrientsMap.replace(foodNutrient.getNutrient().getId(), updatedFoodNutrient);
                }

                // Otherwise, we will add the nutrient as a new entry.
                else {
                    totalRecipeNutrientsMap.put(foodNutrient.getNutrient().getId(), foodNutrient);
                }
            }
        }

        Collection<FoodNutrient> mapValues = totalRecipeNutrientsMap.values();
        ArrayList<FoodNutrient> nutrients = new ArrayList<FoodNutrient>(mapValues);
        // TODO - delete print debugs
        for (FoodNutrient nutrient : nutrients) {
            System.out.println("Food nutrient: " + nutrient.getNutrient().getName());
        }
        this.totalRecipeNutrients = nutrients;
    }

    /*
    Calculates a recipe's nutritional value per serving size.
     */
    public void setRecipeNutrientsPerServing() {
        List<FoodNutrient> nutrientsPerServing = new ArrayList<>();
        for (FoodNutrient nutrient : totalRecipeNutrients) {
            FoodNutrient newNutrient = nutrient;
            newNutrient.setAmount(nutrient.getAmount() / numServings);
            nutrientsPerServing.add(newNutrient);
        }

        recipeNutrientsPerServing = nutrientsPerServing;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<FoodNutrient> getRecipeNutrientsPerServing() {
        return recipeNutrientsPerServing;
    }
}
