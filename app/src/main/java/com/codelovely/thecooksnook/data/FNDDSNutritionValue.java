package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity (
        tableName = "FNDDSNutVal",
        primaryKeys = {"Food code", "Nutrient code"},
        foreignKeys = {@ForeignKey(entity = MainFoodDesc.class, parentColumns = "Food code", childColumns = "Food code"),
            @ForeignKey(entity = NutrientDesc.class, parentColumns = "Nutrient code", childColumns = "Nutrient code")
        }
)

public class FNDDSNutritionValue {
    @ColumnInfo(name="Food code") int foodCode;
    @ColumnInfo(name="Nutrient code") int nutrientCode;
    @ColumnInfo(name="Nutrient value") float nutrientVal;
}

