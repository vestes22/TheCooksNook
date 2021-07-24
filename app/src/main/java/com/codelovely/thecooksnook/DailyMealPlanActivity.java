package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.codelovely.thecooksnook.models.MealPlan;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.viewmodels.DailyMealPlanViewModel;

import java.util.List;

public class DailyMealPlanActivity extends AppCompatActivity {
    DailyMealPlanViewModel mDailyMealPlanViewModel;
    NutritionProfileFragment nutritionProfileFragment;
    TextView dateText, dayOfWeekText, breakfastText, lunchText, dinnerText, appetizerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_meal_plan);

        mDailyMealPlanViewModel = new ViewModelProvider(this).get(DailyMealPlanViewModel.class);

        Intent intent = getIntent();
        int mealPlanId = intent.getIntExtra("mealPlanId", 0);
        mDailyMealPlanViewModel.setMealPlan(mealPlanId);

        dateText = findViewById(R.id.dailyMealPlan_dateText);
        dayOfWeekText = findViewById(R.id.dailyMealPlan_dayOfWeekText);
        breakfastText = findViewById(R.id.dailyMealPlan_breakfastRecipeText);
        lunchText = findViewById(R.id.dailyMealPlan_lunchRecipeText);
        dinnerText = findViewById(R.id.dailyMealPlan_dinnerRecipeText);
        appetizerText = findViewById(R.id.dailyMealPlan_appetizerRecipeText);
        
        MealPlan mealPlan = mDailyMealPlanViewModel.getMealPlan();
        dateText.setText(mealPlan.getMonth() + " " + mealPlan.getDay() + ", " + mealPlan.getYear());
        dayOfWeekText.setText(mealPlan.getDayOfWeek());
        

        nutritionProfileFragment  = (NutritionProfileFragment) getSupportFragmentManager().findFragmentById(R.id.dailyMealPlan_nutritionProfileFragment);
        List<RecipeModel> menuRecipes = mDailyMealPlanViewModel.getMealPlan().getRecipes();
        for (RecipeModel recipe : menuRecipes) {
            nutritionProfileFragment.getNutritionProfileViewModel().updateHashMap(recipe);
            if (recipe.getCategory().equals(RecipeCategory.BREAKFAST.toString())) {
                breakfastText.setText(recipe.getName());
            }
            if (recipe.getCategory().equals(RecipeCategory.LUNCH.toString())) {
                lunchText.setText(recipe.getName());
            }
            if (recipe.getCategory().equals(RecipeCategory.DINNER.toString())) {
                dinnerText.setText(recipe.getName());
            }
            if (recipe.getCategory().equals(RecipeCategory.APPETIZER.toString())) {
                appetizerText.setText(recipe.getName());
            }
        }
    }

    public void editMealPlanButtonClicked(View view) {
        // TODO
    }
}
