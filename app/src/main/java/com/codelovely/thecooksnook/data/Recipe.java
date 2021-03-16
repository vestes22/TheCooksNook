package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class Recipe {
    @PrimaryKey @ColumnInfo(name="Recipe code") long recipeCode;
    String title;
    String description;
    String instructions;
}
