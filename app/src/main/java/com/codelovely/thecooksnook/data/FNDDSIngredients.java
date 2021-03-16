package com.codelovely.thecooksnook.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "FNDDSIngred",
        foreignKeys = {@ForeignKey(entity = MainFoodDesc.class, parentColumns = "Food code", childColumns = "Food code"),
                @ForeignKey(entity = IngredNutValue.class, parentColumns = "Ingredient code", childColumns = "Ingredient code"),
                @ForeignKey(entity = FoodPortionDesc.class, parentColumns = "Portion code", childColumns = "Portion code")
        }
)

public class FNDDSIngredients {
    @PrimaryKey @ColumnInfo(name="Food code") int foodCode;
    @ColumnInfo(name="Seq num") int seqNumber;
    @ColumnInfo(name="Ingredient code") int ingredientCode;
    @ColumnInfo(name="Ingredient description") String ingredientDesc;
    @ColumnInfo(name="Amount") float amount;
    @ColumnInfo(name="Measure") String measure;
    @ColumnInfo(name="Portion code") int portionCode;
    @ColumnInfo(name="Retention code") int retentionCode;
    @ColumnInfo(name="Ingredient weight") float ingredientWeight;
}
