package com.codelovely.thecooksnook.models;

import com.codelovely.thecooksnook.models.restmodels.FoodNutrient;
import com.codelovely.thecooksnook.models.restmodels.Nutrient;

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
    private String category;

    private Map<Integer, FoodNutrient> totalRecipeNutrientsMap = new HashMap<>();

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
        Nutrient energyNutrient = new Nutrient();
        energyNutrient.setId(1008);
        energyNutrient.setName("Energy");
        energyNutrient.setUnitName("kCal");

        int energyId = 1008;
        int energyAtwaterGeneralId = 2047;
        int energyAtwaterSpecificId = 2048;

        for (IngredientModel ingredient : ingredients) {

            FoodNutrient energyValue = null;
            FoodNutrient energyAtwaterGeneralValue = null;
            FoodNutrient energyAtwaterSpecificValue = null;

            for (FoodNutrient foodNutrient : ingredient.getFoodNutrientsAdjustedForRecipe()) {
                // If nutrient already exists in the map, we will add the amount in this ingredient's nutrient to the current tally.
                int key = foodNutrient.getNutrient().getId();

                // Calories are calculated weirdly. The database has 3 three different types of calories - but we only want to use one.
                // If this is a calorie nutrient, we'll mark it and skip it so we can deal with it later.
                if (key == energyId) {
                    energyValue = foodNutrient;
                }
                else if (key == energyAtwaterSpecificId) {
                    energyAtwaterSpecificValue = foodNutrient;
                }
                else if (key == energyAtwaterGeneralId) {
                    energyAtwaterGeneralValue = foodNutrient;
                }
                else {
                    updateMap(key, foodNutrient);
                }
            } // End Food Nutrient for loop.

            /*
             Dealing with the calories..
             Food Data Central has 4 different ways of calculating and storing calories.
             Not all foods contain values for all four data types. In fact, most do not. Therefore, we must choose the most accurate value out of the available data to represent the calories for our ingredient.
             Order of priority:
             1) Atwater Specific value energy - Most accurate, and we want to use this value if possible to represent the calories for this ingredient.
             2) Atwater General value energy
             3) Energy (kCal)
             4) Energy (kJ) - we only want to use this if the energy is not already provided in kCals. Otherwise, we can convert this to kCal and use it.

             Whichever value we choose to represent the number of calories for this particular ingredient, we convert it to an Energy (kCal) Nutrient, which is the standardized format we
             are choosing to represent calories for our recipe.
             */

            if (energyAtwaterSpecificValue != null) {
                energyAtwaterSpecificValue.setNutrient(energyNutrient);
                energyValue = energyAtwaterSpecificValue;
            }
            else if (energyAtwaterGeneralValue != null) {
                energyAtwaterGeneralValue.setNutrient(energyNutrient);
                energyValue = energyAtwaterGeneralValue;
            }

            if (energyValue != null) {
                updateMap(energyId, energyValue);
            }
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
