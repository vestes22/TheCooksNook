package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "FNDDSIngred",
        primaryKeys = {"Food code", "Seq num"},
        foreignKeys = {
                @ForeignKey(entity = MainFoodDesc.class, parentColumns = "Food code", childColumns = "Food code"),
                @ForeignKey(entity = FoodPortionDesc.class, parentColumns = "Portion code", childColumns = "Portion code")
        }
)

public class FNDDSIngredients {
    private @ColumnInfo(name="Food code") int foodId;
    private @ColumnInfo(name="Seq num") int seqNumber;
    private @ColumnInfo(name="Ingredient code") int ingredientId;
    private @ColumnInfo(name="Ingredient description") String ingredientDesc;
    private @ColumnInfo(name="Amount") float amount;
    private @ColumnInfo(name="Measure") String measure;
    private @ColumnInfo(name="Portion code") int portionId;
    private @ColumnInfo(name="Retention code") int retentionId;
    private @ColumnInfo(name="Ingredient weight") float ingredientWeight;

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public void setIngredientDesc(String ingredientDesc) {
        this.ingredientDesc = ingredientDesc;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setPortionId(int portionId) {
        this.portionId = portionId;
    }

    public void setRetentionId(int retentionId) {
        this.retentionId = retentionId;
    }

    public void setIngredientWeight(float ingredientWeight) {
        this.ingredientWeight = ingredientWeight;
    }

    public int getFoodId() {
        return foodId;
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public String getIngredientDesc() {
        return ingredientDesc;
    }

    public float getAmount() {
        return amount;
    }

    public String getMeasure() {
        return measure;
    }

    public int getPortionId() {
        return portionId;
    }

    public int getRetentionId() {
        return retentionId;
    }

    public float getIngredientWeight() {
        return ingredientWeight;
    }
}
