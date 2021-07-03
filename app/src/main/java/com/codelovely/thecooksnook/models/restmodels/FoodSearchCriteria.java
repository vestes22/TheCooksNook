package com.codelovely.thecooksnook.models.restmodels;

/*
JSON for request body of 'search' POST request
 */
public class FoodSearchCriteria {
    private String query;
    private String[] dataType;
    private int pageSize;
    private int pageNumber;
    private String sortBy;
    private String sortOrder;
    private String brandOwner;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String[] getDataType() {
        return dataType;
    }

    public void setDataType(String[] dataType) {
        this.dataType = dataType;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getBrandOwner() {
        return brandOwner;
    }

    public void setBrandOwner(String brandOwner) {
        this.brandOwner = brandOwner;
    }
}
