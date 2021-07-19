package com.codelovely.thecooksnook.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT  * FROM users")
    LiveData<List<User>> getAll();

    @Query("SELECT EXISTS(SELECT * FROM users WHERE `User code` = :userCode )")
    boolean checkIfExists(String userCode);

    @Insert
    void insert(User user);
}
