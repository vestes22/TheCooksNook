package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "addFoodDesc",
        primaryKeys = {"Food code", "Seq num"},
        foreignKeys = {@ForeignKey(entity = MainFoodDesc.class, parentColumns = "Food code", childColumns = "Food code")}
)

public class AddFoodDesc {
    @ColumnInfo(name="Food code") int foodCode;
    @ColumnInfo(name="Seq num") int seqNumber;
    @ColumnInfo(name="Additional food description") String addFoodDesc;
}
