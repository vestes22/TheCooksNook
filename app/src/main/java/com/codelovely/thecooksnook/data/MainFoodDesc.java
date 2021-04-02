package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="mainFoodDesc")

public class MainFoodDesc {
    private @PrimaryKey @ColumnInfo(name="Food code") int foodId;
    private @ColumnInfo(name="Main food description") String mainFoodDesc;
    private @ColumnInfo(name="WWEIA Category number") int wweiaCategoryNumber;
    private @ColumnInfo(name="WWEIA Category description") String wweiaCategoryDesc;

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public void setMainFoodDesc(String mainFoodDesc) {
        this.mainFoodDesc = mainFoodDesc;
    }

    public void setWweiaCategoryNumber(int wweiaCategoryNumber) {
        this.wweiaCategoryNumber = wweiaCategoryNumber;
    }

    public void setWweiaCategoryDesc(String wweiaCategoryDesc) {
        this.wweiaCategoryDesc = wweiaCategoryDesc;
    }

    public int getFoodId() {
        return foodId;
    }

    public String getMainFoodDesc() {
        return mainFoodDesc;
    }

    public int getWweiaCategoryNumber() {
        return wweiaCategoryNumber;
    }

    public String getWweiaCategoryDesc() {
        return wweiaCategoryDesc;
    }
}
