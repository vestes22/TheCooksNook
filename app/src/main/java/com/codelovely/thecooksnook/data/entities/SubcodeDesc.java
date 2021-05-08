package com.codelovely.thecooksnook.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subcodeDesc")

public class SubcodeDesc {
    private @PrimaryKey @ColumnInfo(name="Subcode") int subcode;
    private @ColumnInfo(name="Subcode description") String subcodeDesc;

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public void setSubcodeDesc(String subcodeDesc) {
        this.subcodeDesc = subcodeDesc;
    }

    public int getSubcode() {
        return subcode;
    }

    public String getSubcodeDesc() {
        return subcodeDesc;
    }
}
