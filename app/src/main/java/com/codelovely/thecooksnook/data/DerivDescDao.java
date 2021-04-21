package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DerivDescDao {
    @Query("SELECT * FROM derivDesc")
    public List<DerivDesc> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(DerivDesc... derivDescs);
}
