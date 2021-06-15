package com.codelovely.thecooksnook.models;

import java.util.List;

public class Recipe {
    private int id;
    private String name;
    private String description;
    private int numServings;
    private List<Ingredient> ingredients;
    private List<Nutrient> nutrients;
    private String instructions;
    private String category;

    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) { this.description = description; }

    public void setNumServings(int numServings) { this.numServings = numServings; }

    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }

    public void setInstructions(String instructions) { this.instructions = instructions; }

    public void setCategory(String category) { this.category = category; }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public int getNumServings() { return numServings; }

    public List<Ingredient> getIngredients() { return ingredients; }

    public String getInstructions() { return instructions; }

    public String getCategory() { return category; }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    // TODO - write method calculateNutrients.
    // This method will iterate through all ingredients in the recipe's list and add the nutrients.
    // The total value of each nutrient will be stored in the recipe's nutrients list.
    // We will need to calculate the nutrients each time we query a recipe object from the database.
    // We need to consider two things: total nutrients, and total nutrients per serving size.
    // TODO - accessing nutrients may be faster if we store them in a dictionary or hash instead (key/value = nutrientId/nutrientObject). Just for the recipe, we probs can keep then in standard lists in the Ingredient class.
    // Question - total nutrients in the recipe will have fields for foodId, portion units, etc. but we don't care about those for TOTAL recipe nutrients. Do we just keep the values null?
}
