package com.codelovely.thecooksnook.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.ShoppingList;

import java.util.List;

@Dao
public interface ShoppingListDao {
    @Query("SELECT * FROM shoppingLists")
    public LiveData<List<ShoppingList>> getAll();

    @Insert
    void insert(ShoppingList shoppingList);
}
