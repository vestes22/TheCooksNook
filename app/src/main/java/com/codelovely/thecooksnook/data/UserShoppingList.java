package com.codelovely.thecooksnook.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity (
        tableName = "userShoppingLists",
        foreignKeys = {
                @ForeignKey(entity = ShoppingList.class, parentColumns = "Shopping list code", childColumns = "Shopping list code"),
                @ForeignKey(entity = IngredNutValue.class, parentColumns = "Ingredient code", childColumns = "Ingredient code"),
                @ForeignKey(entity = MainFoodDesc.class, parentColumns = "Food code", childColumns = "Food code")
        }
)
public class UserShoppingList {

}
