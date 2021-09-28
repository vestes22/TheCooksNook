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
import android.util.Log;
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
import com.codelovely.thecooksnook.models.IngredientModel;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.restmodels.FoodNutrient;
import com.codelovely.thecooksnook.viewmodels.RecipeViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.text.nlclassifier.NLClassifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RecipeActivity extends AppCompatActivity implements SavedIngredientsListAdapter.IngredientsListener {
    RecipeViewModel mRecipeViewModel;
    RecipeModel recipe;
    TextView recipeName, recipeDescription, recipeInstructions, recipeNumServings;
    SavedIngredientsListAdapter ingredientsAdapter;
    NutrientListAdapter recipeNutrientAdapter;
    Button nutritionProfileButton, closeRecipeNutrientPopup, closeIngredientNutrientPopup;
    LinearLayout linearLayout;
    PopupWindow recipeNutrientWindow, ingredientNutrientWindow;

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
        recipeNutrientAdapter = new NutrientListAdapter(new NutrientListAdapter.NutrientsDiff());
        recipeNutrientAdapter.submitList(recipe.getRecipeNutrientsPerServing());
        nutrientsRv.setAdapter(recipeNutrientAdapter);
        nutrientsRv.setLayoutManager(new LinearLayoutManager(this));

        String ingredientNames = "";
        List<IngredientModel> ingredients = recipe.getIngredients();
        for (IngredientModel ingredient : ingredients) {
            ingredientNames = ingredientNames + ingredient.getDescription() + ", ";
        }
        NLClassifier classifier;
        try {
            classifier = NLClassifier.createFromFile(this, "model.tflite");
            List<Category> results = classifier.classify(ingredientNames);
            float highestScore = results.get(0).getScore();
            Category topResult = results.get(0);
            for (Category result : results) {
                if (result.getScore() > highestScore) {
                    highestScore = result.getScore();
                    topResult = result;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onIngredientClicked(int position) {
        TextView ingredientNutritionItemName, recipeServingAmount, originalServingAmount;
        NutrientListAdapter recipeServingAdapter, originalServingAdapter;
        List<IngredientModel> ingredients = ingredientsAdapter.getCurrentList();
        IngredientModel ingredient = ingredients.get(position);

        List<FoodNutrient> nutrients = ingredient.getFoodNutrientsAdjustedForRecipe();

        //instantiate the popup.xml layout file
        LayoutInflater layoutInflater = (LayoutInflater) RecipeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        @SuppressLint("InflateParams") View customView = layoutInflater.inflate(R.layout.ingredient_nutrition_profile, null);

        //instantiate popup window
        ingredientNutrientWindow = new PopupWindow(customView, ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);

        //display the popup window
        ingredientNutrientWindow.showAtLocation(linearLayout, Gravity.TOP | Gravity.START, 0, 0);

        ingredientNutritionItemName = customView.findViewById(R.id.ingredientNutritionProfile_itemName);
        recipeServingAmount = customView.findViewById(R.id.ingredientNutritionProfile_recipeAmount);
        originalServingAmount = customView.findViewById(R.id.ingredientNutritionProfile_originalAmount);
        closeIngredientNutrientPopup = customView.findViewById(R.id.ingredientNutritionProfile_closePopUpButton);

        ingredientNutritionItemName.setText(ingredient.getDescription());

        String recipeAmount = getString(R.string.recipe_serving, ingredient.getAmountInRecipe(), ingredient.getServingSizeUnit());
        recipeServingAmount.setText(recipeAmount);

        String originalAmount = getString(R.string.original_serving, 100.0f, ingredient.getServingSizeUnit());
        originalServingAmount.setText(originalAmount);

        //Ingredient Nutrition RecyclerView
        RecyclerView recipeServingRv = customView.findViewById(R.id.ingredientNutritionProfile_recipeServingNutrientsRecyclerView);
        recipeServingAdapter = new NutrientListAdapter(new NutrientListAdapter.NutrientsDiff());
        recipeServingAdapter.submitList(ingredient.getFoodNutrientsAdjustedForRecipe());
        recipeServingRv.setAdapter(recipeServingAdapter);
        recipeServingRv.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView originalServingRv = customView.findViewById(R.id.ingredientNutritionProfile_originalServingNutrientsRecyclerView);
        originalServingAdapter = new NutrientListAdapter(new NutrientListAdapter.NutrientsDiff());
        originalServingAdapter.submitList(ingredient.getFoodNutrientsPerOriginalServingSize());
        originalServingRv.setAdapter(originalServingAdapter);
        originalServingRv.setLayoutManager(new LinearLayoutManager(this));

        //close the popup window on button click
        closeIngredientNutrientPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientNutrientWindow.dismiss();
            }
        });

    }

    public void recipeNutritionProfileButtonClicked(View view) {
        TextView calorieProgress, fatProgress, fiberProgress, carbProgress, proteinProgress, recipeNutritionItemName;
        ProgressBar calorieBar, fatBar, fiberBar, carbBar, proteinBar;
        PieChart macronutrientChart;
        RadarChart radarChart;

        //instantiate the popup.xml layout file
        LayoutInflater layoutInflater = (LayoutInflater) RecipeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        @SuppressLint("InflateParams") View customView = layoutInflater.inflate(R.layout.fragment_nutrition_profile, null);

        closeRecipeNutrientPopup = customView.findViewById(R.id.recipeNutritionProfile_closePopUpButton);
        recipeNutritionItemName = customView.findViewById(R.id.recipeNutritionProfile_itemName);

        macronutrientChart = customView.findViewById(R.id.recipeNutritionProfile_macronutrientPieChart);
        radarChart = customView.findViewById(R.id.recipeNutritionProfile_radarChart);

        calorieBar = customView.findViewById(R.id.recipeNutritionProfile_calorieDeterminateBar);
        fatBar = customView.findViewById(R.id.recipeNutritionProfile_fatDeterminateBar);
        fiberBar = customView.findViewById(R.id.recipeNutritionProfile_fiberDeterminateBar);
        carbBar = customView.findViewById(R.id.recipeNutritionProfile_carbsDeterminateBar);
        proteinBar = customView.findViewById(R.id.recipeNutritionProfile_proteinDeterminateBar);

        calorieProgress = customView.findViewById(R.id.recipeNutritionProfile_calorieProgressText);
        fatProgress = customView.findViewById(R.id.recipeNutritionProfile_fatProgressText);
        fiberProgress = customView.findViewById(R.id.recipeNutritionProfile_fiberProgressText);
        carbProgress = customView.findViewById(R.id.recipeNutritionProfile_carbsProgressText);
        proteinProgress = customView.findViewById(R.id.recipeNutritionProfile_proteinProgressText);

        //instantiate popup window
        recipeNutrientWindow = new PopupWindow(customView, ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);

        //display the popup window
        recipeNutrientWindow.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);

        recipeNutritionItemName.setText(recipe.getName());

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
            } else if (nutrientId == 1004) {
                fat = nutrient.getAmount();
            } else if (nutrientId == 1005) {
                carbs = nutrient.getAmount();
            } else if (nutrientId == 1079) {
                fiber = nutrient.getAmount();
            } else if (nutrientId == 1003) {
                protein = nutrient.getAmount();
            }
        }
        String ingredientNames = "";
        List<IngredientModel> ingredients = recipe.getIngredients();
        for (IngredientModel ingredient : ingredients) {
            ingredientNames = ingredientNames + ingredient.getDescription() + ", ";
        }
        NLClassifier classifier;
        List<RadarEntry> radarEntries = new ArrayList<>();
        final List<String> labelValues = new ArrayList<>();

        try {
            classifier = NLClassifier.createFromFile(this, "model.tflite");
            List<Category> results = classifier.classify(ingredientNames);
            for (int i = 0; i < results.size(); i++) {
                radarEntries.add(new RadarEntry(results.get(i).getScore(), results.get(i).getLabel()));
                labelValues.add(results.get(i).getLabel());
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ERROR", "Classifier could not be created.");
        }

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelValues));
        xAxis.setTextSize(16f);
        xAxis.setLabelRotationAngle(-45);

        String[] yAxisLabels = new String[20];
        for (int i = 0; i < yAxisLabels.length; i++) {
            yAxisLabels[i] = "";
        }

        YAxis yAxis = radarChart.getYAxis();
        yAxis.setTextColor(Color.parseColor("#EAF0F8"));
        yAxis.setTextSize(10);
        String longestLabel = xAxis.getLongestLabel();
        System.out.println("Radar Chart longest label: " + longestLabel);
        xAxis.setTextColor(Color.parseColor("#EAF0F8"));
        RadarDataSet dataSet = new RadarDataSet(radarEntries, "Likelihood Score");
        dataSet.setValueFormatter(new IndexAxisValueFormatter(yAxisLabels));
        dataSet.setLineWidth(2.5f);
        dataSet.setFillColor(Color.parseColor("#FF6600"));
        dataSet.setDrawFilled(true);
        dataSet.setColor(Color.parseColor("#FF6600"));
        RadarData radarData = new RadarData(dataSet);
        radarChart.setData(radarData);
        radarChart.setWebColor(Color.parseColor("#EAF0F8"));
        radarChart.setWebColorInner(Color.parseColor("#EAF0F8"));
        radarChart.getDescription().setText("");
        radarChart.invalidate();


        // Calorie progress bar
        if (calories != null) {
            calorieBar.setProgress(Math.round((calories / RecommendedDailyValues.ENERGY.getDailyValue()) * 100));
            String calorieText = getString(R.string.kCal_progress, calories, (int) RecommendedDailyValues.ENERGY.getDailyValue());
            calorieProgress.setText(calorieText);
        }
        // Total fats progress bar
        if (fat != null) {
            fatBar.setProgress(Math.round((fat / RecommendedDailyValues.TOTAL_FAT.getDailyValue()) * 100), true);
            String fatText = getString(R.string.g_progress, fat, (int) RecommendedDailyValues.TOTAL_FAT.getDailyValue());
            fatProgress.setText(fatText);
        }
        // Fiber progress bar
        if (fiber != null) {
            fiberBar.setProgress(Math.round((fiber / RecommendedDailyValues.FIBER.getDailyValue()) * 100), true);
            String fiberText = getString(R.string.g_progress, fiber, (int) RecommendedDailyValues.FIBER.getDailyValue());
            fiberProgress.setText(fiberText);
        }
        // Carbs progress bar
        if (carbs != null) {
            carbBar.setProgress(Math.round((carbs / RecommendedDailyValues.CARBS.getDailyValue()) * 100), true);
            String carbsText = getString(R.string.g_progress, carbs, (int) RecommendedDailyValues.CARBS.getDailyValue());
            carbProgress.setText(carbsText);
        }
        //
        if (protein != null) {
            proteinBar.setProgress(Math.round((protein / RecommendedDailyValues.PROTEIN.getDailyValue()) * 100), true);
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
        closeRecipeNutrientPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeNutrientWindow.dismiss();
            }
        });

    }
}