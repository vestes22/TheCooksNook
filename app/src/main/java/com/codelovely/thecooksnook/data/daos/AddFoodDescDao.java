package com.codelovely.thecooksnook.data.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.AddFoodDesc;

import java.util.List;

@Dao
public interface AddFoodDescDao {
    @Query("SELECT * FROM addFoodDesc")
    public List<AddFoodDesc> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(AddFoodDesc... addFoodDescs);
}
