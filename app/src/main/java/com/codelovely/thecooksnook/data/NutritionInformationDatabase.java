package com.codelovely.thecooksnook.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// Song and Album are classes annotated with @Entity.
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
        SubcodeDesc.class
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


}
