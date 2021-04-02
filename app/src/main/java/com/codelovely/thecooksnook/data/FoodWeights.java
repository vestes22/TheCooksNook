package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName ="foodWeights",
        primaryKeys = {"Food code", "Subcode", "Seq num", "Portion code"},
        foreignKeys = {@ForeignKey(entity = FoodPortionDesc.class, parentColumns = "Portion code", childColumns = "Portion code"),
            @ForeignKey(entity = MainFoodDesc.class, parentColumns = "Food code", childColumns = "Food code"),
            @ForeignKey(entity = SubcodeDesc.class, parentColumns = "Subcode", childColumns = "Subcode")
        }
)

public class FoodWeights {
    private @ColumnInfo(name="Food code") int foodId;
    private @ColumnInfo(name="Subcode") int subcode;
    private @ColumnInfo(name="Portion code") int portionId;
    private @ColumnInfo(name="Seq num") int seqNum;
    private @ColumnInfo(name="Portion weight") float portionWeight;

    public void setFoodId(int foodId) {
        this.foodId=foodId;
    }

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public void setPortionId(int portionId) {
        this.portionId = portionId;
    }

    public void setSeqNum(int seqNum) {
        this.seqNum = seqNum;
    }

    public void setPortionWeight(float portionWeight) {
        this.portionWeight = portionWeight;
    }

    public int getFoodId() {
        return foodId;
    }

    public int getSubcode() {
        return subcode;
    }

    public int getPortionId() {
        return portionId;
    }

    public int getSeqNum() {
        return seqNum;
    }

    public float getPortionWeight() {
        return portionWeight;
    }
}