package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (
        tableName = "foodSubcodeLinks",
        primaryKeys = {"Food code", "Subcode"},
        foreignKeys = {@ForeignKey(entity = MainFoodDesc.class, parentColumns = "Food code", childColumns = "Food code"),
                @ForeignKey(entity = SubcodeDesc.class, parentColumns = "Subcode", childColumns = "Subcode")
        }
)
public class FoodSubcodeLinks {
    @ColumnInfo(name="Food code") int foodCode;
    @ColumnInfo(name="Subcode") int subcode;
}
