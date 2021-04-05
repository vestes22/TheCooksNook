package com.codelovely.thecooksnook.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT  * FROM users")
    public LiveData<List<User>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);
}
