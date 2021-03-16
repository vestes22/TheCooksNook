package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="mainFoodDesc")

public class MainFoodDesc {
    @PrimaryKey @ColumnInfo(name="Food code") int foodCode;
    @ColumnInfo(name="Main food description") String mainFoodDesc;
    @ColumnInfo(name="WWEIA Category number") int wweiaCategoryNumber;
    @ColumnInfo(name="WWEIA Category description") String wweiaCategoryDesc;
}
