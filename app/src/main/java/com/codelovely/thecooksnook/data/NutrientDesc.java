package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "nutDesc")

public class NutrientDesc {
    @PrimaryKey @ColumnInfo(name="Nutrient code") int nutrientCode;
    @ColumnInfo(name="Nutrient description") String nutrientDesc;
    @ColumnInfo(name="Tagname") String tagname;
    @ColumnInfo(name="Unit") String unit;
    @ColumnInfo(name="Decimals") int decimals;
}
