package com.codelovely.thecooksnook;

public enum RecommendedDailyValues {
    PROTEIN(1003, 50.0f),
    ENERGY(1008, 2000.0f),
    VITAMIN_C(1162, 90.0f),
    SODIUM(1093, 2300.00f),
    CARBS(1005, 275.0f),
    TOTAL_FAT(1004, 78.0f),
    FIBER(1079, 28.0f),
    CALCIUM(1087, 1300.0f),
    IRON(1089, 18.0f),
    POTASSIUM(1092, 4700.0f),
    CHOLESTEROL(1253, 300.0f),
    VITAMIN_A(1104, 900.0f);

    public final float v;
    private final int id;


    RecommendedDailyValues(int id, float v) {
        this.id = id;
        this.v = v;
    }

    public float getDailyValue() {
        return v;
    }
    public int getNutrientId() { return id; }
}
