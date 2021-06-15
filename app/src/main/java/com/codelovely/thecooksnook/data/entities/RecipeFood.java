package com.codelovely.thecooksnook.data.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.codelovely.thecooksnook.data.MainFoodDesc;

@Entity (
        tableName = "recipeFoods",
        primaryKeys = {"Recipe code", "Food code"},
        foreignKeys = {@ForeignKey(entity=Recipe.class, parentColumns = "Recipe code", childColumns = "Recipe code"),
                @ForeignKey(entity= MainFoodDesc.class, parentColumns="Food code", childColumns = "Food code"),
                @ForeignKey(entity=FoodPortionDesc.class, parentColumns = "Portion code", childColumns = "Portion code")}
)
public class RecipeFood {
    private @ColumnInfo(name="Recipe code") int recipeId;
    private @ColumnInfo(name="Food code") int foodId;
    private @ColumnInfo(name="Portion code") int portionCode;
    private float quantity;

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public void setPortionCode(int portionCode) {
        this.portionCode = portionCode;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int getFoodId() {
        return foodId;
    }

    public int getPortionCode() {
        return portionCode;
    }

    public float getQuantity() {
        return quantity;
    }
}
