package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (
        tableName = "moistAdjust",
        foreignKeys = {@ForeignKey(entity = MainFoodDesc.class, parentColumns = "Food code", childColumns = "Food code")}
)

public class MoistAdjust {
    @PrimaryKey @ColumnInfo(name="Food code") int foodCode;
    @ColumnInfo(name = "Moisture change") float moistureChange;
}
