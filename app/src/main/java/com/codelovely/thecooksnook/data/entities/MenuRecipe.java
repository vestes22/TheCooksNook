package com.codelovely.thecooksnook.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

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


    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getMenuId() {
        return menuId;
    }

    public int getRecipeId() {
        return recipeId;
    }

}
