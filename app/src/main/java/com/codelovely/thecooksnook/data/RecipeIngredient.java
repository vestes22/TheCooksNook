package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        tableName = "recipeIngredients",
        primaryKeys = {"Recipe code", "Food code", "Ingredient code"},
        foreignKeys = {@ForeignKey(entity=Recipe.class, parentColumns = "Recipe code", childColumns = "Recipe code"),
            @ForeignKey(entity=MainFoodDesc.class, parentColumns="Food code", childColumns = "Food code"),
            @ForeignKey(entity=IngredNutValue.class, parentColumns = "Ingredient code", childColumns = "Ingredient code")
        }
)
public class RecipeIngredient {
    @ColumnInfo(name="Recipe code") int recipeId;
    @ColumnInfo(name="Food code") int foodCode;
    @ColumnInfo(name="Ingredient code") int ingredientCode;

    //Do I add columns for the amount and unit of measure? Methinks not, because the Amount and Units can be found in portion table.
}
