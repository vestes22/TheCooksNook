package com.codelovely.thecooksnook.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.codelovely.thecooksnook.data.daos.AddFoodDescDao;
import com.codelovely.thecooksnook.data.daos.DerivDescDao;
import com.codelovely.thecooksnook.data.daos.FNDDSIngredientsDao;
import com.codelovely.thecooksnook.data.daos.FNDDSNutritionValueDao;
import com.codelovely.thecooksnook.data.daos.FoodPortionDao;
import com.codelovely.thecooksnook.data.daos.FoodPortionDescDao;
import com.codelovely.thecooksnook.data.daos.FoodSubcodeLinksDao;
import com.codelovely.thecooksnook.data.daos.FoodWeightsDao;
import com.codelovely.thecooksnook.data.daos.IngredNutValueDao;
import com.codelovely.thecooksnook.data.daos.IngredientDao;
import com.codelovely.thecooksnook.data.daos.MainFoodDescDao;
import com.codelovely.thecooksnook.data.daos.MenuDao;
import com.codelovely.thecooksnook.data.daos.MenuRecipeDao;
import com.codelovely.thecooksnook.data.daos.MoistAdjustDao;
import com.codelovely.thecooksnook.data.daos.NutrientDescDao;
import com.codelovely.thecooksnook.data.daos.RecipeDao;
import com.codelovely.thecooksnook.data.daos.RecipeFoodDao;
import com.codelovely.thecooksnook.data.daos.ShoppingListDao;
import com.codelovely.thecooksnook.data.daos.ShoppingListFoodDao;
import com.codelovely.thecooksnook.data.daos.SubcodeDescDao;
import com.codelovely.thecooksnook.data.daos.UserDao;
import com.codelovely.thecooksnook.data.daos.UserRecipeDao;
import com.codelovely.thecooksnook.data.entities.AddFoodDesc;
import com.codelovely.thecooksnook.data.entities.DerivDesc;
import com.codelovely.thecooksnook.data.entities.FNDDSIngredients;
import com.codelovely.thecooksnook.data.entities.FNDDSNutritionValue;
import com.codelovely.thecooksnook.data.entities.FoodPortionDesc;
import com.codelovely.thecooksnook.data.entities.FoodSubcodeLinks;
import com.codelovely.thecooksnook.data.entities.FoodWeights;
import com.codelovely.thecooksnook.data.entities.IngredNutValue;
import com.codelovely.thecooksnook.data.entities.Menu;
import com.codelovely.thecooksnook.data.entities.MenuRecipe;
import com.codelovely.thecooksnook.data.entities.MoistAdjust;
import com.codelovely.thecooksnook.data.entities.NutrientDesc;
import com.codelovely.thecooksnook.data.entities.Recipe;
import com.codelovely.thecooksnook.data.entities.RecipeFood;
import com.codelovely.thecooksnook.data.entities.ShoppingList;
import com.codelovely.thecooksnook.data.entities.ShoppingListFood;
import com.codelovely.thecooksnook.data.entities.SubcodeDesc;
import com.codelovely.thecooksnook.data.entities.User;
import com.codelovely.thecooksnook.data.entities.UserRecipe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(version = 1, exportSchema = false, entities = {
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
    abstract public FoodPortionDao getFoodPortionDao();
    abstract public FoodPortionDescDao getFoodPortionDescDao();
    abstract public FoodSubcodeLinksDao getFoodSubcodeLinksDao();
    abstract public FoodWeightsDao getFoodWeightsDao();
    abstract public IngredientDao getIngredientDao();
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
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    db.execSQL("INSERT INTO mainFoodDesc_fts(mainFoodDesc_fts) VALUES ('rebuild')");
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


