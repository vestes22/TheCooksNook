package com.codelovely.thecooksnook.models;

public class FoodPortion {
    private int foodCode;
    private int portionCode;
    private String portionDesc;

    public void setFoodCode(int foodCode) { this.foodCode = foodCode; }

    public void setPortionCode(int portionCode) { this.portionCode = portionCode; }

    public void setPortionDesc(String portionDesc) { this.portionDesc = portionDesc; }

    public int getFoodCode() { return foodCode; }

    public int getPortionCode() { return portionCode; }

    public String getPortionDesc() { return portionDesc; }
}
