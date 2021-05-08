package com.codelovely.thecooksnook.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "foodPortionDesc")

public class FoodPortionDesc {
    private @PrimaryKey @ColumnInfo(name="Portion code") int portionId;
    private @ColumnInfo(name="Portion description") String portionDesc;

    public void setPortionId(int portionId) {
        this.portionId = portionId;
    }

    public void setPortionDesc(String portionDesc) {
        this.portionDesc = portionDesc;
    }

    public int getPortionId() {
        return portionId;
    }

    public String getPortionDesc() {
        return portionDesc;
    }
}
