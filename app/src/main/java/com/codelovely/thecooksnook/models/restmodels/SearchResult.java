package com.codelovely.thecooksnook.models.restmodels;

import java.util.List;

public class SearchResult {
    private FoodSearchCriteria foodSearchCriteria;
    private int totalHits;
    private int currentPage;
    private int totalPages;
    private List<SearchResultFood> foods;

    public FoodSearchCriteria getFoodSearchCriteria() {
        return foodSearchCriteria;
    }

    public void setFoodSearchCriteria(FoodSearchCriteria foodSearchCriteria) {
        this.foodSearchCriteria = foodSearchCriteria;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchResultFood> getFoods() {
        return foods;
    }

    public void setFoods(List<SearchResultFood> foods) {
        this.foods = foods;
    }
}
