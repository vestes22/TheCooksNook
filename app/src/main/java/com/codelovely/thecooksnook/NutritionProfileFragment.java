package com.codelovely.thecooksnook;

import android.graphics.Color;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.codelovely.thecooksnook.models.IngredientModel;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.restmodels.FoodNutrient;
import com.codelovely.thecooksnook.viewmodels.NutritionProfileViewModel;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class NutritionProfileFragment extends Fragment {

    private NutritionProfileViewModel mNutritionProfileViewModel;
    private Observer recipeDataObserver;
    private TextView calorieProgress, fatProgress, fiberProgress, carbProgress, proteinProgress, recipeNutritionItemName;
    private ProgressBar calorieBar, fatBar, fiberBar, carbBar, proteinBar;
    private PieChart macronutrientChart;
    private RadarChart radarChart;

    public NutritionProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNutritionProfileViewModel = new ViewModelProvider(this).get(NutritionProfileViewModel.class);

        recipeDataObserver = new Observer<Map<Integer, FoodNutrient>>() {
            @Override
            public void onChanged(Map<Integer, FoodNutrient> nutritionNutrients) {
                updateUI(nutritionNutrients);
            }
        };
        mNutritionProfileViewModel.getTotalNutrients().observe(this, recipeDataObserver);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nutrition_profile, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        View view = getView();
        if (view != null) {

            macronutrientChart = view.findViewById(R.id.recipeNutritionProfile_macronutrientPieChart);
            radarChart = view.findViewById(R.id.recipeNutritionProfile_radarChart);

            calorieBar = view.findViewById(R.id.recipeNutritionProfile_calorieDeterminateBar);
            fatBar = view.findViewById(R.id.recipeNutritionProfile_fatDeterminateBar);
            fiberBar = view.findViewById(R.id.recipeNutritionProfile_fiberDeterminateBar);
            carbBar = view.findViewById(R.id.recipeNutritionProfile_carbsDeterminateBar);
            proteinBar = view.findViewById(R.id.recipeNutritionProfile_proteinDeterminateBar);

            calorieProgress = view.findViewById(R.id.recipeNutritionProfile_calorieProgressText);
            fatProgress = view.findViewById(R.id.recipeNutritionProfile_fatProgressText);
            fiberProgress = view.findViewById(R.id.recipeNutritionProfile_fiberProgressText);
            carbProgress = view.findViewById(R.id.recipeNutritionProfile_carbsProgressText);
            proteinProgress = view.findViewById(R.id.recipeNutritionProfile_proteinProgressText);

            }
        }

        private void updateUI(Map<Integer, FoodNutrient> totalNutrients) {

            Float calories = 0f;
            Float fat = 0f;
            Float protein = 0f;
            Float carbs = 0f;
            Float fiber = 0f;

            int calorieId = RecommendedDailyValues.ENERGY.getNutrientId();
            int fatId = RecommendedDailyValues.TOTAL_FAT.getNutrientId();
            int proteinId = RecommendedDailyValues.PROTEIN.getNutrientId();
            int carbId = RecommendedDailyValues.CARBS.getNutrientId();
            int fiberId = RecommendedDailyValues.FIBER.getNutrientId();


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


            // Sets data for the Macronutrient Pie Chart
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


                // Sets data for the Radar Chart
                Map<String, Float> resultsMap = new HashMap<>();

                for (RecipeModel recipe : mNutritionProfileViewModel.getRecipes()) {

                    String ingredientNames = "";
                    List<IngredientModel> ingredients = recipe.getIngredients();

                    for (IngredientModel ingredient : ingredients) {
                        ingredientNames = ingredientNames + ingredient.getDescription() + ", ";
                    }

                    // Uses the machine learning model to classify each recipe into a category based on its ingredients.
                    // Sums the total category scores for all recipes.
                    NLClassifier classifier;
                    try {
                        classifier = NLClassifier.createFromFile(getActivity(), "model.tflite");
                        List<Category> results = classifier.classify(ingredientNames);
                        for (int i = 0; i < results.size(); i++) {

                            if (resultsMap.containsKey(results.get(i).getLabel())) {
                                float addedNumber = results.get(i).getScore();
                                addedNumber = addedNumber + resultsMap.get(results.get(i).getLabel());
                                resultsMap.replace(results.get(i).getLabel(), addedNumber);
                            }
                            else {
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
                    float averageScore = (float) score.getValue() / mNutritionProfileViewModel.getRecipes().size();
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

            }
    }

    public NutritionProfileViewModel getNutritionProfileViewModel () {
        return mNutritionProfileViewModel;
    }
}
