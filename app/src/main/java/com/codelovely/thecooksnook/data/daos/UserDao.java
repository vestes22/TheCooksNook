package com.codelovely.thecooksnook.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT  * FROM users")
    public LiveData<List<User>> getAll();

    @Insert
    void insert(User user);
}
