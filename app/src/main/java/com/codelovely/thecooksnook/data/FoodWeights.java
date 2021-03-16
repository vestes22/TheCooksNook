package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName ="foodWeights",
        primaryKeys = {"Food code", "Subcode", "Seq num"},
        foreignKeys = {@ForeignKey(entity = FoodPortionDesc.class, parentColumns = "Portion code", childColumns = "Portion code"),
            @ForeignKey(entity = MainFoodDesc.class, parentColumns = "Food code", childColumns = "Food code"),
            @ForeignKey(entity = SubcodeDesc.class, parentColumns = "Subcode", childColumns = "Subcode")
        }
)

public class FoodWeights {
    @ColumnInfo(name="Food code") int foodCode;
    @ColumnInfo(name="Subcode") int subcode;
    @ColumnInfo(name="Portion code") int portionCode;
    @ColumnInfo(name="Seq num") int seqNum;
    @ColumnInfo(name="Portion weight") float portionWeight;
}