package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "foodPortionDesc")

public class FoodPortionDesc {
    @PrimaryKey @ColumnInfo(name="Portion code") int portionCode;
    @ColumnInfo(name="Portion description") String portionDesc;
}
