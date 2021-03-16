package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        tableName = "userRecipes",
        primaryKeys = {"User code", "Recipe code"},
        foreignKeys = {@ForeignKey(entity=User.class, parentColumns = "User code", childColumns = "User code"),
            @ForeignKey(entity = Recipe.class, parentColumns = "Recipe code", childColumns = "Recipe code")
        }
)
public class UserRecipe {
    @ColumnInfo(name="User code") int userId;
    @ColumnInfo(name="Recipe code") long recipeId;
}
