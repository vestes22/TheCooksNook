package com.codelovely.thecooksnook.data.entities;

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
    private @ColumnInfo(name="User code") int userId;
    private @ColumnInfo(name="Recipe code") int recipeId;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getUserId() {
        return userId;
    }

    public int getRecipeId() {
        return recipeId;
    }
}
