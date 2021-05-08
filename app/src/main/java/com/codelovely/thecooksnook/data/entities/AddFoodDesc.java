package com.codelovely.thecooksnook.data.entities;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.codelovely.thecooksnook.data.MainFoodDesc;


@Entity(
        tableName = "addFoodDesc",
        primaryKeys = {"Food code", "Seq num"},
        foreignKeys = {@ForeignKey(entity = MainFoodDesc.class, parentColumns = "Food code", childColumns = "Food code")}
)

public class AddFoodDesc {
    private @Nullable @ColumnInfo(name="Food code") int foodId;
    private @Nullable @ColumnInfo(name="Seq num") int seqNumber;
    private @ColumnInfo(name="Additional food description") String addFoodDesc;

    public void setFoodId(int foodId) { this.foodId = foodId; }

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
