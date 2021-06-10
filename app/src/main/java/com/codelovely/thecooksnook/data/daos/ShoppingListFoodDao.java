package com.codelovely.thecooksnook.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.ShoppingListFood;

import java.util.List;

@Dao
public interface ShoppingListFoodDao {
    @Query("SELECT * FROM shoppingListFoods")
    public LiveData<List<ShoppingListFood>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ShoppingListFood shoppingListFood);
}