package com.codelovely.thecooksnook.data.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;


@Entity(
        tableName = "recommendedDailyValues",
        primaryKeys = {"Nutrient code"}
)
public class RecommendedDailyValue {
    private @ColumnInfo(name="Nutrient code") int nutrientCode;
    private @ColumnInfo(name="Daily value") float dailyValue;

    public void setNutrientCode(int nutrientCode) { this.nutrientCode = nutrientCode; }

    public void setDailyValue(float dailyValue) { this.dailyValue = dailyValue; }

    public int getNutrientCode() { return nutrientCode; }

    public float getDailyValue() { return dailyValue; }
}
