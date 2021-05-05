package com.codelovely.thecooksnook.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(version = 2, exportSchema = false, entities = {
        AddFoodDesc.class,
        DerivDesc.class,
        FNDDSIngredients.class,
        FNDDSNutritionValue.class,
        FoodPortionDesc.class,
        FoodSubcodeLinks.class,
        FoodWeights.class,
        IngredNutValue.class,
        MainFoodDesc.class,
        MainFoodDescFTS.class,
        Menu.class,
        MenuRecipe.class,
        MoistAdjust.class,
        NutrientDesc.class,
        Recipe.class,
        RecipeFood.class,
        ShoppingList.class,
        ShoppingListFood.class,
        SubcodeDesc.class,
        User.class,
        UserRecipe.class,
})
@TypeConverters({Converters.class})
public abstract class NutritionInformationDatabase extends RoomDatabase {
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
    abstract public ShoppingListDao getShoppingListDao();
    abstract public ShoppingListFoodDao getShoppingListFoodDao();
    abstract public SubcodeDescDao getSubcodeDescDao();
    abstract public UserDao getUserDao();
    abstract public UserRecipeDao getUserRecipeDao();

    private static volatile NutritionInformationDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static NutritionInformationDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NutritionInformationDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NutritionInformationDatabase.class, "nutrition_information_database")
                            .createFromAsset("FNDDSDatabase.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


