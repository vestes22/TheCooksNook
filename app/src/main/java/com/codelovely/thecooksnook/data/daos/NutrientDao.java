package com.codelovely.thecooksnook.data.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.Nutrient;

@Dao
public interface NutrientDao {
    @Query("SELECT EXISTS(SELECT * FROM nutrients WHERE id = :id)")
    boolean checkIfExists(int id);

    @Insert
    long insert(Nutrient nutrient);

    @Query("SELECT * FROM nutrients WHERE id = :id")
    Nutrient getNutrientById(int id);
}
