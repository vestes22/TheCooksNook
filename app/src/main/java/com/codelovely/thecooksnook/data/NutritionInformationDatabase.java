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
        MoistAdjust.class,
        NutrientDesc.class,
        SubcodeDesc.class,
        Recipe.class,
        RecipeIngredient.class,
        Menu.class,
        ShoppingList.class,
        User.class,
        UserMenu.class,
        UserRecipe.class,
        UserShoppingList.class
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
    abstract public MoistAdjustDao getMoistAdjustDao();
    abstract public NutrientDescDao getNutrientDescDao();
    abstract public SubcodeDescDao getSubcodeDescDao();
    abstract public RecipeDao getRecipeDao();
    abstract public RecipeIngredientDao getRecipeIngredientDao();
    abstract public ShoppingListDao getShoppingListDao();
    abstract public UserDao getUserDao();
    abstract public UserMenuDao getUserMenuDao();
    abstract public UserRecipeDao getUserRecipeDao();
    abstract public UserShoppingListDao getUserShoppingListDao();
    abstract public MenuDao getMenuDao();

}
