package com.codelovely.thecooksnook;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.data.daos.FoodNutrientDao;
import com.codelovely.thecooksnook.data.daos.IngredientDao;
import com.codelovely.thecooksnook.data.daos.NutrientDao;
import com.codelovely.thecooksnook.data.daos.RecipeDao;
import com.codelovely.thecooksnook.data.daos.RecipeIngredientDao;
import com.codelovely.thecooksnook.data.entities.FoodNutrient;
import com.codelovely.thecooksnook.data.entities.Ingredient;
import com.codelovely.thecooksnook.data.entities.Nutrient;
import com.codelovely.thecooksnook.data.entities.RecipeIngredient;
import com.codelovely.thecooksnook.models.IngredientModel;
import com.codelovely.thecooksnook.data.entities.Recipe;

import com.codelovely.thecooksnook.models.RecipeModel;
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

    private RecipeDao mRecipeDao;
    private IngredientDao mIngredientDao;
    private NutrientDao mNutrientDao;
    private RecipeIngredientDao mRecipeIngredientDao;
    private FoodNutrientDao mFoodNutrientDao;
    private RetrofitNetworkInterface mService;
    private MutableLiveData<List<SearchResultFood>> searchResultMutableLiveData;
    private MutableLiveData<List<IngredientModel>> selectedIngredientsList;
    private List<IngredientModel> _selectedIngredientsList;

    public DatabaseRepository(Application application) {
        NutritionInformationDatabase db = NutritionInformationDatabase.getDatabase(application);
        mService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitNetworkInterface.class);
        mRecipeDao = db.getRecipeDao();
        mIngredientDao = db.getIngredientDao();
        mNutrientDao = db.getNutrientDao();
        mFoodNutrientDao = db.getFoodNutrientDao();
        mRecipeIngredientDao = db.getRecipeIngredientDao();
        searchResultMutableLiveData = new MutableLiveData<>();
        selectedIngredientsList = new MutableLiveData<>();
        _selectedIngredientsList = new ArrayList<>();
    }

    public void insertRecipe(RecipeModel recipeModel) {
        Recipe recipe = convertToRecipe(recipeModel);
        int recipeId = (int) mRecipeDao.insert(recipe);
        System.out.println("Recipe inserted!");
        List<IngredientModel> ingredientModels = recipeModel.getIngredients();

        for (IngredientModel ingredientModel : ingredientModels) {
            Ingredient ingredient = convertToIngredient(ingredientModel);

            if (!mIngredientDao.checkIfExists(ingredient.getFdcId())) {
                mIngredientDao.insert(ingredient);
                System.out.println("Ingredient inserted!");
            }


            for (com.codelovely.thecooksnook.models.restmodels.FoodNutrient foodNutrientModel : ingredientModel.getFoodNutrientsPerOriginalServingSize()) {
                Nutrient nutrient = convertToNutrient(foodNutrientModel.getNutrient());

                if (!mNutrientDao.checkIfExists(nutrient.getId())) {
                    mNutrientDao.insert(nutrient);
                    System.out.println("Nutrient inserted!");
                }

                FoodNutrient foodNutrient = convertToFoodNutrient(foodNutrientModel);
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
    }


    public List<RecipeModel> getRecipesByCategory(String category) {
        List<Recipe> recipes = mRecipeDao.getRecipeByCategory(category);
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
        RecipeModel recipeModel = convertToRecipeModel(recipe);
        List<IngredientModel> ingredientModels = new ArrayList<>();
        List<RecipeIngredient> recipeIngredients = mRecipeIngredientDao.getIngredientsByRecipeId(recipeId);

        for (RecipeIngredient recipeIngredient : recipeIngredients) {

            List<FoodNutrient> foodNutrients = mFoodNutrientDao.getFoodNutrientsByFdcId(recipeIngredient.getFdcId());
            List<com.codelovely.thecooksnook.models.restmodels.FoodNutrient> foodNutrientModels = new ArrayList<>();

            for (FoodNutrient foodNutrient : foodNutrients) {

                Nutrient nutrient = mNutrientDao.getNutrientById(foodNutrient.getNutrientId());
                com.codelovely.thecooksnook.models.restmodels.Nutrient nutrientModel = convertToNutrientModel(nutrient);

                com.codelovely.thecooksnook.models.restmodels.FoodNutrient foodNutrientModel = convertToFoodNutrientModel(foodNutrient);
                foodNutrientModel.setNutrient(nutrientModel);
                foodNutrientModels.add(foodNutrientModel);
            } // End FoodNutrient for loop.

            Ingredient ingredient = mIngredientDao.getIngredientById(recipeIngredient.getFdcId());
            IngredientModel ingredientModel = convertToIngredientModel(ingredient);
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

    private Recipe convertToRecipe(RecipeModel model) {
        Recipe recipe = new Recipe();
        recipe.setTitle(model.getName());
        recipe.setDescription(model.getDescription());
        recipe.setNumServings(model.getNumServings());
        recipe.setInstructions(model.getInstructions());
        recipe.setCategory(model.getCategory());
        return recipe;
    }

    private Ingredient convertToIngredient(IngredientModel model) {
        Ingredient ingredient = new Ingredient();

        ingredient.setFdcId(model.getFdcId());
        ingredient.setDataType(model.getDataType());
        ingredient.setDescription(model.getDescription());
        ingredient.setServingSizeUnit(model.getServingSizeUnit());
        ingredient.setCategory(model.getCategory());

        return ingredient;
    }

    private Nutrient convertToNutrient(com.codelovely.thecooksnook.models.restmodels.Nutrient nutrientModel) {
        Nutrient nutrient = new Nutrient();
        nutrient.setId(nutrientModel.getId());
        nutrient.setName(nutrientModel.getName());
        nutrient.setUnitName(nutrientModel.getUnitName());
        return nutrient;
    }

    private FoodNutrient convertToFoodNutrient(com.codelovely.thecooksnook.models.restmodels.FoodNutrient foodNutrientModel) {
        FoodNutrient foodNutrient = new FoodNutrient();
        foodNutrient.setAmount(foodNutrientModel.getAmount());
        foodNutrient.setNutrientId(foodNutrientModel.getNutrient().getId());
        return foodNutrient;
    }

    private RecipeModel convertToRecipeModel(Recipe recipe) {
        RecipeModel recipeModel = new RecipeModel();
        recipeModel.setCategory(recipe.getCategory());
        recipeModel.setDescription(recipe.getDescription());
        recipeModel.setName(recipe.getTitle());
        recipeModel.setNumServings(recipe.getNumServings());
        recipeModel.setInstructions(recipe.getInstructions());
        recipeModel.setId(recipe.getRecipeId());
        return recipeModel;
    }

    private IngredientModel convertToIngredientModel(Ingredient ingredient) {
        IngredientModel ingredientModel = new IngredientModel();
        ingredientModel.setFdcId(ingredient.getFdcId());
        ingredientModel.setDataType(ingredient.getDataType());
        ingredientModel.setDescription(ingredient.getDescription());
        ingredientModel.setCategory(ingredient.getCategory());
        ingredientModel.setServingSizeUnit(ingredient.getServingSizeUnit());

        return ingredientModel;
    }

    private com.codelovely.thecooksnook.models.restmodels.FoodNutrient convertToFoodNutrientModel(FoodNutrient foodNutrient) {
        com.codelovely.thecooksnook.models.restmodels.FoodNutrient foodNutrientModel = new com.codelovely.thecooksnook.models.restmodels.FoodNutrient();
        foodNutrientModel.setFdcId(foodNutrient.getFdcId());
        foodNutrientModel.setAmount(foodNutrient.getAmount());
        return foodNutrientModel;
    }

    private com.codelovely.thecooksnook.models.restmodels.Nutrient convertToNutrientModel(Nutrient nutrient) {
        com.codelovely.thecooksnook.models.restmodels.Nutrient nutrientModel = new com.codelovely.thecooksnook.models.restmodels.Nutrient();
        nutrientModel.setId(nutrient.getId());
        nutrientModel.setName(nutrient.getName());
        nutrientModel.setUnitName(nutrient.getUnitName());

        return nutrientModel;
    }

    public void deleteRecipeById(int recipeId) {
        // TODO
    }

}
