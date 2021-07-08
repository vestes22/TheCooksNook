package com.codelovely.thecooksnook.models.restmodels;

public class SearchResultFood {
    private int fdcId;
    private String dataType;
    private String description;
    private String brandOwner;
    private String ingredients;
    private String additionalDescriptions;
    private float score;

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


    public String getBrandOwner() {
        return brandOwner;
    }

    public void setBrandOwner(String brandOwner) {
        this.brandOwner = brandOwner;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getAdditionalDescriptions() {
        return additionalDescriptions;
    }

    public void setAdditionalDescriptions(String additionalDescriptions) {
        this.additionalDescriptions = additionalDescriptions;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
