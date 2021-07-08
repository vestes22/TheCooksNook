package com.codelovely.thecooksnook.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.ShoppingListFood;

import java.util.List;

@Dao
public interface ShoppingListFoodDao {
    @Query("SELECT * FROM shoppingListFoods")
    LiveData<List<ShoppingListFood>> getAll();

    @Query("SELECT EXISTS(SELECT * FROM shoppingListFoods WHERE `Shopping list code` = :shoppingListCode AND `Food code` = :fdcId)")
    boolean checkIfExists(int fdcId, int shoppingListCode);

    @Insert
    void insert(ShoppingListFood shoppingListFood);
}
