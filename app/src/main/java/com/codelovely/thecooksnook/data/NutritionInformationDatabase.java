package com.codelovely.thecooksnook.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


import com.codelovely.thecooksnook.data.daos.FoodNutrientDao;
import com.codelovely.thecooksnook.data.daos.IngredientDao;
import com.codelovely.thecooksnook.data.daos.MenuDao;
import com.codelovely.thecooksnook.data.daos.MenuRecipeDao;
import com.codelovely.thecooksnook.data.daos.NutrientDao;
import com.codelovely.thecooksnook.data.daos.RecipeDao;
import com.codelovely.thecooksnook.data.daos.RecipeIngredientDao;
import com.codelovely.thecooksnook.data.daos.ShoppingListDao;
import com.codelovely.thecooksnook.data.daos.ShoppingListFoodDao;
import com.codelovely.thecooksnook.data.daos.UserDao;
import com.codelovely.thecooksnook.data.daos.UserRecipeDao;
import com.codelovely.thecooksnook.data.entities.FoodNutrient;
import com.codelovely.thecooksnook.data.entities.Ingredient;
import com.codelovely.thecooksnook.data.entities.Menu;
import com.codelovely.thecooksnook.data.entities.MenuRecipe;
import com.codelovely.thecooksnook.data.entities.Nutrient;
import com.codelovely.thecooksnook.data.entities.Recipe;
import com.codelovely.thecooksnook.data.entities.RecipeIngredient;
import com.codelovely.thecooksnook.data.entities.ShoppingList;
import com.codelovely.thecooksnook.data.entities.ShoppingListFood;
import com.codelovely.thecooksnook.data.entities.User;
import com.codelovely.thecooksnook.data.entities.UserRecipe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(version = 1, exportSchema = false, entities = {
        FoodNutrient.class,
        Ingredient.class,
        Menu.class,
        MenuRecipe.class,
        Nutrient.class,
        Recipe.class,
        RecipeIngredient.class,
        ShoppingList.class,
        ShoppingListFood.class,
        User.class,
        UserRecipe.class,
})
@TypeConverters({Converters.class})
public abstract class NutritionInformationDatabase extends RoomDatabase {
    abstract public FoodNutrientDao getFoodNutrientDao();
    abstract public IngredientDao getIngredientDao();
    abstract public MenuDao getMenuDao();
    abstract public MenuRecipeDao getMenuRecipeDao();
    abstract public NutrientDao getNutrientDao();
    abstract public RecipeDao getRecipeDao();
    abstract public RecipeIngredientDao getRecipeIngredientDao();
    abstract public ShoppingListDao getShoppingListDao();
    abstract public ShoppingListFoodDao getShoppingListFoodDao();
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
                            .createFromAsset("Database.db")
                            .setJournalMode(JournalMode.TRUNCATE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


