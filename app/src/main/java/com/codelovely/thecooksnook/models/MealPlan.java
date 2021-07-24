package com.codelovely.thecooksnook.models;

import java.time.LocalDate;
import java.util.List;

public class MealPlan {
    private int id;
    private String dayOfWeek;
    private List<RecipeModel> recipes;
    private LocalDate date;
    private String month;
    private int day;
    private int year;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public void setRecipes(List<RecipeModel> recipes) { this.recipes = recipes; }

    public void setMonth(String month) {
        String lowerCaseMonth = month.toLowerCase();
        String formattedMonth = lowerCaseMonth.substring(0, 1).toUpperCase() + lowerCaseMonth.substring(1);
        this.month = formattedMonth;
    }

    public void setDay(int day) { this.day = day; }

    public void setYear(int year) { this.year = year; }

    public String getDayOfWeek() { return dayOfWeek; }

    public List<RecipeModel> getRecipes() { return recipes; }

    public String getMonth() { return month; }

    public int getDay() { return day; }

    public int getYear() { return year; }


}
