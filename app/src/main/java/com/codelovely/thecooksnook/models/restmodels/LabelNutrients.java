package com.codelovely.thecooksnook.models.restmodels;

import com.codelovely.thecooksnook.models.labelnutrients.Calories;
import com.codelovely.thecooksnook.models.labelnutrients.Fat;

public class LabelNutrients {
    private Fat fat;
    private Calories calories;

    public Calories getCalories() {
        return calories;
    }

    public void setCalories(Calories calories) {
        this.calories = calories;
    }

    public Fat getFat() {
        return fat;
    }

    public void setFat(Fat fat) {
        this.fat = fat;
    }
}
