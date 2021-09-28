package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codelovely.thecooksnook.models.IngredientModel;
import com.codelovely.thecooksnook.models.MealPlan;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.UserModel;
import com.codelovely.thecooksnook.models.restmodels.FoodNutrient;
import com.codelovely.thecooksnook.viewmodels.UserProfileViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.text.nlclassifier.NLClassifier;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class UserProfileActivity extends AppCompatActivity {
    UserProfileViewModel mUserProfileViewModel;
    TextView activityTitle, averageValuesText, calorieProgress, proteinProgress, carbProgress, fiberProgress, fatProgress;
    TextView recommendedRecipeCardTitle, recommendedRecipeCardDescription, recommendedRecipeTitle, recommendedRecipeDescription;
    DatePicker startDate, endDate;
    Button calculateMetricsButton;
    ProgressBar progressBar, calorieBar, fiberBar, proteinBar, carbBar, fatBar;
    NutritionProfileFragment nutritionProfileFragment;
    CardView macronutrientCard, lineGraphCard, recommendedRecipeCard;
    LineChart lineChart;
    RadarChart radarChart;
    PieChart macronutrientChart;
    Observer averageValuesDataObserver, recipeObserver;
    Float calories, fat, protein, carbs, fiber;
    int calorieId, fatId, proteinId, carbId, fiberId;
    List<MealPlan> mealsInDateRange;
    Map<String, Float> resultsMap;
    CardView recipeCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mealsInDateRange = new ArrayList<>();
        resultsMap = new HashMap<>();

        mUserProfileViewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);
        activityTitle = findViewById(R.id.userProfile_activityTitle);
        startDate = findViewById(R.id.userProfile_averageValuesStartDatePicker);
        endDate = findViewById(R.id.userProfile_averageValuesEndDatePicker);
        calculateMetricsButton = findViewById(R.id.userProfile_calculateDailyAverageButton);
        progressBar = findViewById(R.id.userProfile_metricsProgressBar);
        nutritionProfileFragment = (NutritionProfileFragment) getSupportFragmentManager().findFragmentById(R.id.addMealPlan_nutritionProfileFragment);
        macronutrientCard = findViewById(R.id.userProfile_macronutrientCard);
        lineGraphCard = findViewById(R.id.userProfile_lineGraphCard);
        averageValuesText = findViewById(R.id.userProfile_itemName);
        calorieBar = findViewById(R.id.userProfile_calorieDeterminateBar);
        fiberBar = findViewById(R.id.userProfile_fiberDeterminateBar);
        carbBar = findViewById(R.id.userProfile_carbsDeterminateBar);
        proteinBar = findViewById(R.id.userProfile_proteinDeterminateBar);
        fatBar = findViewById(R.id.userProfile_fatDeterminateBar);
        calorieProgress = findViewById(R.id.userProfile_calorieProgressText);
        fiberProgress = findViewById(R.id.userProfile_fiberProgressText);
        carbProgress = findViewById(R.id.userProfile_carbsProgressText);
        proteinProgress = findViewById(R.id.userProfile_proteinProgressText);
        fatProgress = findViewById(R.id.userProfile_fatProgressText);
        macronutrientChart = findViewById(R.id.userProfile_macronutrientPieChart);
        radarChart = findViewById(R.id.userProfile_radarChart);
        lineChart = findViewById(R.id.userProfile_caloriesLineChart);
        recommendedRecipeCard = findViewById(R.id.userProfile_recommendedRecipeCard);
        recommendedRecipeCardTitle = findViewById(R.id.userProfile_recommendedRecipe_title);
        recommendedRecipeTitle = findViewById(R.id.userProfile_recommendedRecipeTitle);
        recommendedRecipeCardDescription = findViewById(R.id.userProfile_recommendedRecipeCardDescription);
        recommendedRecipeDescription = findViewById(R.id.userProfile_recommendedRecipeDescription);
        recipeCard = findViewById(R.id.userProfile_recommendedRecipe);

        calories = 0f;
        protein = 0f;
        fiber = 0f;
        fat = 0f;
        carbs = 0f;

        calorieId = RecommendedDailyValues.ENERGY.getNutrientId();
        fatId = RecommendedDailyValues.TOTAL_FAT.getNutrientId();
        proteinId = RecommendedDailyValues.PROTEIN.getNutrientId();
        carbId = RecommendedDailyValues.CARBS.getNutrientId();
        fiberId = RecommendedDailyValues.FIBER.getNutrientId();

        progressBar.setVisibility(View.GONE);
        lineGraphCard.setVisibility(View.GONE);
        macronutrientCard.setVisibility(View.GONE);
        recommendedRecipeCard.setVisibility(View.GONE);

        recipeObserver = new Observer<List<RecipeModel>> () {
            @Override
            public void onChanged(List<RecipeModel> recipeModels) {
                setRecommendedRecipe(recipeModels);
            }
        };

        mUserProfileViewModel.getRecipes().observe(this, recipeObserver);

        averageValuesDataObserver = new Observer<Map<Integer, FoodNutrient>>() {
            @Override
            public void onChanged(Map<Integer, FoodNutrient> nutritionNutrients) {
                setNutrients(nutritionNutrients);
                updateUI(nutritionNutrients);
            }
        };
        mUserProfileViewModel.getAverageDailyNutrients().observe(this, averageValuesDataObserver);
    }

    public void calculateDailyAverageButtonClicked(View view) {
        progressBar.setVisibility(View.VISIBLE);

        int startMonth = startDate.getMonth() + 1;
        int startDay = startDate.getDayOfMonth();
        int startYear = startDate.getYear();
        LocalDate startLocalDate = LocalDate.of(startYear, startMonth, startDay);

        int endMonth = endDate.getMonth() + 1;
        int endDay = endDate.getDayOfMonth();
        int endYear = endDate.getYear();
        LocalDate endLocalDate = LocalDate.of(endYear, endMonth, endDay);

        averageValuesText.setText("Average daily nutritional intake between " + startLocalDate.toString() + " and " + endLocalDate.toString() + ".");
        mUserProfileViewModel.getUserMenuRecipesInDateRange(startLocalDate, endLocalDate);
        mealsInDateRange = mUserProfileViewModel.getMealsInDateRange();

    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        UserModel user = new UserModel();
        user.setFirstName(account.getGivenName());
        user.setLastName(account.getFamilyName());
        user.setUserId(account.getId());

         */
        UserModel userModel = new UserModel();
        userModel.setFirstName("Default user");
        userModel.setLastName("Default user");
        userModel.setUserId("Default user");
        mUserProfileViewModel.setUser(userModel);
        activityTitle.setText(userModel.getFirstName() + "'s Metrics");
    }

    private void updateUI(Map<Integer, FoodNutrient> totalNutrients) {

        setProgressBars();
        setPieChart();
        setRadarChart();
        progressBar.setVisibility(View.GONE);
    }

    private void setNutrients(Map<Integer, FoodNutrient> totalNutrients) {
        if (totalNutrients.containsKey(calorieId)) {
            calories = Objects.requireNonNull(totalNutrients.get(calorieId)).getAmount();
        }

        if (totalNutrients.containsKey(fatId)) {
            fat = Objects.requireNonNull(totalNutrients.get(fatId)).getAmount();
        }

        if (totalNutrients.containsKey(proteinId)) {
            protein = Objects.requireNonNull(totalNutrients.get(proteinId)).getAmount();
        }

        if (totalNutrients.containsKey(carbId)) {
            carbs = Objects.requireNonNull(totalNutrients.get(carbId)).getAmount();
        }

        if (totalNutrients.containsKey(fiberId)) {
            fiber = Objects.requireNonNull(totalNutrients.get(fiberId)).getAmount();
        }
    }

    private void setProgressBars() {

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
        // Protein progress bar
        if (protein != null) {
            proteinBar.setProgress( Math.round((protein/RecommendedDailyValues.PROTEIN.getDailyValue()) * 100), true);
            String proteinText = getString(R.string.g_progress, protein, (int) RecommendedDailyValues.PROTEIN.getDailyValue());
            proteinProgress.setText(proteinText);
        }
    }

    private void setPieChart() {
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

            macronutrientCard.setVisibility(View.VISIBLE);
        }
    }

    private void setRadarChart() {
        // Sets data for the Radar Chart
        List<RecipeModel> totalRecipes = new ArrayList<>();
        List<Entry> calorieEntries = new ArrayList<>();

        int numEntries = 0;
        for (MealPlan mealPlan : mUserProfileViewModel.getMealsInDateRange()) {
            mealsInDateRange.add(mealPlan);
            totalRecipes.addAll(mealPlan.getRecipes());
            float dailyCalories = 0;
            for (RecipeModel recipe : mealPlan.getRecipes()) {
                for (FoodNutrient foodNutrient : recipe.getRecipeNutrientsPerServing()) {
                    if (foodNutrient.getNutrient().getId() == calorieId) {
                        dailyCalories += foodNutrient.getAmount();
                    }
                }
            }
            calorieEntries.add(new Entry(numEntries, dailyCalories));
            numEntries++;
        }

        for (RecipeModel recipe : totalRecipes) {

            String ingredientNames = "";
            List<IngredientModel> ingredients = recipe.getIngredients();

            for (IngredientModel ingredient : ingredients) {
                ingredientNames = ingredientNames + ingredient.getDescription() + ", ";
            }

            // Uses the machine learning model to classify each recipe into a category based on its ingredients.
            // Sums the total category scores for all recipes.
            NLClassifier classifier;
            try {
                classifier = NLClassifier.createFromFile(this, "model.tflite");
                List<Category> results = classifier.classify(ingredientNames);
                for (int i = 0; i < results.size(); i++) {

                    if (resultsMap.containsKey(results.get(i).getLabel())) {
                        float addedNumber = results.get(i).getScore();
                        addedNumber = addedNumber + resultsMap.get(results.get(i).getLabel());
                        resultsMap.replace(results.get(i).getLabel(), addedNumber);
                    } else {
                        resultsMap.put(results.get(i).getLabel(), results.get(i).getScore());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<RadarEntry> radarEntries = new ArrayList<>();
        final List<String> labelValues = new ArrayList<>();

        // Take the average of the category scores, so we can view the category averages for groups of recipes.
        for (Map.Entry score : resultsMap.entrySet()) {
            float averageScore = (float) score.getValue() / totalRecipes.size();
            resultsMap.replace((String) score.getKey(), averageScore);
            radarEntries.add(new RadarEntry(averageScore, (String) score.getKey()));
            labelValues.add((String) score.getKey());
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
        getRecipeOptions();
        setLineChart(calorieEntries);
    }

    private void setLineChart(List<Entry> calorieEntries) {
        // Set Line Chart Data

        for (Entry entry : calorieEntries) {
            System.out.println("calorieEntry: " + entry.toString());
        }
        LineDataSet lineDataSet = new LineDataSet(calorieEntries, "Calories consumed over time.");
        lineDataSet.setColor(Color.parseColor("#FF6600"));
        LineData lineData = new LineData(lineDataSet);
        YAxis leftYAxis = lineChart.getAxisLeft();
        leftYAxis.setTextColor(Color.parseColor("#EAF0F8"));
        leftYAxis.setTextSize(12);
        YAxis rightYAxis = lineChart.getAxisRight();
        rightYAxis.setTextColor(Color.parseColor("#EAF0F8"));
        rightYAxis.setTextSize(12);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#EAF0F8"));
        xAxis.setTextSize(12);
        lineChart.setData(lineData);
        lineChart.invalidate();

        lineGraphCard.setVisibility(View.VISIBLE);
    }

    private void getRecipeOptions() {
        Random rand = new Random();
        int mealType = rand.nextInt(4);
        System.out.println("Random int: " + mealType);

        if (mealType == 0) {
            mUserProfileViewModel.setRecipesByCategory(RecipeCategory.BREAKFAST.toString());
        }
        else if (mealType == 1) {
            mUserProfileViewModel.setRecipesByCategory(RecipeCategory.LUNCH.toString());
        }
        else if (mealType == 2) {
            mUserProfileViewModel.setRecipesByCategory(RecipeCategory.DINNER.toString());
        }
        else if (mealType == 3) {
            mUserProfileViewModel.setRecipesByCategory(RecipeCategory.BREAKFAST.toString());
        }
    }

    private void setRecommendedRecipe(List<RecipeModel> recipes) {
        // Get the top 3 most popular categories from user's meal history

        String topCategoryKey = "";
        float topCategoryValue = 0.0f;
        for (Map.Entry categoryEntry : resultsMap.entrySet()) {
            if ((float) categoryEntry.getValue() > topCategoryValue) {
                topCategoryValue = (float) categoryEntry.getValue();
                topCategoryKey = (String) categoryEntry.getKey();
            }
        }

        List<RecipeModel> matchedRecipes = new ArrayList<>();
        for (RecipeModel recipeModel : recipes) {
            String ingredientNames = "";
            for (IngredientModel ingredientModel : recipeModel.getIngredients()) {
                ingredientNames += ingredientModel.getDescription() + " ";
            }

            NLClassifier classifier;
            try {
                classifier = NLClassifier.createFromFile(this, "model.tflite");
                List<Category> results = classifier.classify(ingredientNames);
                float highestScore = results.get(0).getScore();
                Category topResult = results.get(0);
                for (int i = 0; i < results.size(); i++) {
                    if (results.get(i).getScore() > highestScore) {
                        topResult = results.get(i);
                        highestScore = results.get(i).getScore();
                    }
                }
                if (topResult.getLabel().equals(topCategoryKey)) {
                    System.out.println("Match found!");
                    matchedRecipes.add(recipeModel);
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("ERROR", "Classifier could not be created.");
            }
        }

        if (matchedRecipes.size() != 0) {
            recommendedRecipeCardTitle.setText("We recommend this recipe for you!");
            recommendedRecipeCardDescription.setText("We recommended this recipe because it's categorized similarly to other recipes you have enjoyed.");
            recommendedRecipeTitle.setText(matchedRecipes.get(0).getName());
            recommendedRecipeDescription.setText(matchedRecipes.get(0).getDescription());
            recipeCard.setVisibility(View.VISIBLE);
            recipeCard.setClickable(true);
            recipeCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserProfileActivity.this, RecipeActivity.class);
                    intent.putExtra("recipeId", matchedRecipes.get(0).getId());
                    startActivity(intent);
                }
            });
        }
        else {
            recommendedRecipeCardTitle.setText("We don't have any recommendations.");
            recommendedRecipeCardDescription.setText("We don't have enough data to recommend a recipe to you right now.");
            recommendedRecipeTitle.setText("");
            recommendedRecipeDescription.setText("");
            recipeCard.setVisibility(View.GONE);
        }
        recommendedRecipeCard.setVisibility(View.VISIBLE);
    }
}