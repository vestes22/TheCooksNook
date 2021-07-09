package com.codelovely.thecooksnook.models;

import com.codelovely.thecooksnook.models.restmodels.FoodNutrient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeModel {
    private int id;
    private String name;
    private String description;
    private int numServings;
    private List<IngredientModel> ingredients;
    private List<FoodNutrient> totalRecipeNutrients;
    private List<FoodNutrient> recipeNutrientsPerServing;
    private String instructions;
    private String category;

    private Map<Integer, FoodNutrient> totalRecipeNutrientsMap;

    public RecipeModel() {
        totalRecipeNutrientsMap = new HashMap<>();
    }

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

    /*
    Calculates sum of each nutrient for each ingredient in the recipe.
    This calculates total nutritional value for the entire recipe. Nutritional value for individual serving sizes
    is calculated in setRecipeNutrientsPerServing().
     */

    public void setTotalRecipeNutrients() {

        List<IngredientModel> tempIngredients = new ArrayList<IngredientModel>();

        /*
         We create a temp list populated with new IngredientModel references to address a reference bug
         that was altering the values saved in the ingredient's foodNutrientsAdjustedForRecipe list.
         */
        for (IngredientModel ingredient : ingredients) {
            IngredientModel newIngredient = new IngredientModel();
            newIngredient.setServingSizeUnit(ingredient.getServingSizeUnit());
            newIngredient.setAmountInRecipe(ingredient.getAmountInRecipe());
            newIngredient.setCategory(ingredient.getCategory());
            newIngredient.setDataType(ingredient.getDataType());
            newIngredient.setDescription(ingredient.getDescription());
            newIngredient.setFdcId(ingredient.getFdcId());
            newIngredient.setFoodNutrientsPerOriginalServingSize(ingredient.getFoodNutrientsPerOriginalServingSize());
            newIngredient.setFoodNutrientsAdjustedForRecipe();
            tempIngredients.add(newIngredient);
        }

        for (IngredientModel ingredient : tempIngredients) {

            for (FoodNutrient foodNutrient : ingredient.getFoodNutrientsAdjustedForRecipe()) {
                // If nutrient already exists in the map, we will add the amount in this ingredient's nutrient to the current tally.
                int key = foodNutrient.getNutrient().getId();

                updateMap(key, foodNutrient);

            } // End Food Nutrient for loop.

        } // End ingredient for loop

        Collection<FoodNutrient> mapValues = totalRecipeNutrientsMap.values();
        this.totalRecipeNutrients = new ArrayList<>(mapValues);
    }


    private void updateMap(int key, FoodNutrient foodNutrient) {

        if (totalRecipeNutrientsMap.containsKey(key)) {
            float newAmount;
            FoodNutrient updatedFoodNutrient = totalRecipeNutrientsMap.get(key);
            if (updatedFoodNutrient == null) {
                newAmount = foodNutrient.getAmount();
            }
            else {
                newAmount = updatedFoodNutrient.getAmount() + foodNutrient.getAmount();
            }
            foodNutrient.setAmount(newAmount);
            totalRecipeNutrientsMap.replace(key, foodNutrient);
        }

        // Otherwise, we will add the nutrient as a new entry.
        else {
            totalRecipeNutrientsMap.put(key, foodNutrient);
        }
    }

    /*
    Calculates a recipe's nutritional value per serving size.
     */
    public void setRecipeNutrientsPerServing() {
        List<FoodNutrient> nutrientsPerServing = new ArrayList<>();
        for (FoodNutrient nutrient : totalRecipeNutrients) {
            nutrient.setAmount(nutrient.getAmount() / numServings);
            nutrientsPerServing.add(nutrient);
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
