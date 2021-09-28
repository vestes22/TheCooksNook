package com.codelovely.thecooksnook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.codelovely.thecooksnook.adapters.RecipeAdapter;
import com.codelovely.thecooksnook.models.MealPlan;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.UserModel;
import com.codelovely.thecooksnook.viewmodels.AddMealPlanViewModel;
import com.codelovely.thecooksnook.viewmodels.CookBookViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class AddMealPlanActivity extends AppCompatActivity implements RecipeAdapter.RecipeListener {
    RecipeAdapter recipeAdapter;
    LinearLayout linearLayout;
    PopupWindow cookbookWindow;
    ListView breakfastText, lunchText, dinnerText, appetizerText, dessertText, drinkText;
    AddMealPlanViewModel mAddMealPlanViewModel;
    Observer<List<RecipeModel>> breakfastRecipeObserver, lunchRecipeObserver, dinnerRecipeObserver, appetizerRecipeObserver, dessertRecipeObserver, drinkRecipeObserver;
    NutritionProfileFragment nutritionProfileFragment;
    DatePicker datePicker;
    List<String> breakfastList, lunchList, dinnerList, appetizerList, dessertList, drinkList;
    ArrayAdapter<String> breakfastAdapter, lunchAdapter, dinnerAdapter, appetizerAdapter, dessertAdapter, drinkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal_plan);

        mAddMealPlanViewModel = new ViewModelProvider(this).get(AddMealPlanViewModel.class);
        breakfastList = new ArrayList<>();
        lunchList = new ArrayList<>();
        dinnerList = new ArrayList<>();
        appetizerList = new ArrayList<>();
        dessertList = new ArrayList<>();
        drinkList = new ArrayList<>();

        breakfastText = findViewById(R.id.addMealPlan_breakfastRecipeList);
        lunchText = findViewById(R.id.addMealPlan_lunchRecipeList);
        dinnerText = findViewById(R.id.addMealPlan_dinnerRecipeList);
        appetizerText = findViewById(R.id.addMealPlan_appetizerRecipeList);
        dessertText = findViewById(R.id.addMealPlan_dessertRecipeList);
        drinkText = findViewById(R.id.addMealPlan_drinkRecipeList);
        linearLayout = findViewById(R.id.addMealPlan_linearLayout);
        nutritionProfileFragment = (NutritionProfileFragment) getSupportFragmentManager().findFragmentById(R.id.addMealPlan_nutritionProfileFragment);
        datePicker = findViewById(R.id.addMealPlan_datePicker);

        breakfastAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1_edited, breakfastList);
        lunchAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1_edited, lunchList);
        dinnerAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1_edited, dinnerList);
        appetizerAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1_edited, appetizerList);
        dessertAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1_edited, dessertList);
        drinkAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1_edited, drinkList);

        breakfastText.setAdapter(breakfastAdapter);
        lunchText.setAdapter(lunchAdapter);
        dinnerText.setAdapter(dinnerAdapter);
        appetizerText.setAdapter(appetizerAdapter);
        dessertText.setAdapter(dessertAdapter);
        drinkText.setAdapter(drinkAdapter);

        breakfastRecipeObserver = new Observer<List<RecipeModel>>() {
            @Override
            public void onChanged(List<RecipeModel> breakfastRecipes) {
                breakfastList.clear();
                breakfastAdapter.clear();
                for (RecipeModel recipe : breakfastRecipes) {
                    breakfastList.add(recipe.getName());
                }
                justifyListViewHeightBasedOnChildren(breakfastText);
            }
        };
        mAddMealPlanViewModel.getBreakfastRecipes().observe(this, breakfastRecipeObserver);

        lunchRecipeObserver = new Observer<List<RecipeModel>>() {
            @Override
            public void onChanged(List<RecipeModel> lunchRecipes) {
                lunchList.clear();
                lunchAdapter.clear();
                for (RecipeModel recipe : lunchRecipes) {
                    lunchList.add(recipe.getName());
                }
                justifyListViewHeightBasedOnChildren(lunchText);
            }
        };
        mAddMealPlanViewModel.getLunchRecipes().observe(this, lunchRecipeObserver);

        dinnerRecipeObserver = new Observer<List<RecipeModel>>() {
            @Override
            public void onChanged(List<RecipeModel> dinnerRecipes) {
                dinnerList.clear();
                dinnerAdapter.clear();
                for (RecipeModel recipe : dinnerRecipes) {
                    dinnerList.add(recipe.getName());
                }
                justifyListViewHeightBasedOnChildren(dinnerText);
            }
        };
        mAddMealPlanViewModel.getDinnerRecipes().observe(this, dinnerRecipeObserver);

        appetizerRecipeObserver = new Observer<List<RecipeModel>>() {
            @Override
            public void onChanged(List<RecipeModel> appetizerRecipes) {
                appetizerList.clear();
                appetizerAdapter.clear();
                for (RecipeModel recipe : appetizerRecipes) {
                    appetizerList.add(recipe.getName());
                }
                justifyListViewHeightBasedOnChildren(appetizerText);
            }
        };
        mAddMealPlanViewModel.getAppetizerRecipes().observe(this, appetizerRecipeObserver);

        dessertRecipeObserver = new Observer<List<RecipeModel>>() {
            @Override
            public void onChanged(List<RecipeModel> dessertRecipes) {
                dessertList.clear();
                dessertAdapter.clear();
                for (RecipeModel recipe : dessertRecipes) {
                    dessertList.add(recipe.getName());
                    System.out.println(recipe.getName());
                }
                justifyListViewHeightBasedOnChildren(dessertText);
            }
        };
        mAddMealPlanViewModel.getDessertRecipes().observe(this, dessertRecipeObserver);

        drinkRecipeObserver = new Observer<List<RecipeModel>>() {
            @Override
            public void onChanged(List<RecipeModel> drinkRecipes) {
                drinkAdapter.clear();
                drinkList.clear();
                for (RecipeModel recipe : drinkRecipes) {
                    drinkList.add(recipe.getName());
                }
                justifyListViewHeightBasedOnChildren(drinkText);
            }
        };
        mAddMealPlanViewModel.getDrinkRecipes().observe(this, drinkRecipeObserver);

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
        mAddMealPlanViewModel.setUser(userModel);

        breakfastText.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                String recipeName = breakfastList.get(position);
                mAddMealPlanViewModel.removeBreakfast(recipeName);
                nutritionProfileFragment.getNutritionProfileViewModel().removeRecipe(recipeName);
            }
        });

        lunchText.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                String recipeName = lunchList.get(position);
                mAddMealPlanViewModel.removeLunch(recipeName);
                nutritionProfileFragment.getNutritionProfileViewModel().removeRecipe(recipeName);
            }
        });

        dinnerText.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String recipeName = dinnerList.get(position);
                mAddMealPlanViewModel.removeDinner(recipeName);
                nutritionProfileFragment.getNutritionProfileViewModel().removeRecipe(recipeName);
            }
        });

        appetizerText.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                String recipeName = appetizerList.get(position);
                mAddMealPlanViewModel.removeAppetizer(recipeName);
                nutritionProfileFragment.getNutritionProfileViewModel().removeRecipe(recipeName);
            }
        });

        dessertText.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                String recipeName = dessertList.get(position);
                mAddMealPlanViewModel.removeDessert(recipeName);
                nutritionProfileFragment.getNutritionProfileViewModel().removeRecipe(recipeName);
            }
        });

        drinkText.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                String recipeName = drinkList.get(position);
                mAddMealPlanViewModel.removeDrink(recipeName);
                nutritionProfileFragment.getNutritionProfileViewModel().removeRecipe(recipeName);
            }
        });

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
        mCookBookViewModel.setUser(userModel);

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
        mealPlan.setMonth(localDate.getMonth().toString());
        mealPlan.setDate(localDate);
        mealPlan.setRecipes(mAddMealPlanViewModel.getRecipes());
        mAddMealPlanViewModel.insertMealPlan(mealPlan);

        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }

    public void onDessertButtonClicked(View view) {
        recipePopup(RecipeCategory.DESSERT.toString());
    }

    public void onDrinkButtonClicked(View view) {
        recipePopup(RecipeCategory.DRINK.toString());
    }

    public static void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }
}
