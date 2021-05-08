package com.codelovely.thecooksnook.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.codelovely.thecooksnook.data.MainFoodDesc;

@Entity (
        tableName = "foodSubcodeLinks",
        primaryKeys = {"Food code", "Subcode"},
        foreignKeys = {@ForeignKey(entity = MainFoodDesc.class, parentColumns = "Food code", childColumns = "Food code"),
                @ForeignKey(entity = SubcodeDesc.class, parentColumns = "Subcode", childColumns = "Subcode")
        }
)
public class FoodSubcodeLinks {
    private @ColumnInfo(name="Food code") int foodId;
    private @ColumnInfo(name="Subcode") int subcode;

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public int getFoodId() {
        return foodId;
    }

    public int getSubcode() {
        return subcode;
    }
}
