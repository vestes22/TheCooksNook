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
    private @ColumnInfo(name="Ingredient code") int ingredientId;
    private @ColumnInfo(name="Ingredient description") String ingredientDesc;
    private @ColumnInfo(name="Nutrient code") int nutrientId;
    private @ColumnInfo(name="Nutrient value") float nutrientValue;
    private @ColumnInfo(name="Nutrient value source") String nutrientValueSource;
    private @ColumnInfo(name="FDC ID") int fdcId;
    private @ColumnInfo(name="Derivation code") String derivationCode;
    private @ColumnInfo(name="SR AddMod year") int addModYear;
    private @ColumnInfo(name="Foundation year acquired") int foundationYearAcq;

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public void setIngredientDesc(String ingredientDesc) {
        this.ingredientDesc = ingredientDesc;
    }

    public void setNutrientId(int nutrientId) {
        this.nutrientId = nutrientId;
    }

    public void setNutrientValue(float nutrientValue) {
        this.nutrientValue = nutrientValue;
    }

    public void setNutrientValueSource(String nutrientValueSource) {
        this.nutrientValueSource = nutrientValueSource;
    }

    public void setFdcId(int fdcId) {
        this.fdcId = fdcId;
    }

    public void setDerivationCode(String derivationCode) {
        this.derivationCode = derivationCode;
    }

    public void setAddModYear(int addModYear) {
        this.addModYear = addModYear;
    }

    public void setFoundationYearAcq(int foundationYearAcq) {
        this.foundationYearAcq = foundationYearAcq;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public String getIngredientDesc() {
        return ingredientDesc;
    }

    public int getNutrientId() {
        return nutrientId;
    }

    public float getNutrientValue() {
        return nutrientValue;
    }

    public String getNutrientValueSource() {
        return nutrientValueSource;
    }

    public int getFdcId() {
        return fdcId;
    }

    public String getDerivationCode() {
        return derivationCode;
    }

    public int getAddModYear() {
        return addModYear;
    }

    public int getFoundationYearAcq() {
        return foundationYearAcq;
    }
}
