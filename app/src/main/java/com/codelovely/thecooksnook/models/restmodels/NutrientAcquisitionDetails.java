package com.codelovely.thecooksnook.models.restmodels;

public class NutrientAcquisitionDetails {
    private int sampleUnitId;
    private String purchaseDate;
    private String storeCity;
    private String storeState;

    public int getSampleUnitId() {
        return sampleUnitId;
    }

    public void setSampleUnitId(int sampleUnitId) {
        this.sampleUnitId = sampleUnitId;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getStoreCity() {
        return storeCity;
    }

    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
    }

    public String getStoreState() {
        return storeState;
    }

    public void setStoreState(String storeState) {
        this.storeState = storeState;
    }
}
