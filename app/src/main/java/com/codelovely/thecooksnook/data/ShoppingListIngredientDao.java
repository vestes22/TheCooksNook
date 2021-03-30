package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShoppingListIngredientDao {
    @Query("SELECT * FROM shoppingListIngredients")
    public List<ShoppingListIngredient> getAll();
}
