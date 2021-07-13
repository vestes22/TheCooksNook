package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.codelovely.thecooksnook.adapters.RecipeAdapter;
import com.codelovely.thecooksnook.data.entities.Recipe;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.viewmodels.AddMealPlanViewModel;
import com.codelovely.thecooksnook.viewmodels.AddRecipeViewModel;
import com.codelovely.thecooksnook.viewmodels.CookBookViewModel;

import java.util.List;

public class AddMealPlanActivity extends AppCompatActivity implements RecipeAdapter.RecipeListener {
    RecipeAdapter recipeAdapter;
    LinearLayout linearLayout;
    PopupWindow cookbookWindow;
    TextView breakfastText, lunchText, dinnerText, appetizerText;
    AddMealPlanViewModel mAddMealPlanViewModel;

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
    }

    public void saveMealPlanButtonClicked(View view) {
        // TODO
    }

    public void onBreakfastButtonClicked(View view) {
        recipePopup("BREAKFAST");
    }

    public void onLunchButtonClicked(View view) {
        recipePopup("LUNCH");
    }

    @Override
    public void onRecipeClicked(int position) {
        List<RecipeModel> recipes = recipeAdapter.getCurrentList();
        RecipeModel recipe = recipes.get(position);
        String category = recipe.getCategory();
        if (category.equals("BREAKFAST")) {
            mAddMealPlanViewModel.setBreakfastRecipe(recipe);
            breakfastText.setText(recipe.getName());
        }
        if (category.equals("LUNCH")) {
            mAddMealPlanViewModel.setLunchRecipe(recipe);
            lunchText.setText(recipe.getName());
        }
        if (category.equals("DINNER")) {
            mAddMealPlanViewModel.setDinnerRecipe(recipe);
            dinnerText.setText(recipe.getName());
        }
        if (category.equals("APPETIZER")) {
            mAddMealPlanViewModel.setLunchRecipe(recipe);
            appetizerText.setText(recipe.getName());
            updateFragment();
        }
        System.out.println(recipe.getDescription() + " Recipe clicked!");
        cookbookWindow.dismiss();
    }

    public void onAppetizersButtonClicked(View view) {
        recipePopup("APPETIZER");
    }

    public void onDinnerButtonClicked(View view) {
        recipePopup("DINNER");

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

        List<RecipeModel> recipes =  mCookBookViewModel.getRecipesByCategory(category);

        RecyclerView recipeRv = customView.findViewById(R.id.cookBook_recipeRecyclerview);
        recipeAdapter = new RecipeAdapter(new RecipeAdapter.RecipeDiff(), this);
        recipeAdapter.submitList(recipes);
        recipeRv.setAdapter(recipeAdapter);
        recipeRv.setLayoutManager(new LinearLayoutManager(this));

        final Button button = customView.findViewById(R.id.cookbook_closeButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cookbookWindow.dismiss();
            }
        });
    }

    private void updateFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.addMealPlan_nutritionProfileFragmentPlaceholder, new NutritionProfileFragment());
        ft.commit();

    }
}
