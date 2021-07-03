package com.codelovely.thecooksnook.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.Menu;

import java.util.List;

@Dao
public interface MenuDao {
    @Query("SELECT * FROM menus")
    public LiveData<List<Menu>> getAll();

    @Insert
    void insert(Menu menu);
}
