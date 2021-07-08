package com.codelovely.thecooksnook;

public enum RecommendedDailyValues {
    PROTEIN(50.0f),
    ENERGY(2000.0f),
    VITAMIN_C(90.0f),
    SODIUM(2300.00f),
    CARBS(275.0f),
    TOTAL_FAT(78.0f),
    FIBER(28.0f),
    CALCIUM(1300.0f),
    IRON(18.0f),
    MAGNESIUM(420.0f),
    PHOSPHORUS(1250.0f),
    POTASSIUM(4700.0f),
    MANGANESE(2.3f),
    RIBOFLAVIN(1.3f),
    NIACIN(16.0f),
    VITAMIN_B6(1.7f),
    FOLATE(400.0f),
    CHOLESTEROL(300.0f),
    VITAMIN_A(900.0f);

    public final float v;


    RecommendedDailyValues(float v) {
        this.v = v;
    }

    public float getDailyValue() {
        return v;
    }
}
