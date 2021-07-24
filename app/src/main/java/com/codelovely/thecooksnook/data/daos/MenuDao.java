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
    LiveData<List<Menu>> getAll();

    @Insert
    long insert(Menu menu);

    @Query("SELECT * FROM menus WHERE `User code` = :userCode")
    List<Menu> getUserMenus(String userCode);
}
