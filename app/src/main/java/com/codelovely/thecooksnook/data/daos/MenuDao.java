package com.codelovely.thecooksnook.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.Menu;

import java.util.List;

@Dao
public interface MenuDao {
    @Query("SELECT * FROM menus")
    public LiveData<List<Menu>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Menu menu);
}
