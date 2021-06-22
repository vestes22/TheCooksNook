package com.codelovely.thecooksnook.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipe {
    private int id;
    private String name;
    private String description;
    private int numServings;
    private List<Ingredient> ingredients;
    private List<Nutrient> recipeNutrientsPerServing;
    private String instructions;
    private String category;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumServings(int numServings) {
        this.numServings = numServings;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getNumServings() {
        return numServings;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getCategory() {
        return category;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    /*
    Each recipe has a list of its ingredients.
    Each ingredient has a list of its nutrients.
    To calculate the Nutrition Facts for a serving size,
    We will add the nutrient values for each ingredient, to get the nutrients for the whole recipe.
    Then divide by the number of servings.
     */
    public void setRecipeNutrientsPerServing(List<Ingredient> ingredients) {
        Map<Integer, Nutrient> nutrientMap = new HashMap<>();

        // We will consider the nutritional values for each ingredient.
        for (Ingredient ingredient : ingredients) {
            // For a particular ingredient, we need to iterate through each nutrient and add its value to the collective recipe nutrients.
            for (Nutrient nutrient : ingredient.getNutrients()) {
                if (nutrientMap.containsKey(nutrient.getNutrientCode())) {
                    Nutrient newNutrient = nutrientMap.get(nutrient.getNutrientCode());
                    newNutrient.setNutrientValue(newNutrient.getNutrientValue() + nutrient.getNutrientValue());
                    nutrientMap.replace(nutrient.getNutrientCode(), newNutrient);
                } else {
                    nutrientMap.put(nutrient.getNutrientCode(), nutrient);
                }
            }
        }

        // Now we have a HashMap containing all of the collective nutrients for each ingredient.
        // We used a map for fast access times when adding the nutrients, but it will be easier to work with a list for the rest
        // of our app. So we will convert our HashMap to an ArrayList of nutrients.
        Collection<Nutrient> mapValues = nutrientMap.values();
        ArrayList<Nutrient> nutrients = new ArrayList<Nutrient>(mapValues);

        for (Nutrient nutrient : nutrients) {
            nutrient.setNutrientValue(nutrient.getNutrientValue() / numServings);
        }

        recipeNutrientsPerServing = nutrients;
    }

    public List<Nutrient> getRecipeNutrientsPerServing() {
        return recipeNutrientsPerServing;
    }
}
