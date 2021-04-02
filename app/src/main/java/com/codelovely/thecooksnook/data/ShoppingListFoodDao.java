package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShoppingListFoodDao {
    @Query("SELECT * FROM shoppingListFoods")
    public List<ShoppingListFood> getAll();
}