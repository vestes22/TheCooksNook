package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.codelovely.thecooksnook.data.MainFoodDesc;
import com.codelovely.thecooksnook.data.daos.FoodPortionDao;
import com.codelovely.thecooksnook.data.daos.MainFoodDescDao;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.models.FoodPortion;

import java.util.List;

public class AddRecipeActivity extends AppCompatActivity implements SearchResultsAdapter.SearchResultsListener {
    MainFoodDescDao mMainFoodDescDao;
    EditText searchIngredientsText;
    SearchResultsAdapter adapter;
    List<MainFoodDesc> mSearchResults;
    FoodPortionDao mFoodPortionDao;
    List<FoodPortion> mFoodPortions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        mSearchResults = null;
        mMainFoodDescDao = NutritionInformationDatabase.getDatabase(this).getMainFoodDescDao();
        mFoodPortionDao = NutritionInformationDatabase.getDatabase(this).getFoodPortionDao();
        searchIngredientsText = findViewById(R.id.searchText);
    }

    public void searchButtonClicked(View view) {
        final String query = searchIngredientsText.getText().toString();
        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mSearchResults = mMainFoodDescDao.search("*" + query + "*");
            }
        });

        RecyclerView searchResultsRv = findViewById(R.id.search_results);
        adapter = new SearchResultsAdapter(mSearchResults, this);
        searchResultsRv.setAdapter(adapter);
        searchResultsRv.setLayoutManager(new LinearLayoutManager(this));

        if (mSearchResults == null) {
            System.out.println("No results returned.");
        }
        else {
            for (MainFoodDesc food : mSearchResults) {
                System.out.println(food.getMainFoodDesc());
            }
        }
    }

    @Override
    public void onSearchResultClicked(int position) {
        // TODO - When searchResults recyclerview item is clicked.
        // This part is making the app crash.
        // For some reason it won't let me access foodPortions from inner class?
        // But it will let me use the global mFoodPortions... I do not want that.
        // So how? What do? Whyyyyyyyyyyyyyyyyyyyyyyyyyyyy
        System.out.println("Search result " + mSearchResults.get(position).getMainFoodDesc() + " clicked!");
        final int foodCode = mSearchResults.get(position).getFoodId();
        final List<FoodPortion> foodPortions;
        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mFoodPortions = mFoodPortionDao.getPortionOptions(foodCode);
            }
        });

        for (FoodPortion portion : mFoodPortions) {
            System.out.println(portion.getPortionDesc());
        }

    }
}
