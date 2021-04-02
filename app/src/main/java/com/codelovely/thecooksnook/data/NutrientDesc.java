package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "nutDesc")

public class NutrientDesc {
    private @PrimaryKey @ColumnInfo(name="Nutrient code") int nutrientId;
    private @ColumnInfo(name="Nutrient description") String nutrientDesc;
    private @ColumnInfo(name="Tagname") String tagname;
    private @ColumnInfo(name="Unit") String unit;
    private @ColumnInfo(name="Decimals") int decimals;

    public void setNutrientId(int nutrientId) {
        this.nutrientId = nutrientId;
    }

    public void setNutrientDesc(String nutrientDesc) {
        this.nutrientDesc = nutrientDesc;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    public int getNutrientId() {
        return nutrientId;
    }

    public String getNutrientDesc() {
        return nutrientDesc;
    }

    public String getTagname() {
        return tagname;
    }

    public String getUnit() {
        return unit;
    }

    public int getDecimals()  {
        return decimals;
    }
}
