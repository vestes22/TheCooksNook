package com.codelovely.thecooksnook.models.restmodels;


import java.util.List;

/*

JSON for request body of 'foods' POST request. Retrieves a list of food items by a list of up to 20 FDC IDs.
 Optional format and nutrients can be specified. Invalid FDC ID's or ones that are not found are omitted and an empty set is returned if there are no matches.
 */
public class FoodsCriteria {
    /*
    minItems: 1
    maxItems: 20
    example: List [ 534358, 373052, 616350 ]
    List of multiple FDC ID's
     */
    private int[] fdcIds;

    /*
    Optional. 'abridged' for an abridged set of elements, 'full' for all elements (default).
    Enum:
    [ abridged, full ]
     */
    private String format;

    /*
    minItems: 1
    maxItems: 25
    example: List [ 203, 204, 205 ]
    Optional. List of up to 25 nutrient numbers. Only the nutrient information for the specified nutrients will be returned.
    If a food does not have any matching nutrients, the food will be returned with an empty foodNutrients element.
     */
    private int[] nutrients;

    public int[] getFdcIds() {
        return fdcIds;
    }

    public void setFdcIds(int[] fdcIds) {
        this.fdcIds = fdcIds;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int[] getNutrients() {
        return nutrients;
    }

    public void setNutrients(int[] nutrients) {
        this.nutrients = nutrients;
    }
}
