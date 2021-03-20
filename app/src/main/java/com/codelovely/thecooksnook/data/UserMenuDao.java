package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserMenuDao {
    @Query("SELECT * FROM userMenus")
    public List<UserMenu> getAll();
}
