package com.codelovely.thecooksnook.models.restmodels;

public class FoodNutrient {
    private int fdcId;
    private int id;
    private float amount;
    private Nutrient nutrient;

    public void setFdcId(int fdcId) { this.fdcId = fdcId; }

    public int getFdcId() { return fdcId; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Nutrient getNutrient() {
        return nutrient;
    }

    public void setNutrient(Nutrient nutrient) {
        this.nutrient = nutrient;
    }
}
