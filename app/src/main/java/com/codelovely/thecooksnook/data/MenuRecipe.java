package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (
        tableName = "menuRecipes",
        primaryKeys = {"Menu code", "Recipe code"},
        foreignKeys = {
                @ForeignKey(entity = Menu.class, parentColumns = "Menu code", childColumns = "Menu code"),
                @ForeignKey(entity = Recipe.class, parentColumns = "Recipe code", childColumns = "Recipe code")
        }
)
public class MenuRecipe {
    private @ColumnInfo(name="Menu code") int menuId;
    private @ColumnInfo(name="Recipe code") int recipeId;
    private @ColumnInfo(name="Recipe date") Date recipeDate;
    private @ColumnInfo(name="Recipe category") String recipeCategory;

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setRecipeDate(Date recipeDate) {
        this.recipeDate = recipeDate;
    }

    public void setRecipeCategory (String recipeCategory) {
        this.recipeCategory = recipeCategory;
    }

    public int getMenuId() {
        return menuId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public Date getRecipeDate() {
        return recipeDate;
    }

    public String recipeCategory() {
        return recipeCategory;
    }
}
