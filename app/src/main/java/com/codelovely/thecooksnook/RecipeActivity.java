package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codelovely.thecooksnook.adapters.NutrientListAdapter;
import com.codelovely.thecooksnook.adapters.SavedIngredientsListAdapter;
import com.codelovely.thecooksnook.data.entities.Ingredient;
import com.codelovely.thecooksnook.models.IngredientModel;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.restmodels.FoodNutrient;
import com.codelovely.thecooksnook.viewmodels.RecipeViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;


public class RecipeActivity extends AppCompatActivity implements SavedIngredientsListAdapter.IngredientsListener {
    RecipeViewModel mRecipeViewModel;
    RecipeModel recipe;
    TextView recipeName, recipeDescription, recipeInstructions, recipeNumServings, nutritionItemName;
    TextView calorieProgress, fatProgress, fiberProgress, carbProgress, proteinProgress;
    SavedIngredientsListAdapter ingredientsAdapter;
    NutrientListAdapter nutrientAdapter;
    Button nutritionProfileButton, closePopUpBtn;
    LinearLayout linearLayout;
    PopupWindow popupWindow;
    ProgressBar calorieBar, fatBar, fiberBar, carbBar, proteinBar;

    PieChart macronutrientChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        Intent mIntent = getIntent();
        int recipeId = mIntent.getIntExtra("recipeId", 0);
        recipe = mRecipeViewModel.getRecipeById(recipeId);
        recipeName = findViewById(R.id.recipe_recipeName);
        recipeDescription = findViewById(R.id.recipe_recipeDescription);
        recipeInstructions = findViewById(R.id.recipe_recipeInstructions);
        recipeNumServings = findViewById(R.id.recipe_recipeNumServings);
        nutritionProfileButton = findViewById(R.id.recipe_recipeNutritionProfileButton);
        linearLayout = findViewById(R.id.recipe_linearLayout);

        recipeName.setText(recipe.getName());
        recipeDescription.setText(recipe.getDescription());
        recipeInstructions.setText(recipe.getInstructions());
        String numServingsText = getString(R.string.servings, recipe.getNumServings());
        recipeNumServings.setText(numServingsText);


        RecyclerView ingredientsRv = findViewById(R.id.recipe_ingredientsListRecyclerView);
        ingredientsAdapter = new SavedIngredientsListAdapter(new SavedIngredientsListAdapter.IngredientsDiff(), this);
        ingredientsAdapter.submitList(recipe.getIngredients());
        ingredientsRv.setAdapter(ingredientsAdapter);
        ingredientsRv.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView nutrientsRv = findViewById(R.id.recipe_recipeNutritionRecyclerView);
        nutrientAdapter = new NutrientListAdapter(new NutrientListAdapter.NutrientsDiff());
        nutrientAdapter.submitList(recipe.getRecipeNutrientsPerServing());
        nutrientsRv.setAdapter(nutrientAdapter);
        nutrientsRv.setLayoutManager(new LinearLayoutManager(this));


        nutritionProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) RecipeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert layoutInflater != null;
                @SuppressLint("InflateParams") View customView = layoutInflater.inflate(R.layout.nutrition_profile, null);

                closePopUpBtn = customView.findViewById(R.id.closePopUpButton);
                nutritionItemName = customView.findViewById(R.id.nutritionProfile_itemName);

                macronutrientChart = customView.findViewById(R.id.nutritionProfile_macronutrientPieChart);

                calorieBar = customView.findViewById(R.id.nutritionProfile_calorieDeterminateBar);
                fatBar = customView.findViewById(R.id.nutritionProfile_fatDeterminateBar);
                fiberBar = customView.findViewById(R.id.nutritionProfile_fiberDeterminateBar);
                carbBar = customView.findViewById(R.id.nutritionProfile_carbsDeterminateBar);
                proteinBar = customView.findViewById(R.id.nutritionProfile_proteinDeterminateBar);

                calorieProgress = customView.findViewById(R.id.nutritionProfile_calorieProgressText);
                fatProgress = customView.findViewById(R.id.nutritionProfile_fatProgressText);
                fiberProgress = customView.findViewById(R.id.nutritionProfile_fiberProgressText);
                carbProgress = customView.findViewById(R.id.nutritionProfile_carbsProgressText);
                proteinProgress = customView.findViewById(R.id.nutritionProfile_proteinProgressText);

                //instantiate popup window
                popupWindow = new PopupWindow(customView, ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);

                //display the popup window
                popupWindow.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);

                nutritionItemName.setText(recipe.getName());

                List<FoodNutrient> nutrients = recipe.getRecipeNutrientsPerServing();

                Float calories = null;
                Float fat = null;
                Float protein = null;
                Float carbs = null;
                Float fiber = null;

                for (FoodNutrient nutrient : nutrients) {
                    int nutrientId = nutrient.getNutrient().getId();
                    if (nutrientId == 1008) {
                        calories = nutrient.getAmount();
                    }
                    else if (nutrientId == 1004) {
                        fat = nutrient.getAmount();
                    }
                    else if (nutrientId == 1005) {
                        carbs = nutrient.getAmount();
                    }
                    else if(nutrientId == 1079) {
                        fiber = nutrient.getAmount();
                    }
                    else if (nutrientId == 1003) {
                        protein = nutrient.getAmount();
                    }
                }


                // Calorie progress bar
                if (calories != null) {
                    calorieBar.setProgress(Math.round((calories/RecommendedDailyValues.ENERGY.getDailyValue()) * 100));
                    String calorieText = getString(R.string.kCal_progress, calories, (int) RecommendedDailyValues.ENERGY.getDailyValue());
                    calorieProgress.setText(calorieText);
                }
                // Total fats progress bar
                if (fat != null) {
                    fatBar.setProgress(Math.round((fat/RecommendedDailyValues.TOTAL_FAT.getDailyValue()) * 100), true);
                    String fatText = getString(R.string.g_progress, fat, (int) RecommendedDailyValues.TOTAL_FAT.getDailyValue());
                    fatProgress.setText(fatText);
                }
                // Fiber progress bar
                if (fiber != null) {
                    fiberBar.setProgress( Math.round((fiber/RecommendedDailyValues.FIBER.getDailyValue()) * 100), true);
                    String fiberText = getString(R.string.g_progress, fiber, (int) RecommendedDailyValues.FIBER.getDailyValue());
                    fiberProgress.setText(fiberText);
                }
                // Carbs progress bar
                if (carbs != null) {
                    carbBar.setProgress( Math.round((carbs/RecommendedDailyValues.CARBS.getDailyValue()) * 100), true);
                    String carbsText = getString(R.string.g_progress, carbs, (int) RecommendedDailyValues.CARBS.getDailyValue());
                    carbProgress.setText(carbsText);
                }
                //
                if (protein != null) {
                    proteinBar.setProgress( Math.round((protein/RecommendedDailyValues.PROTEIN.getDailyValue()) * 100), true);
                    String proteinText = getString(R.string.g_progress, protein, (int) RecommendedDailyValues.PROTEIN.getDailyValue());
                    proteinProgress.setText(proteinText);
                }

                if (carbs != null && fat != null && protein != null) {
                    List<PieEntry> energyEntries = new ArrayList<>();
                    energyEntries.add(new PieEntry(fat * 9, "Fats"));
                    energyEntries.add(new PieEntry(protein * 4, "Protein"));
                    energyEntries.add(new PieEntry(carbs * 4, "Carbs"));
                    PieDataSet energySet = new PieDataSet(energyEntries, "Energy");
                    List<Integer> colors = new ArrayList<>();
                    colors.add(Color.parseColor("#FFC300"));
                    colors.add(Color.parseColor("#C70039"));
                    colors.add(Color.parseColor("#89CFF0"));
                    colors.add(Color.GRAY);
                    energySet.setColors(colors);
                    PieData energyData = new PieData(energySet);
                    energyData.setValueTextSize(16);
                    energyData.setValueTextColor(Color.parseColor("#EAF0F8"));

                    macronutrientChart.setDrawHoleEnabled(true);
                    macronutrientChart.setHoleRadius(40f);
                    macronutrientChart.setHoleColor(Color.parseColor("#545454"));
                    macronutrientChart.setCenterText("Macronutrients \n Ratio");
                    macronutrientChart.setCenterTextColor(Color.parseColor("#EAF0F8"));
                    macronutrientChart.setUsePercentValues(true);
                    macronutrientChart.setData(energyData);
                    macronutrientChart.getLegend().setEnabled(false);
                    macronutrientChart.getDescription().setText("");
                    macronutrientChart.invalidate(); // refresh
                }

                //close the popup window on button click
                closePopUpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }
        });


    }

    @Override
    public void onIngredientClicked(int position) {
        List<IngredientModel> ingredients = ingredientsAdapter.getCurrentList();
        IngredientModel ingredient = ingredients.get(position);
        System.out.println(ingredient.getDescription() + " clicked!");
        // TODO - make nutrition pop-up when ingredient clicked.
        // Then, time to move on to meal planning!!
    }

    public void addToMealPlanButtonClicked(View view) {
        // TODO
    }

    public void addToShoppingListButtonClicked(View view) {
        // TODO
    }

    public void deleteRecipeButtonClicked(View view) {
        int recipeId = recipe.getId();
        mRecipeViewModel.delete(recipeId);
        Intent intent = new Intent(this, CookBookHomeActivity.class);
        startActivity(intent);
    }
}
