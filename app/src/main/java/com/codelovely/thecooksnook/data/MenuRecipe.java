package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
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
    @ColumnInfo(name="Menu code") int menuCode;
    @ColumnInfo(name="Recipe code") int recipeCode;
    @ColumnInfo(name="Recipe date") Date recipeDate;
    @ColumnInfo(name="Recipe category") String recipeCategory;
}
