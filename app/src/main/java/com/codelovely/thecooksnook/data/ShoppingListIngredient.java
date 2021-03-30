package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity (
        tableName = "shoppingListIngredients",
        primaryKeys = {"Shopping list code", "Ingredient code"},
        foreignKeys = {
                @ForeignKey(entity = ShoppingList.class, parentColumns = "Shopping list code", childColumns = "Shopping list code"),
                @ForeignKey(entity = IngredNutValue.class, parentColumns = "Ingredient code", childColumns = "Ingredient code")
        }
)
public class ShoppingListIngredient {
        @ColumnInfo(name="Shopping list code") int shoppingCode;
        @ColumnInfo(name="Ingredient code") int ingredientCode;
}
