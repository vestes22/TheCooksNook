package com.codelovely.thecooksnook;

public class Nutrients {
    private static int[] nutrientNumbersList = {605,205,303,204,606,307,291,269,601,301,203,208,306,318,401,958,957};
    private static int[] nutrientIds = {1257, 1005, 1089, 1110, 1004, 1258, 1093, 1079, 2000, 1253, 1087, 1003, 1008, 1092, 1104, 1162, 1186};

    static int[] getNutrientNumbersList() {
        return nutrientNumbersList;
    }

    public static int[] getNutrientIds() { return nutrientIds; }
}
