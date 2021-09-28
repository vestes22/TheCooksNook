package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codelovely.thecooksnook.models.IngredientModel;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.restmodels.SearchResultFood;
import com.codelovely.thecooksnook.viewmodels.PopulateRecipeViewModel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PopulateRecipeActivity extends AppCompatActivity {
    PopulateRecipeViewModel mViewModel;
    Observer<SearchResultFood> searchResultsObserver;
    List<SearchResultFood> results = new ArrayList<>();

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_populate_recipe);

        button = this.findViewById(R.id.populateRecipe_button);
        mViewModel = new ViewModelProvider(this).get(PopulateRecipeViewModel.class);
    }

    public void populateRecipes(View view) {

        InputStream inputStream = getResources().openRawResource(R.raw.recipe_data_raw);
        TSVFileReader csvFile = new TSVFileReader(inputStream);
        List<String[]> recipes = csvFile.read();
        List<SearchResultFood> resultFoods = new ArrayList<>();

        recipes.remove(0);

        for (String[] recipe : recipes) {
            RecipeModel recipeModel = new RecipeModel();
            List<IngredientModel> ingredientModels = new ArrayList<>();
            String title = recipe[9].substring(1, recipe[9].length()-2);
            recipeModel.setName(recipe[9]);
            recipeModel.setDescription("This is a default recipe provided by the kind researchers at Recipe1M+. It doesn't have a custom description, but we are sure it tastes delicious all the same.");
            String instructions = recipe[4];
            recipeModel.setInstructions(formatInstructions(instructions));
            recipeModel.setCategory(recipe[13]);
            recipeModel.setNumServings(4);
            List<String> ingredients = formatIngredients(recipe[3]);
            List<Float> ingredientAmounts = formatWeights(recipe[12]);

            for (int i = 0; i < ingredients.size(); i++) {
                IngredientModel ingredientModel = new IngredientModel();
                ingredientModel.setDescription(ingredients.get(i));
                ingredientModel.setAmountInRecipe(ingredientAmounts.get(i));
                ingredientModel.setServingSizeUnit("g");
                ingredientModels.add(ingredientModel);
            }
            recipeModel.setIngredients(ingredientModels);

            mViewModel.insertPreliminaryRecipe(recipeModel);
        }

    }

    public List<String> formatIngredients(String ingredients) {
        StringBuilder sb = new StringBuilder(ingredients);
        sb.deleteCharAt(ingredients.length() - 1);
        sb.deleteCharAt(ingredients.length() - 2);
        sb.deleteCharAt(0);
        sb.deleteCharAt(0);

        List<String> matchList = new ArrayList<String>();
        Pattern regex = Pattern.compile("\\{(.*?)\\}");
        Matcher regexMatcher = regex.matcher(sb.toString());

        while (regexMatcher.find()) {//Finds Matching Pattern in String
            matchList.add(regexMatcher.group(1));//Fetching Group from String
        }

        for (int i = 0; i < matchList.size(); i++) {
            String newIngredient = matchList.get(i).substring(9, matchList.get(i).length()-1);
            matchList.set(i, newIngredient);
        }
        return matchList;
    }

    public List<Float> formatWeights(String weights) {
        StringBuilder sb = new StringBuilder(weights);
        sb.deleteCharAt(weights.length() - 1);
        sb.deleteCharAt(weights.length() - 2);
        sb.deleteCharAt(0);
        sb.deleteCharAt(0);

        String weightString = sb.toString();

        String[] row = weightString.split(",");

        List<Float> weightList = new ArrayList<>();

        System.out.println("Weight List: ");
        for (int i = 0; i < row.length; i++) {
            weightList.add(Float.parseFloat(row[i]));
            System.out.println(weightList.get(i));
        }

        return weightList;
    }

    public String formatInstructions(String instructions) {
        StringBuilder sb = new StringBuilder(instructions);
        sb.deleteCharAt(instructions.length() - 1);
        sb.deleteCharAt(instructions.length() - 2);
        sb.deleteCharAt(0);
        sb.deleteCharAt(0);

        List<String> matchList = new ArrayList<String>();
        Pattern regex = Pattern.compile("\\{(.*?)\\}");
        Matcher regexMatcher = regex.matcher(sb.toString());
        String newInstructions = "";

        while (regexMatcher.find()) {//Finds Matching Pattern in String
            matchList.add(regexMatcher.group(1));//Fetching Group from String
        }

        for (int i = 0; i < matchList.size(); i++) {
            String newInstruction = matchList.get(i).substring(9, matchList.get(i).length()-1);
            matchList.set(i, newInstruction);
            newInstructions = newInstructions + newInstruction + "\n \n";
        }

        System.out.println(newInstructions);

        return newInstructions;
    }
}