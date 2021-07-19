package com.codelovely.thecooksnook;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codelovely.thecooksnook.ClassConverters.FoodNutrientConverter;
import com.codelovely.thecooksnook.ClassConverters.IngredientConverter;
import com.codelovely.thecooksnook.ClassConverters.NutrientConverter;
import com.codelovely.thecooksnook.ClassConverters.RecipeConverter;
import com.codelovely.thecooksnook.ClassConverters.UserConverter;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.data.daos.FoodNutrientDao;
import com.codelovely.thecooksnook.data.daos.IngredientDao;
import com.codelovely.thecooksnook.data.daos.MenuDao;
import com.codelovely.thecooksnook.data.daos.MenuRecipeDao;
import com.codelovely.thecooksnook.data.daos.NutrientDao;
import com.codelovely.thecooksnook.data.daos.RecipeDao;
import com.codelovely.thecooksnook.data.daos.RecipeIngredientDao;
import com.codelovely.thecooksnook.data.daos.UserDao;
import com.codelovely.thecooksnook.data.daos.UserRecipeDao;
import com.codelovely.thecooksnook.data.entities.FoodNutrient;
import com.codelovely.thecooksnook.data.entities.Ingredient;
import com.codelovely.thecooksnook.data.entities.Menu;
import com.codelovely.thecooksnook.data.entities.Nutrient;
import com.codelovely.thecooksnook.data.entities.RecipeIngredient;
import com.codelovely.thecooksnook.data.entities.User;
import com.codelovely.thecooksnook.data.entities.UserRecipe;
import com.codelovely.thecooksnook.models.IngredientModel;
import com.codelovely.thecooksnook.data.entities.Recipe;

import com.codelovely.thecooksnook.models.MealPlan;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.UserModel;
import com.codelovely.thecooksnook.models.restmodels.BrandedFoodItem;
import com.codelovely.thecooksnook.models.restmodels.FoundationFoodItem;
import com.codelovely.thecooksnook.models.restmodels.SearchResult;
import com.codelovely.thecooksnook.models.restmodels.SearchResultFood;
import com.codelovely.thecooksnook.network.RetrofitClientInstance;
import com.codelovely.thecooksnook.network.RetrofitNetworkInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatabaseRepository {

    private final RetrofitNetworkInterface mService;
    private final MutableLiveData<List<SearchResultFood>> searchResultMutableLiveData;
    private final MutableLiveData<List<IngredientModel>> selectedIngredientsList;
    private final List<IngredientModel> _selectedIngredientsList;

    private final FoodNutrientDao mFoodNutrientDao;
    private final IngredientDao mIngredientDao;
    private final MenuDao mMenuDao;
    private final MenuRecipeDao mMenuRecipeDao;
    private final NutrientDao mNutrientDao;
    private final RecipeDao mRecipeDao;
    private final RecipeIngredientDao mRecipeIngredientDao;
    private final UserDao mUserDao;
    private final UserRecipeDao mUserRecipeDao;


    public DatabaseRepository(Application application) {
        NutritionInformationDatabase db = NutritionInformationDatabase.getDatabase(application);
        mService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitNetworkInterface.class);
        searchResultMutableLiveData = new MutableLiveData<>();
        selectedIngredientsList = new MutableLiveData<>();
        _selectedIngredientsList = new ArrayList<>();

        mFoodNutrientDao = db.getFoodNutrientDao();
        mIngredientDao = db.getIngredientDao();
        mMenuDao = db.getMenuDao();
        mMenuRecipeDao = db.getMenuRecipeDao();
        mNutrientDao = db.getNutrientDao();
        mRecipeDao = db.getRecipeDao();
        mRecipeIngredientDao = db.getRecipeIngredientDao();
        mUserDao = db.getUserDao();
        mUserRecipeDao = db.getUserRecipeDao();
    }

    public void insertRecipe(UserModel userModel, RecipeModel recipeModel) {
        Recipe recipe = RecipeConverter.convertToRecipe(recipeModel);
        int recipeId = (int) mRecipeDao.insert(recipe);
        System.out.println("Recipe inserted!");
        List<IngredientModel> ingredientModels = recipeModel.getIngredients();

        for (IngredientModel ingredientModel : ingredientModels) {
            Ingredient ingredient = IngredientConverter.convertToIngredient(ingredientModel);

            if (!mIngredientDao.checkIfExists(ingredient.getFdcId())) {
                mIngredientDao.insert(ingredient);
                System.out.println("Ingredient inserted!");
            }


            for (com.codelovely.thecooksnook.models.restmodels.FoodNutrient foodNutrientModel : ingredientModel.getFoodNutrientsPerOriginalServingSize()) {
                Nutrient nutrient = NutrientConverter.convertToNutrient(foodNutrientModel.getNutrient());

                if (!mNutrientDao.checkIfExists(nutrient.getId())) {
                    mNutrientDao.insert(nutrient);
                    System.out.println("Nutrient inserted!");
                }

                FoodNutrient foodNutrient = FoodNutrientConverter.convertToFoodNutrient(foodNutrientModel);
                foodNutrient.setFdcId(ingredientModel.getFdcId());
                System.out.println("Food nutrient ID: " + foodNutrient.getFdcId());
                if (!mFoodNutrientDao.checkIfExists(foodNutrient.getFdcId(), foodNutrient.getNutrientId())) {
                    mFoodNutrientDao.insert(foodNutrient);
                    System.out.println("FoodNutrient inserted!");
                }
            }


            RecipeIngredient recipeIngredient =  new RecipeIngredient();
            recipeIngredient.setRecipeId(recipeId);
            recipeIngredient.setFdcId(ingredientModel.getFdcId());
            recipeIngredient.setAmount(ingredientModel.getAmountInRecipe());
            recipeIngredient.setDataType(ingredientModel.getDataType());
            if (!mRecipeIngredientDao.checkIfExists(recipeIngredient.getFdcId(), recipeIngredient.getRecipeId())) {
                mRecipeIngredientDao.insert(recipeIngredient);
                System.out.println("Recipe Ingredient inserted!");
            }
        }

        UserRecipe userRecipe = new UserRecipe();
        userRecipe.setRecipeId(recipeId);
        userRecipe.setUserId(userModel.getUserId());
        mUserRecipeDao.insert(userRecipe);
    }

    public List<RecipeModel> getRecipesByCategory(UserModel user, String category) {
        List<Recipe> recipes = mRecipeDao.getUserRecipeByCategory(user.getUserId(), category);
        List<RecipeModel> modelRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            RecipeModel modelRecipe = new RecipeModel();
            modelRecipe.setId(recipe.getRecipeId());
            modelRecipe.setName(recipe.getTitle());
            modelRecipe.setDescription(recipe.getDescription());
            modelRecipe.setNumServings(recipe.getNumServings());
            modelRecipe.setCategory(category);
            modelRecipe.setInstructions(recipe.getInstructions());
            modelRecipes.add(modelRecipe);
        }
        return modelRecipes;
    }

    public RecipeModel getRecipeById(int recipeId) {
        Recipe recipe = mRecipeDao.getRecipeById(recipeId);
        RecipeModel recipeModel = RecipeConverter.convertToRecipeModel(recipe);
        List<IngredientModel> ingredientModels = new ArrayList<>();
        List<RecipeIngredient> recipeIngredients = mRecipeIngredientDao.getIngredientsByRecipeId(recipeId);

        for (RecipeIngredient recipeIngredient : recipeIngredients) {

            List<FoodNutrient> foodNutrients = mFoodNutrientDao.getFoodNutrientsByFdcId(recipeIngredient.getFdcId());
            List<com.codelovely.thecooksnook.models.restmodels.FoodNutrient> foodNutrientModels = new ArrayList<>();

            for (FoodNutrient foodNutrient : foodNutrients) {

                Nutrient nutrient = mNutrientDao.getNutrientById(foodNutrient.getNutrientId());
                com.codelovely.thecooksnook.models.restmodels.Nutrient nutrientModel = NutrientConverter.convertToNutrientModel(nutrient);

                com.codelovely.thecooksnook.models.restmodels.FoodNutrient foodNutrientModel = FoodNutrientConverter.convertToFoodNutrientModel(foodNutrient);
                foodNutrientModel.setNutrient(nutrientModel);
                foodNutrientModels.add(foodNutrientModel);
            } // End FoodNutrient for loop.

            Ingredient ingredient = mIngredientDao.getIngredientById(recipeIngredient.getFdcId());
            IngredientModel ingredientModel = IngredientConverter.convertToIngredientModel(ingredient);
            ingredientModel.setAmountInRecipe(recipeIngredient.getAmount());
            ingredientModel.setFoodNutrientsPerOriginalServingSize(foodNutrientModels);
            ingredientModel.setFoodNutrientsAdjustedForRecipe();



            ingredientModels.add(ingredientModel);
        } // End Ingredient for loop.

        recipeModel.setIngredients(ingredientModels);
        recipeModel.setTotalRecipeNutrients();
        recipeModel.setRecipeNutrientsPerServing();

        return recipeModel;
    }


    public void searchFoodDataCentralByName(final String query) {
        String[] dataType = {"Branded", "Foundation"};
        String sortBy = "dataType.keyword";
        String sortOrder = "desc";

        mService.searchFoods(RetrofitClientInstance.getApiKey(), query, dataType, sortBy, sortOrder).enqueue(new Callback<SearchResult>() {

            @Override
            public void onResponse(@NonNull Call<SearchResult> call, @NonNull Response<SearchResult> response) {
                if (response.code() == 404) {
                    Log.e("ERROR", "Response 404 - Not found");
                }
                else if (response.body() != null) {
                    SearchResult result = response.body();
                    List<SearchResultFood> foods = result.getFoods();
                    searchResultMutableLiveData.postValue(foods);
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                searchResultMutableLiveData.postValue(null);
                if (t instanceof IOException) {
                    Log.e("ERROR", "Network error - try again.");
                }
                else {
                    Log.e("ERROR", "Conversion error. Could not convert to model.");
                }
            }
        });
    }


    public void getBrandedFoodItemById(int fdcId) {
        mService.getBrandedFoodItemById(fdcId, Nutrients.getNutrientNumbersList(), RetrofitClientInstance.getApiKey()).enqueue(new Callback<BrandedFoodItem>() {

            @Override
            public void onResponse(@NonNull Call<BrandedFoodItem> call, @NonNull Response<BrandedFoodItem> response) {
                if (response.code() == 404) {
                    Log.e("ERROR", "Response 404 - Not found");
                }
                else if(response.code() == 400) {
                    Log.e("ERROR", "Response 400 - Bad parameter");
                }
                else if (response.body() != null) {
                    BrandedFoodItem result = response.body();
                    IngredientModel ingredient = new IngredientModel();

                    ingredient.setDataType(result.getDataType());
                    ingredient.setDescription(result.getDescription() + " | " + result.getBrandOwner());
                    ingredient.setFdcId(result.getFdcId());
                    ingredient.setCategory(result.getBrandedFoodCategory());
                    ingredient.setFoodNutrientsPerOriginalServingSize(result.getFoodNutrients());
                    ingredient.setServingSizeUnit(result.getServingSizeUnit());

                    _selectedIngredientsList.add(ingredient);
                    selectedIngredientsList.postValue(_selectedIngredientsList);
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                searchResultMutableLiveData.postValue(null);
                if (t instanceof IOException) {
                    Log.e("ERROR", "Network error - try again.");
                }
                else {
                    Log.e("ERROR", "Conversion error. Could not convert to model.");
                }
            }
        });
    }


    public void getFoundationFoodItemById(int fdcId) {
        mService.getFoundationFoodItemById(fdcId, Nutrients.getNutrientNumbersList(), RetrofitClientInstance.getApiKey()).enqueue(new Callback<FoundationFoodItem>() {

            @Override
            public void onResponse(@NonNull Call<FoundationFoodItem> call, @NonNull Response<FoundationFoodItem> response) {
                if (response.isSuccessful())
                {
                    if (response.code() == 404) {
                        Log.e("ERROR", "Response 404 - Not found");
                    }
                    else if(response.code() == 400) {
                        Log.e("ERROR", "Response 400 - Bad parameter");
                    }
                    else if (response.body() != null) {
                        FoundationFoodItem result = response.body();
                        IngredientModel ingredient = new IngredientModel();
                        ingredient.setDataType(result.getDataType());
                        ingredient.setDescription(result.getDescription());
                        ingredient.setFdcId(result.getFdcId());
                        ingredient.setFoodNutrientsPerOriginalServingSize(result.getFoodNutrients());
                        ingredient.setServingSizeUnit("g");
                        ingredient.setCategory(result.getFoodCategory().getDescription());
                        _selectedIngredientsList.add(ingredient);
                        selectedIngredientsList.postValue(_selectedIngredientsList);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                searchResultMutableLiveData.postValue(null);
                System.out.println("Something went wrong!");
                if (t instanceof IOException) {
                    Log.e("ERROR", "Network error - try again.");
                }
                else {
                    Log.e("ERROR", "Conversion error. Could not convert to model.");
                }
            }
        });
    }

    public LiveData<List<SearchResultFood>> getSearchResultMutableLiveData() {
        return searchResultMutableLiveData;
    }


    public LiveData<List<IngredientModel>> getSelectedIngredientsLiveData() {
        return selectedIngredientsList;
    }


    public void deleteRecipeById(int recipeId) {
        // TODO
    }

    public void insertMenuItem(MealPlan mealPlan) {
        Menu menu = new Menu();

    }

    public void insertUser(UserModel userModel) {
        User user = UserConverter.convertToUser(userModel);

        boolean userExists = mUserDao.checkIfExists(user.getUserId());
        if (!userExists) {
            mUserDao.insert(user);
        }
    }

    public void initializeUserRecipes(UserModel user) {
        List<Integer> recipeIds = mUserRecipeDao.getUserRecipes("Default user");
        for (Integer recipeId : recipeIds) {
            UserRecipe userRecipe = new UserRecipe();
            userRecipe.setRecipeId(recipeId);
            userRecipe.setUserId(user.getUserId());
            boolean added = mUserRecipeDao.checkIfExists(userRecipe.getUserId(), userRecipe.getRecipeId());
            if (!added) {
                mUserRecipeDao.insert(userRecipe);
            }
        }

    }

}
