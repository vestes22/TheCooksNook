package com.codelovely.thecooksnook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.codelovely.thecooksnook.adapters.RecipeAdapter;
import com.codelovely.thecooksnook.data.entities.Recipe;
import com.codelovely.thecooksnook.models.IngredientModel;
import com.codelovely.thecooksnook.models.MealPlan;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.restmodels.SearchResultFood;
import com.codelovely.thecooksnook.viewmodels.AddMealPlanViewModel;
import com.codelovely.thecooksnook.viewmodels.AddRecipeViewModel;
import com.codelovely.thecooksnook.viewmodels.CookBookViewModel;
import com.codelovely.thecooksnook.viewmodels.NutritionProfileViewModel;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AddMealPlanActivity extends AppCompatActivity implements RecipeAdapter.RecipeListener {
    RecipeAdapter recipeAdapter;
    LinearLayout linearLayout;
    PopupWindow cookbookWindow;
    TextView breakfastText, lunchText, dinnerText, appetizerText;
    AddMealPlanViewModel mAddMealPlanViewModel;
    Observer<Map<String, RecipeModel>> mealPlanRecipeObserver;
    NutritionProfileFragment nutritionProfileFragment;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal_plan);

        mAddMealPlanViewModel = new ViewModelProvider(this).get(AddMealPlanViewModel.class);


        breakfastText = findViewById(R.id.addMealPlan_breakfastRecipeText);
        lunchText = findViewById(R.id.addMealPlan_lunchRecipeText);
        dinnerText = findViewById(R.id.addMealPlan_dinnerRecipeText);
        appetizerText = findViewById(R.id.addMealPlan_appetizerRecipeText);
        linearLayout = findViewById(R.id.addMealPlan_linearLayout);
        nutritionProfileFragment = (NutritionProfileFragment) getSupportFragmentManager().findFragmentById(R.id.addMealPlan_nutritionProfileFragment);
        datePicker = findViewById(R.id.addMealPlan_datePicker);

        mealPlanRecipeObserver = new Observer<Map<String, RecipeModel>>() {
            @Override
            public void onChanged(Map<String, RecipeModel> mealPlanRecipes) {
                if (mealPlanRecipes.containsKey(RecipeCategory.BREAKFAST.toString())) {
                    RecipeModel breakfastRecipe = mealPlanRecipes.get(RecipeCategory.BREAKFAST.toString());
                    breakfastText.setText(breakfastRecipe.getName());
                }
                if (mealPlanRecipes.containsKey(RecipeCategory.LUNCH.toString())) {
                    RecipeModel lunchRecipe = mealPlanRecipes.get(RecipeCategory.LUNCH.toString());
                    lunchText.setText(lunchRecipe.getName());
                }
                if (mealPlanRecipes.containsKey(RecipeCategory.DINNER.toString())) {
                    RecipeModel dinnerRecipe = mealPlanRecipes.get(RecipeCategory.DINNER.toString());
                    dinnerText.setText(dinnerRecipe.getName());
                }
                if (mealPlanRecipes.containsKey(RecipeCategory.APPETIZER.toString())) {
                    RecipeModel appetizerRecipe = mealPlanRecipes.get(RecipeCategory.APPETIZER.toString());
                    appetizerText.setText(appetizerRecipe.getName());
                }
            }
        };
        mAddMealPlanViewModel.getMealPlanRecipes().observe(this, mealPlanRecipeObserver);
    }

    public void onBreakfastButtonClicked(View view) {
        recipePopup(RecipeCategory.BREAKFAST.toString());
    }

    public void onLunchButtonClicked(View view) {
        recipePopup(RecipeCategory.LUNCH.toString());
    }

    @Override
    public void onRecipeClicked(int position) {
        List<RecipeModel> recipes = recipeAdapter.getCurrentList();
        int recipeId = recipes.get(position).getId();
        RecipeModel recipe = mAddMealPlanViewModel.getRecipeById(recipeId);

        mAddMealPlanViewModel.updateHashMap(recipe);
        nutritionProfileFragment.getNutritionProfileViewModel().updateHashMap(recipe);
        System.out.println(recipe.getDescription() + " Recipe clicked!");
        cookbookWindow.dismiss();
    }

    public void onAppetizersButtonClicked(View view) {
        recipePopup(RecipeCategory.APPETIZER.toString());
    }

    public void onDinnerButtonClicked(View view) {
        recipePopup(RecipeCategory.DINNER.toString());

    }

    private void recipePopup(String category) {
        CookBookViewModel mCookBookViewModel = new ViewModelProvider(this).get(CookBookViewModel.class);

        LayoutInflater layoutInflater = (LayoutInflater) AddMealPlanActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        @SuppressLint("InflateParams") View customView = layoutInflater.inflate(R.layout.activity_cook_book, null);

        //instantiate popup window
        cookbookWindow = new PopupWindow(customView, ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);

        //display the popup window
        cookbookWindow.showAtLocation(linearLayout, Gravity.TOP|Gravity.START, 0, 0);

        RecyclerView recipeRv = customView.findViewById(R.id.cookBook_recipeRecyclerview);
        recipeAdapter = new RecipeAdapter(new RecipeAdapter.RecipeDiff(), this);
        recipeRv.setAdapter(recipeAdapter);
        recipeRv.setLayoutManager(new LinearLayoutManager(this));

        final Observer<List<RecipeModel>> recipeListObserver = new Observer<List<RecipeModel>>() {
          @Override
          public void onChanged(@Nullable final List<RecipeModel> recipeList) {
              recipeAdapter.submitList(null);
              recipeAdapter.submitList(recipeList);
          }
        };

        mCookBookViewModel.getRecipesByCategory().observe(this, recipeListObserver);

        mCookBookViewModel.setRecipesByCategory(category);

        final Button button = customView.findViewById(R.id.cookbook_closeButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cookbookWindow.dismiss();
            }
        });
    }

    public void saveMealPlanButtonClicked(View view) {
        MealPlan mealPlan = new MealPlan();

        int month = datePicker.getMonth() + 1;
        int day = datePicker.getDayOfMonth();
        int year = datePicker.getYear();
        LocalDate localDate = LocalDate.of(year, month, day);
        mealPlan.setDay(day);
        mealPlan.setYear(year);
        String monthString = localDate.getMonth().toString().toLowerCase();
        String formattedMonth = monthString.substring(0, 1).toUpperCase() + monthString.substring(1);
        mealPlan.setMonth(formattedMonth);
        mealPlan.setRecipes(mAddMealPlanViewModel.getRecipes());
    }
}
