package com.codelovely.thecooksnook.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.codelovely.thecooksnook.data.MainFoodDesc;

@Entity (
        tableName = "FNDDSNutVal",
        primaryKeys = {"Food code", "Nutrient code"},
        foreignKeys = {@ForeignKey(entity = MainFoodDesc.class, parentColumns = "Food code", childColumns = "Food code"),
            @ForeignKey(entity = NutrientDesc.class, parentColumns = "Nutrient code", childColumns = "Nutrient code")
        }
)

public class FNDDSNutritionValue {
    private @ColumnInfo(name="Food code") int foodId;
    private @ColumnInfo(name="Nutrient code") int nutrientId;
    private @ColumnInfo(name="Nutrient value") float nutrientVal;

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public void setNutrientId(int nutrientId) {
        this.nutrientId = nutrientId;
    }

    public void setNutrientVal(float nutrientVal) {
        this.nutrientVal = nutrientVal;
    }

    public int getFoodId() {
        return foodId;
    }

    public int getNutrientId() {
        return nutrientId;
    }

    public float getNutrientVal() {
        return nutrientVal;
    }
}

