package com.codelovely.thecooksnook.models;

public class Nutrient {
    private int foodCode;
    private String mainFoodDesc;
    private float portionWeight;
    private int portionCode;
    private String portionDesc;
    private int nutrientCode;
    private  float nutrientValue;
    private String nutrientDescription;
    private String tagname;
    private String unit;
    private int decimals;

    public void setFoodCode(int foodCode) { this.foodCode = foodCode; }

    public void setMainFoodDesc(String mainFoodDesc) { this.mainFoodDesc = mainFoodDesc; }

    public void setPortionWeight(float portionWeight) {this.portionWeight = portionWeight; }

    public void setPortionCode(int portionCode) { this.portionCode = portionCode; }

    public void setPortionDesc(String portionDesc) { this.portionDesc = portionDesc; }

    public void setNutrientCode(int nutrientCode) { this.nutrientCode = nutrientCode; }

    public void setNutrientValue(float nutrientValue) { this.nutrientValue = nutrientValue; }

    public void setNutrientDescription(String nutrientDescription) { this.nutrientDescription = nutrientDescription; }

    public void setTagname(String tagname) { this.tagname = tagname; }

    public void setUnit(String unit) { this.unit = unit; }

    public void setDecimals(int decimals) { this.decimals = decimals; }

    public int getFoodCode() { return foodCode; }

    public String getMainFoodDesc() { return mainFoodDesc; }

    public float getPortionWeight() { return portionWeight; }

    public int getPortionCode() { return portionCode; }

    public String getPortionDesc() { return portionDesc; }

    public int getNutrientCode() { return nutrientCode; }

    public float getNutrientValue() { return nutrientValue; }

    public String getNutrientDescription() { return nutrientDescription; }

    public String getTagname() { return tagname; }

    public String getUnit() { return unit; }

    public int getDecimals() { return decimals;
    }
}
