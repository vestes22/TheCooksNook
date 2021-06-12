package com.codelovely.thecooksnook.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class Recipe {
    private @PrimaryKey(autoGenerate=true) @ColumnInfo(name="Recipe code") int recipeId;
    private String title;
    private String description;
    private @ColumnInfo(name="Number of servings") int numServings;
    private String instructions;
    private String category;

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumServings(int numServings) { this.numServings = numServings; }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setCategory(String category) { this.category = category; }

    public int getRecipeId() {
        return recipeId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getNumServings() { return numServings; }

    public String getInstructions() {
        return instructions;
    }

    public String getCategory() { return category; }
}
