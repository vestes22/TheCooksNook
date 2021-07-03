package com.codelovely.thecooksnook.models.restmodels;

import java.util.List;


public class BrandedFoodItem {
    private int fdcId;
    private String brandOwner;
    private String dataType;
    private String description;
    private String ingredients;
    private String servingSizeUnit;
    private String brandedFoodCategory;
    private List<FoodNutrient> foodNutrients;
    private LabelNutrients labelNutrients;


    public int getFdcId() {
        return fdcId;
    }

    public void setFdcId(int fdcId) {
        this.fdcId = fdcId;
    }

    public String getBrandOwner() {
        return brandOwner;
    }

    public void setBrandOwner(String brandOwner) {
        this.brandOwner = brandOwner;
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getServingSizeUnit() {
        return servingSizeUnit;
    }

    public void setServingSizeUnit(String servingSizeUnit) {
        this.servingSizeUnit = servingSizeUnit;
    }

    public String getBrandedFoodCategory() {
        return brandedFoodCategory;
    }

    public void setBrandedFoodCategory(String brandedFoodCategory) {
        this.brandedFoodCategory = brandedFoodCategory;
    }

    public List<FoodNutrient> getFoodNutrients() {
        return foodNutrients;
    }

    public void setFoodNutrients(List<FoodNutrient> foodNutrients) {
        this.foodNutrients = foodNutrients;
    }

    public LabelNutrients getLabelNutrients() {
        return labelNutrients;
    }

    public void setLabelNutrients(LabelNutrients labelNutrients) {
        this.labelNutrients = labelNutrients;
    }
}
