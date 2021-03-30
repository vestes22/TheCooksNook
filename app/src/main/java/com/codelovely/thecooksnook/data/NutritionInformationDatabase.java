package com.codelovely.thecooksnook.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {
        AddFoodDesc.class,
        DerivDesc.class,
        FNDDSIngredients.class,
        FNDDSNutritionValue.class,
        FoodPortionDesc.class,
        FoodSubcodeLinks.class,
        FoodWeights.class,
        IngredNutValue.class,
        MainFoodDesc.class,
        Menu.class,
        MenuRecipe.class,
        MoistAdjust.class,
        NutrientDesc.class,
        Recipe.class,
        RecipeFood.class,
        RecipeIngredient.class,
        ShoppingList.class,
        ShoppingListFood.class,
        ShoppingListIngredient.class,
        SubcodeDesc.class,
        User.class,
        UserRecipe.class,
})

abstract class NutritionInformationDatabase extends RoomDatabase {
    abstract public AddFoodDescDao getAddFoodDescDao();
    abstract public DerivDescDao getDerivDescDao();
    abstract public FNDDSIngredientsDao getFNDDSIngredientsDao();
    abstract public FNDDSNutritionValueDao getFNDDSNutritionValueDao();
    abstract public FoodPortionDescDao getFoodPortionDescDao();
    abstract public FoodSubcodeLinksDao getFoodSubcodeLinksDao();
    abstract public FoodWeightsDao getFoodWeightsDao();
    abstract public IngredNutValueDao getIngredNutValueDao();
    abstract public MainFoodDescDao getMainFoodDescDao();
    abstract public MenuDao getMenuDao();
    abstract public MenuRecipeDao getMenuRecipeDao();
    abstract public MoistAdjustDao getMoistAdjustDao();
    abstract public NutrientDescDao getNutrientDescDao();
    abstract public RecipeDao getRecipeDao();
    abstract public RecipeFoodDao getRecipeFoodDao();
    abstract public RecipeIngredientDao getRecipeIngredientDao();
    abstract public ShoppingListDao getShoppingListDao();
    abstract public ShoppingListFoodDao getShoppingListFoodDao();
    abstract public ShoppingListIngredientDao getShoppingListIngredientDao();
    abstract public SubcodeDescDao getSubcodeDescDao();
    abstract public UserDao getUserDao();
    abstract public UserRecipeDao getUserRecipeDao();

}
