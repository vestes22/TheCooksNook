package com.codelovely.thecooksnook.models.restmodels;

public class FoodPortion {
    private int id;
    private float amount;
    private int dataPoints;
    private float gramWeight;
    private int minYearAcquired;
    private String modifier;
    private String portionDescription;
    private int sequenceNumber;
    private MeasureUnit measureUnit;

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

    public int getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(int dataPoints) {
        this.dataPoints = dataPoints;
    }

    public float getGramWeight() {
        return gramWeight;
    }

    public void setGramWeight(float gramWeight) {
        this.gramWeight = gramWeight;
    }

    public int getMinYearAcquired() {
        return minYearAcquired;
    }

    public void setMinYearAcquired(int minYearAcquired) {
        this.minYearAcquired = minYearAcquired;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getPortionDescription() {
        return portionDescription;
    }

    public void setPortionDescription(String portionDescription) {
        this.portionDescription = portionDescription;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public MeasureUnit getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(MeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }
}
