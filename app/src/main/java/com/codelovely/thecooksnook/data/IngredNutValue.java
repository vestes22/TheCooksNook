package com.codelovely.thecooksnook.data;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "ingredNutVal",
        primaryKeys = {"Ingredient code", "Nutrient code"},
        foreignKeys = {@ForeignKey(entity = NutrientDesc.class, parentColumns = "Nutrient code", childColumns = "Nutrient code"),
            @ForeignKey(entity = DerivDesc.class, parentColumns = "Derivation code", childColumns = "Derivation code")
        }
)

public class IngredNutValue {
    @ColumnInfo(name="Ingredient code") int ingredientCode;
    @ColumnInfo(name="Ingredient description") String ingredientDesc;
    @ColumnInfo(name="Nutrient code") int nutrientCode;
    @ColumnInfo(name="Nutrient value") float nutrientValue;
    @ColumnInfo(name="Nutrient value source") String nutrientValueSource;
    @ColumnInfo(name="FDC ID") int fdcId;
    @ColumnInfo(name="Derivation code") String derivationCode;
    @ColumnInfo(name="SR AddMod year") int addModYear;
    @ColumnInfo(name="Foundation year acquired") int foundationYearAcq;
}
