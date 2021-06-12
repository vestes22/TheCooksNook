package com.codelovely.thecooksnook.models;

import java.util.List;

public class Recipe {
    private int id;
    private String name;
    private String description;
    private int numServings;
    private List<Ingredient> ingredients;
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
}
