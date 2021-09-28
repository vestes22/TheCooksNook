package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.codelovely.thecooksnook.models.MealPlan;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.viewmodels.DailyMealPlanViewModel;

import java.util.ArrayList;
import java.util.List;

public class DailyMealPlanActivity extends AppCompatActivity {
    DailyMealPlanViewModel mDailyMealPlanViewModel;
    NutritionProfileFragment nutritionProfileFragment;
    TextView dateText, dayOfWeekText;
    ListView breakfastList, lunchList, dinnerList, appetizerList, dessertList, drinkList;
    List<String> breakfastL, lunchL, dinnerL, appetizerL, dessertL, drinkL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_meal_plan);

        breakfastL = new ArrayList<>();
        lunchL = new ArrayList<>();
        dinnerL = new ArrayList<>();
        appetizerL = new ArrayList<>();
        dessertL = new ArrayList<>();
        drinkL = new ArrayList<>();

        mDailyMealPlanViewModel = new ViewModelProvider(this).get(DailyMealPlanViewModel.class);

        Intent intent = getIntent();
        int mealPlanId = intent.getIntExtra("mealPlanId", 0);
        mDailyMealPlanViewModel.setMealPlan(mealPlanId);

        dateText = findViewById(R.id.dailyMealPlan_dateText);
        dayOfWeekText = findViewById(R.id.dailyMealPlan_dayOfWeekText);
        breakfastList = findViewById(R.id.dailyMealPlan_breakfastRecipeList);
        lunchList = findViewById(R.id.dailyMealPlan_lunchRecipeList);
        dinnerList = findViewById(R.id.dailyMealPlan_dinnerRecipeList);
        appetizerList = findViewById(R.id.dailyMealPlan_appetizerRecipeList);
        dessertList = findViewById(R.id.dailyMealPlan_dessertRecipeList);
        drinkList = findViewById(R.id.dailyMealPlan_drinkRecipeList);
        
        MealPlan mealPlan = mDailyMealPlanViewModel.getMealPlan();
        dateText.setText(mealPlan.getMonth() + " " + mealPlan.getDay() + ", " + mealPlan.getYear());
        dayOfWeekText.setText(mealPlan.getDayOfWeek());
        

        nutritionProfileFragment  = (NutritionProfileFragment) getSupportFragmentManager().findFragmentById(R.id.dailyMealPlan_nutritionProfileFragment);
        List<RecipeModel> menuRecipes = mDailyMealPlanViewModel.getMealPlan().getRecipes();

        for (RecipeModel recipe : menuRecipes) {
            nutritionProfileFragment.getNutritionProfileViewModel().updateHashMap(recipe);
            if (recipe.getCategory().equals(RecipeCategory.BREAKFAST.toString())) {
                breakfastL.add(recipe.getName());
            }
            else if (recipe.getCategory().equals(RecipeCategory.LUNCH.toString())) {
                lunchL.add(recipe.getName());
            }
            else if (recipe.getCategory().equals(RecipeCategory.DINNER.toString())) {
               dinnerL.add(recipe.getName());
            }
            else if (recipe.getCategory().equals(RecipeCategory.APPETIZER.toString())) {
                appetizerL.add(recipe.getName());
            }
            else if (recipe.getCategory().equals(RecipeCategory.DESSERT.toString())) {
                dessertL.add(recipe.getName());
            }
            else if (recipe.getCategory().equals(RecipeCategory.DRINK.toString())) {
                drinkL.add(recipe.getName());
            }

            ArrayAdapter breakfastAdapter = new ArrayAdapter(this, R.layout.simple_list_item_1_edited, breakfastL);
            breakfastList.setAdapter(breakfastAdapter);

            ArrayAdapter lunchAdapter = new ArrayAdapter(this, R.layout.simple_list_item_1_edited, lunchL);
            lunchList.setAdapter(lunchAdapter);

            ArrayAdapter dinnerAdapter = new ArrayAdapter(this, R.layout.simple_list_item_1_edited, dinnerL);
            dinnerList.setAdapter(dinnerAdapter);

            ArrayAdapter appetizerAdapter = new ArrayAdapter(this, R.layout.simple_list_item_1_edited, appetizerL);
            appetizerList.setAdapter(appetizerAdapter);

            ArrayAdapter dessertAdapter = new ArrayAdapter(this, R.layout.simple_list_item_1_edited, dessertL);
            dessertList.setAdapter(dessertAdapter);

            ArrayAdapter drinkAdapter = new ArrayAdapter(this, R.layout.simple_list_item_1_edited, drinkL);
            drinkList.setAdapter(drinkAdapter);

            breakfastList.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
                {
                    String recipeName = breakfastL.get(position);
                    RecipeModel recipe = new RecipeModel();
                    for (RecipeModel recipeModel : menuRecipes) {
                        if (recipeName.equals(recipeModel.getName())) {
                            recipe = recipeModel;
                            break;
                        }
                    }
                    Intent intent = new Intent(DailyMealPlanActivity.this, RecipeActivity.class);
                    intent.putExtra("recipeId", recipe.getId());
                    startActivity(intent);
                }
            });

            lunchList.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
                {
                    String recipeName = lunchL.get(position);
                    RecipeModel recipe = new RecipeModel();
                    for (RecipeModel recipeModel : menuRecipes) {
                        if (recipeName.equals(recipeModel.getName())) {
                            recipe = recipeModel;
                            break;
                        }
                    }
                    Intent intent = new Intent(DailyMealPlanActivity.this, RecipeActivity.class);
                    intent.putExtra("recipeId", recipe.getId());
                    startActivity(intent);
                }
            });

            dinnerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
                {
                    String recipeName = dinnerL.get(position);
                    RecipeModel recipe = new RecipeModel();
                    for (RecipeModel recipeModel : menuRecipes) {
                        if (recipeName.equals(recipeModel.getName())) {
                            recipe = recipeModel;
                            break;
                        }
                    }
                    Intent intent = new Intent(DailyMealPlanActivity.this, RecipeActivity.class);
                    intent.putExtra("recipeId", recipe.getId());
                    startActivity(intent);
                }
            });

            appetizerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
                {
                    String recipeName = appetizerL.get(position);
                    RecipeModel recipe = new RecipeModel();
                    for (RecipeModel recipeModel : menuRecipes) {
                        if (recipeName.equals(recipeModel.getName())) {
                            recipe = recipeModel;
                            break;
                        }
                    }
                    Intent intent = new Intent(DailyMealPlanActivity.this, RecipeActivity.class);
                    intent.putExtra("recipeId", recipe.getId());
                    startActivity(intent);
                }
            });

            dessertList.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
                {
                    String recipeName = dessertL.get(position);
                    RecipeModel recipe = new RecipeModel();
                    for (RecipeModel recipeModel : menuRecipes) {
                        if (recipeName.equals(recipeModel.getName())) {
                            recipe = recipeModel;
                            break;
                        }
                    }
                    Intent intent = new Intent(DailyMealPlanActivity.this, RecipeActivity.class);
                    intent.putExtra("recipeId", recipe.getId());
                    startActivity(intent);
                }
            });

            drinkList.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
                {
                    String recipeName = drinkL.get(position);
                    RecipeModel recipe = new RecipeModel();
                    for (RecipeModel recipeModel : menuRecipes) {
                        if (recipeName.equals(recipeModel.getName())) {
                            recipe = recipeModel;
                            break;
                        }
                    }
                    Intent intent = new Intent(DailyMealPlanActivity.this, RecipeActivity.class);
                    intent.putExtra("recipeId", recipe.getId());
                    startActivity(intent);
                }
            });

        }
    }
}
