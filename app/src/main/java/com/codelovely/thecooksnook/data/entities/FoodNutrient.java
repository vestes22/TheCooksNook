package com.codelovely.thecooksnook.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity (
        tableName = "foodNutrients",
        primaryKeys = {"FDC ID", "Nutrient id"},
        foreignKeys = {@ForeignKey(entity = Nutrient.class, parentColumns = "id", childColumns = "Nutrient id"),
        @ForeignKey(entity=Ingredient.class, parentColumns = "FDC ID", childColumns = "FDC ID")}
)
public class FoodNutrient {
    private @ColumnInfo(name="FDC ID") int fdcId;
    private float amount;
    private @ColumnInfo(name="Nutrient id") int nutrientId;

    public int getFdcId() {
        return fdcId;
    }

    public void setFdcId(int id) {
        this.fdcId = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getNutrientId() {
        return nutrientId;
    }

    public void setNutrientId(int nutrientId) {
        this.nutrientId = nutrientId;
    }
}
