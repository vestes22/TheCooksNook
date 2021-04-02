package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity (
        tableName = "shoppingListFoods",
        primaryKeys = {"Shopping list code", "Food code"},
        foreignKeys = {
                @ForeignKey(entity=ShoppingList.class, parentColumns = "Shopping list code", childColumns = "Shopping list code"),
                @ForeignKey(entity=MainFoodDesc.class, parentColumns = "Food code", childColumns = "Food code")
        }
)
public class ShoppingListFood {
    private @ColumnInfo(name="Shopping list code") int shoppingListId;
    private @ColumnInfo(name="Food code") int foodId;

    public void setShoppingListId(int shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getShoppingListId() {
        return shoppingListId;
    }

    public int getFoodId() {
        return foodId;
    }
}
