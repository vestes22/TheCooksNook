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


import com.codelovely.thecooksnook.models.restmodels.FoodNutrient;
import com.codelovely.thecooksnook.viewmodels.NutritionProfileViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class NutritionProfileFragment extends Fragment {

    private NutritionProfileViewModel mNutritionProfileViewModel;
    private Observer recipeDataObserver;
    private TextView calorieProgress, fatProgress, fiberProgress, carbProgress, proteinProgress, recipeNutritionItemName;
    private ProgressBar calorieBar, fatBar, fiberBar, carbBar, proteinBar;
    private PieChart macronutrientChart;

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

            Float calories = null;
            Float fat = null;
            Float protein = null;
            Float carbs = null;
            Float fiber = null;

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
    }

    public NutritionProfileViewModel getNutritionProfileViewModel () {
        return mNutritionProfileViewModel;
    }
}
