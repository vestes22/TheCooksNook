package com.codelovely.thecooksnook.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (
        tableName = "ingredients"
)
public class Ingredient {
    private @PrimaryKey @ColumnInfo(name = "FDC ID") int fdcId;
    private @ColumnInfo(name="Data type") String dataType;
    private String description;
    private String category;
    private @ColumnInfo(name="Serving size unit") String servingSizeUnit;

    public int getFdcId() {
        return fdcId;
    }

    public void setFdcId(int fdcId) {
        this.fdcId = fdcId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServingSizeUnit() {
        return servingSizeUnit;
    }

    public void setServingSizeUnit(String servingSizeUnit) {
        this.servingSizeUnit = servingSizeUnit;
    }

    public void setCategory(String category) { this.category = category; }

    public String getCategory() { return category; }
}
