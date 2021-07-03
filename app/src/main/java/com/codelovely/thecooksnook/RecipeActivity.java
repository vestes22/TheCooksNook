package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.codelovely.thecooksnook.adapters.NutrientListAdapter;
import com.codelovely.thecooksnook.adapters.SavedIngredientsListAdapter;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.viewmodels.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;


public class RecipeActivity extends AppCompatActivity {
    RecipeViewModel mRecipeViewModel;
    RecipeModel recipe;
    TextView recipeName, recipeDescription, recipeInstructions, recipeNumServings;
    SavedIngredientsListAdapter ingredientsAdapter;
    NutrientListAdapter nutrientAdapter;
    Button nutritionProfileButton, closePopUpBtn;
    LinearLayout linearLayout;
    PopupWindow popupWindow;
    AnyChartView vitaminCircularGauge;

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
        recipeNumServings.setText("Servings: " + recipe.getNumServings());


        RecyclerView ingredientsRv = findViewById(R.id.recipe_ingredientsListRecyclerView);
        ingredientsAdapter = new SavedIngredientsListAdapter(new SavedIngredientsListAdapter.IngredientsDiff(), this);
        ingredientsAdapter.submitList(recipe.getIngredients());
        ingredientsRv.setAdapter(ingredientsAdapter);
        ingredientsRv.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView nutrientsRv = findViewById(R.id.recipe_recipeNutritionRecyclerView);
        nutrientAdapter = new NutrientListAdapter(new NutrientListAdapter.NutrientsDiff(), this);
        nutrientAdapter.submitList(recipe.getRecipeNutrientsPerServing());
        nutrientsRv.setAdapter(nutrientAdapter);
        nutrientsRv.setLayoutManager(new LinearLayoutManager(this));


        nutritionProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) RecipeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.nutrition_profile,null);

                closePopUpBtn = (Button) customView.findViewById(R.id.closePopUpButton);
                vitaminCircularGauge = (AnyChartView) customView.findViewById(R.id.nutritionProfile_vitaminCircularGauge);
                vitaminCircularGauge.setProgressBar(customView.findViewById(R.id.progress_bar));

                //instantiate popup window
                popupWindow = new PopupWindow(customView, ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);

                //display the popup window
                popupWindow.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);

                Pie pie = AnyChart.pie();
                List<DataEntry> data = new ArrayList<>();
                data.add(new ValueDataEntry("Apples", 6371664));
                data.add(new ValueDataEntry("Pears", 789622));
                data.add(new ValueDataEntry("Bananas", 7216301));
                data.add(new ValueDataEntry("Grapes", 1486621));
                data.add(new ValueDataEntry("Oranges", 1200000));

                pie.data(data);

                pie.title("Fruits imported in 2015 (in kg)");

                pie.labels().position("outside");

                pie.legend().title().enabled(true);
                pie.legend().title()
                        .text("Retail channels")
                        .padding(0d, 0d, 10d, 0d);

                pie.legend()
                        .position("center-bottom")
                        .itemsLayout(LegendLayout.HORIZONTAL)
                        .align(Align.CENTER);

                vitaminCircularGauge.setChart(pie);



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
