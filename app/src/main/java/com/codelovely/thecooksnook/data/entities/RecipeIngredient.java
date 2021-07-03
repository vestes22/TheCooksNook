package com.codelovely.thecooksnook.data.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;


@Entity (
        tableName = "recipeIngredients",
        primaryKeys = {"Recipe code", "FDC ID"},
        foreignKeys = {@ForeignKey(entity=Recipe.class, parentColumns = "Recipe code", childColumns = "Recipe code"),
        @ForeignKey(entity=Ingredient.class, parentColumns = "FDC ID", childColumns = "FDC ID")}
)
public class RecipeIngredient {
    private @ColumnInfo(name="Recipe code") int recipeId;
    private @ColumnInfo(name="FDC ID") int fdcId;
    private float amount;
    private @NonNull @ColumnInfo(name="Data type") String dataType;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getFdcId() {
        return fdcId;
    }

    public void setFdcId(int fdcId) {
        this.fdcId = fdcId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
