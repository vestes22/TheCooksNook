package com.codelovely.thecooksnook.data;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity (
        tableName = "recipeFoods",
        primaryKeys = {"Recipe code", "Food code"},
        foreignKeys = {@ForeignKey(entity=Recipe.class, parentColumns = "Recipe code", childColumns = "Recipe code"),
                @ForeignKey(entity=MainFoodDesc.class, parentColumns="Food code", childColumns = "Food code"),}
)
public class RecipeFood {
    @ColumnInfo(name="Recipe code") int recipeCode;
    @ColumnInfo(name="Food code") int foodCode;
}
