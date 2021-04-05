package com.codelovely.thecooksnook.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MenuDao {
    @Query("SELECT * FROM menus")
    public LiveData<List<Menu>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Menu menu);
}
