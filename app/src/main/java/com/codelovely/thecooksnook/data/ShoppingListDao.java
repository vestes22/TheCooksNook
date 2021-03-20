package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShoppingListDao {
    @Query("SELECT * FROM shoppingLists")
    public List<ShoppingList> getAll();
}
