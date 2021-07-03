package com.codelovely.thecooksnook.models;

import java.util.List;

public class MealPlan {
    private String dayOfWeek;
    private List<RecipeModel> recipes;
    private String month;
    private int day;
    private int year;

    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public void setRecipes(List<RecipeModel> recipes) { this.recipes = recipes; }

    public void setMonth(String month) { this.month = month; }

    public void setDay(int day) { this.day = day; }

    public void setYear(int year) { this.year = year; }

    public String getDayOfWeek() { return dayOfWeek; }

    public List<RecipeModel> getRecipes() { return recipes; }

    public String getMonth() { return month; }

    public int getDay() { return day; }

    public int getYear() { return year; }


}
