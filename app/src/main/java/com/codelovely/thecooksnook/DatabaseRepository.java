package com.codelovely.thecooksnook;

import android.annotation.SuppressLint;
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
import com.codelovely.thecooksnook.data.entities.MenuRecipe;
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
import com.codelovely.thecooksnook.models.restmodels.SRLegacyFoodItem;
import com.codelovely.thecooksnook.models.restmodels.SearchResult;
import com.codelovely.thecooksnook.models.restmodels.SearchResultFood;
import com.codelovely.thecooksnook.network.RetrofitClientInstance;
import com.codelovely.thecooksnook.network.RetrofitNetworkInterface;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DatabaseRepository {

    private final RetrofitNetworkInterface mService;
    private final MutableLiveData<List<SearchResultFood>> searchResultMutableLiveData;
    private final List<SearchResultFood> searchResultItem;
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
        searchResultItem = new ArrayList<>();
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
        List<IngredientModel> ingredientModels = recipeModel.getIngredients();

        for (IngredientModel ingredientModel : ingredientModels) {
            Ingredient ingredient = IngredientConverter.convertToIngredient(ingredientModel);

            if (!mIngredientDao.checkIfExists(ingredient.getFdcId())) {
                mIngredientDao.insert(ingredient);
            }

            for (com.codelovely.thecooksnook.models.restmodels.FoodNutrient foodNutrientModel : ingredientModel.getFoodNutrientsPerOriginalServingSize()) {
                Nutrient nutrient = NutrientConverter.convertToNutrient(foodNutrientModel.getNutrient());

                if (!mNutrientDao.checkIfExists(nutrient.getId())) {
                    mNutrientDao.insert(nutrient);
                }

                FoodNutrient foodNutrient = FoodNutrientConverter.convertToFoodNutrient(foodNutrientModel);
                foodNutrient.setFdcId(ingredientModel.getFdcId());
                if (!mFoodNutrientDao.checkIfExists(foodNutrient.getFdcId(), foodNutrient.getNutrientId())) {
                    mFoodNutrientDao.insert(foodNutrient);
                }
            }

            RecipeIngredient recipeIngredient =  new RecipeIngredient();
            recipeIngredient.setRecipeId(recipeId);
            recipeIngredient.setFdcId(ingredientModel.getFdcId());
            recipeIngredient.setAmount(ingredientModel.getAmountInRecipe());
            recipeIngredient.setDataType(ingredientModel.getDataType());
            if (!mRecipeIngredientDao.checkIfExists(recipeIngredient.getFdcId(), recipeIngredient.getRecipeId())) {
                mRecipeIngredientDao.insert(recipeIngredient);
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
        String[] dataType = {"Branded", "Foundation", "SR Legacy"};

        mService.searchFoods(RetrofitClientInstance.getApiKey(), query, dataType).enqueue(new Callback<SearchResult>() {

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

    public void insertPreliminaryRecipe(final RecipeModel recipeModel) {
        String[] dataType = {"Branded", "Foundation", "SR Legacy"};

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        final RetrofitNetworkInterface networkInterface = retrofit.create(RetrofitNetworkInterface.class);
        final List<Observable<?>> requests = new ArrayList<>();
        // Make a collection of all requests you need to call at once.
        for (IngredientModel ingredient : recipeModel.getIngredients()) {
            requests.add(networkInterface.searchObservableFoods(RetrofitClientInstance.getApiKey(), ingredient.getDescription(), dataType));
        }

        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {


                // Zip all requests with the Function, which will receive the results.
                Observable.zip(
                        requests,
                        new Function<Object[], Object>() {
                            @Override
                            public Object apply(Object[] objects) throws Exception {
                                List<SearchResultFood> ingredients = new ArrayList<>();
                                for (int i = 0; i < objects.length; i++) {
                                    SearchResult result = (SearchResult) objects[i];
                                    List<SearchResultFood> foods = result.getFoods();
                                    recipeModel.getIngredients().get(i).setFdcId(foods.get(0).getFdcId());
                                    recipeModel.getIngredients().get(i).setDataType(foods.get(0).getDataType());
                                }

                                return recipeModel;
                            }
                        })
                        // After all requests had been performed the next observer will receive the Object, returned from Function
                        .subscribe(
                                // Will be triggered if all requests will end successfully (4xx and 5xx also are successful requests too)
                                new Consumer<Object>() {
                                    @Override
                                    public void accept(Object o) throws Exception {
                                        final RecipeModel model = (RecipeModel) o;
                                        List<Observable<?>> detailRequests = new ArrayList<>();
                                        // Make a collection of all requests you need to call at once.
                                        for (IngredientModel ingredient : model.getIngredients()) {
                                            detailRequests.add(networkInterface.getSRLegacyObservableFoodItemById(ingredient.getFdcId(), RetrofitClientInstance.getApiKey()));
                                        }

                                        Observable.zip(
                                                detailRequests,
                                                new Function<Object[], Object>() {
                                                    @Override
                                                    public Object apply(Object[] objects) throws Exception {
                                                        for (int i = 0; i < objects.length; i++) {
                                                            SRLegacyFoodItem foodItem = (SRLegacyFoodItem) objects[i];
                                                            if (foodItem.getFdcId() == model.getIngredients().get(i).getFdcId()) {
                                                                model.getIngredients().get(i).setFoodNutrientsPerOriginalServingSize(foodItem.getFoodNutrients());
                                                                model.getIngredients().get(i).setFoodNutrientsAdjustedForRecipe();
                                                            } else if (foodItem.getFdcId() != model.getIngredients().get(i).getFdcId()) {
                                                                System.out.println("The FDC IDs do not match. foodItem: " + foodItem.getDescription() + " Ingredient: " + model.getIngredients().get(i).getDescription());
                                                            }
                                                        }
                                                        return recipeModel;
                                                    }
                                                })
                                                .subscribeOn(Schedulers.newThread())
                                                // After all requests had been performed the next observer will receive the Object, returned from Function
                                                .subscribe(
                                                        // Will be triggered if all requests will end successfully (4xx and 5xx also are successful requests too)
                                                        new Consumer<Object>() {
                                                            @SuppressLint("CheckResult")
                                                            @Override
                                                            public void accept(Object o) throws Exception {
                                                                UserModel user = new UserModel();
                                                                user.setUserId("Default user");
                                                                user.setFirstName("Default user");
                                                                user.setLastName("Default user");
                                                                RecipeModel recipe = (RecipeModel) o;
                                                                insertRecipe(user, recipe);
                                                            }
                                                        },

                                                        // Will be triggered if any error during requests will happen
                                                        new Consumer<Throwable>() {
                                                            @Override
                                                            public void accept(Throwable e) throws Exception {
                                                                Log.e("ERROR:", "Network Timed Out");
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                );

                                    }
                                },

                                // Will be triggered if any error during requests will happen
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable e) throws Exception {
                                        System.out.println("There was an error here.");
                                        e.printStackTrace();
                                    }
                                }
                        );
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
                    ingredient.setDescription(result.getDescription());
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

    public void getSRLegacyFoodItemById(int fdcId) {
        mService.getSRLegacyFoodItemById(fdcId, RetrofitClientInstance.getApiKey()).enqueue(new Callback<SRLegacyFoodItem>() {

            @Override
            public void onResponse(@NonNull Call<SRLegacyFoodItem> call, @NonNull Response<SRLegacyFoodItem> response) {
                if (response.code() == 404) {
                    Log.e("ERROR", "Response 404 - Not found");
                }
                else if(response.code() == 400) {
                    Log.e("ERROR", "Response 400 - Bad parameter");
                }
                else if (response.body() != null) {
                    SRLegacyFoodItem result = response.body();
                    IngredientModel ingredient = new IngredientModel();

                    ingredient.setDataType(result.getDataType());
                    ingredient.setDescription(result.getDescription());
                    ingredient.setFdcId(result.getFdcId());
                    ingredient.setCategory(result.getFoodCategory().getDescription());
                    ingredient.setFoodNutrientsPerOriginalServingSize(result.getFoodNutrients());
                    ingredient.setServingSizeUnit("G");

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

    public void insertMenuItem(UserModel user, MealPlan mealPlan) {
        Menu menu = new Menu();
        menu.setDateCreated(mealPlan.getDate());
        menu.setUserId(user.getUserId());

        int menuId = (int) mMenuDao.insert(menu);
        List<RecipeModel> menuRecipes =  mealPlan.getRecipes();
        for (RecipeModel recipe : menuRecipes) {
            MenuRecipe menuRecipe = new MenuRecipe();
            menuRecipe.setRecipeId(recipe.getId());
            menuRecipe.setMenuId(menuId);
            mMenuRecipeDao.insert(menuRecipe);
        }
    }

    public void insertUser(UserModel userModel) {
        User user = UserConverter.convertToUser(userModel);

        boolean userExists = mUserDao.checkIfExists(user.getUserId());
        if (!userExists) {
            mUserDao.insert(user);
        }
    }

    public List<MealPlan> getUserMealPlans(UserModel user) {
        List<Menu> menus = mMenuDao.getUserMenus(user.getUserId());
        List<MealPlan> mealPlans = new ArrayList<>();

        for (Menu menu : menus) {
            MealPlan mealPlan = new MealPlan();
            mealPlan.setId(menu.getMenuId());
            LocalDate menuDate = menu.getDateCreated();
            if (menuDate != null) {
                mealPlan.setDate(menuDate);
                mealPlans.add(mealPlan);
            }
        }

        return mealPlans;
    }

    public MealPlan getMealPlanById(int id) {
        Menu menu = mMenuDao.getMenuById(id);
        MealPlan mealPlan = new MealPlan();
        mealPlan.setId(menu.getMenuId());
        LocalDate mealPlanDate = menu.getDateCreated();
        mealPlan.setDate(mealPlanDate);
        List<RecipeModel> recipes = new ArrayList();
        List<MenuRecipe> menuRecipes = mMenuRecipeDao.getRecipesForMenu(id);
        for (MenuRecipe menuRecipe : menuRecipes) {
            RecipeModel recipe = getRecipeById(menuRecipe.getRecipeId());
            recipes.add(recipe);
        }
        mealPlan.setRecipes(recipes);
        return mealPlan;
    }

    public List<RecipeModel> getAllRecipes(UserModel user) {
        List<Recipe> recipes = mRecipeDao.getAllUserRecipes(user.getUserId());
        List<RecipeModel> modelRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            RecipeModel modelRecipe = new RecipeModel();
            modelRecipe.setId(recipe.getRecipeId());
            modelRecipe.setName(recipe.getTitle());
            modelRecipe.setDescription(recipe.getDescription());
            modelRecipe.setNumServings(recipe.getNumServings());
            modelRecipe.setCategory(recipe.getCategory());
            modelRecipe.setInstructions(recipe.getInstructions());
            modelRecipes.add(modelRecipe);
        }
        return modelRecipes;
    }

    public List<MealPlan> getUserMenusInDateRange(UserModel user, LocalDate startDate, LocalDate endDate) {
        List<MealPlan> meals = getUserMealPlans(user);
        List<MealPlan> mealsInDateRange = new ArrayList<>();


        for (MealPlan meal : meals) {
            if ((meal.getDate().compareTo(startDate) >= 0) && meal.getDate().compareTo(endDate) <= 0) {
                mealsInDateRange.add(meal);
            }
        }

        for (MealPlan meal : mealsInDateRange) {
            List<RecipeModel> recipes = new ArrayList<>();
            List<MenuRecipe> menuRecipes = mMenuRecipeDao.getRecipesForMenu(meal.getId());
            for (MenuRecipe recipe : menuRecipes) {
                RecipeModel recipeModel = getRecipeById(recipe.getRecipeId());
                recipes.add(recipeModel);
            }
            meal.setRecipes(recipes);
        }

        return mealsInDateRange;
    }

    public List<IngredientModel> getAllIngredients() {
        List<Ingredient> ingredients = mIngredientDao.getAllIngredients();
        List<IngredientModel> ingredientModels = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            IngredientModel ingredientModel = IngredientConverter.convertToIngredientModel(ingredient);
            List<FoodNutrient> foodNutrients = mFoodNutrientDao.getFoodNutrientsByFdcId(ingredient.getFdcId());

            List<com.codelovely.thecooksnook.models.restmodels.FoodNutrient> foodNutrientModels = new ArrayList<>();

            for (FoodNutrient foodNutrient : foodNutrients) {

                Nutrient nutrient = mNutrientDao.getNutrientById(foodNutrient.getNutrientId());
                com.codelovely.thecooksnook.models.restmodels.Nutrient nutrientModel = NutrientConverter.convertToNutrientModel(nutrient);

                com.codelovely.thecooksnook.models.restmodels.FoodNutrient foodNutrientModel = FoodNutrientConverter.convertToFoodNutrientModel(foodNutrient);
                foodNutrientModel.setNutrient(nutrientModel);
                foodNutrientModels.add(foodNutrientModel);
            } // End FoodNutrient for loop.

            ingredientModel.setFoodNutrientsPerOriginalServingSize(foodNutrientModels);
            ingredientModels.add(ingredientModel);
        }
        return ingredientModels;
    }

    public List<RecipeModel> getFullRecipesByCategory(UserModel user, String category) {
        List<RecipeModel> recipes = getRecipesByCategory(user, category);
        List<RecipeModel> returnedRecipes = new ArrayList();
        for (RecipeModel recipeModel : recipes) {
            RecipeModel newRecipe = getRecipeById(recipeModel.getId());
            returnedRecipes.add(newRecipe);
        }
        return returnedRecipes;
    }
}

