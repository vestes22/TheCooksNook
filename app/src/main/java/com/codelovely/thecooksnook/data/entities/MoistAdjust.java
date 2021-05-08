package com.codelovely.thecooksnook.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.codelovely.thecooksnook.data.MainFoodDesc;

@Entity (
        tableName = "moistAdjust",
        foreignKeys = {@ForeignKey(entity = MainFoodDesc.class, parentColumns = "Food code", childColumns = "Food code")}
)

public class MoistAdjust {
    private @PrimaryKey @ColumnInfo(name="Food code") int foodId;
    private @ColumnInfo(name = "Moisture change") float moistureChange;

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public void setMoistureChange(float moistureChange) {
        this.moistureChange = moistureChange;
    }

    public int getFoodId() {
        return foodId;
    }

    public float getMoistureChange() {
        return moistureChange;
    }
}
