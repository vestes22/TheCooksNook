package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserShoppingListDao {
    @Query("SELECT * FROM userShoppingLists")
    public List<UserShoppingList> getAll();
}
