package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subcodeDesc")

public class SubcodeDesc {
    @PrimaryKey @ColumnInfo(name="Subcode") int subcode;
    @ColumnInfo(name="Subcode description") String subcodeDesc;
}
