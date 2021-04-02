package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "addFoodDesc",
        primaryKeys = {"Food code", "Seq num"},
        foreignKeys = {@ForeignKey(entity = MainFoodDesc.class, parentColumns = "Food code", childColumns = "Food code")}
)

public class AddFoodDesc {
    private @ColumnInfo(name="Food code") int foodId;
    private @ColumnInfo(name="Seq num") int seqNumber;
    private @ColumnInfo(name="Additional food description") String addFoodDesc;

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }

    public void setAddFoodDesc(String addFoodDesc) {
        this.addFoodDesc = addFoodDesc;
    }

    public int getFoodId() {
        return foodId;
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public String getAddFoodDesc() {
        return addFoodDesc;
    }
}
