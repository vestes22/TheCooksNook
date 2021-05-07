package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.codelovely.thecooksnook.data.MainFoodDesc;
import com.codelovely.thecooksnook.data.MainFoodDescDao;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.viewmodels.AddRecipeViewModel;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {
    MainFoodDescDao mMainFoodDescDao;
    EditText searchIngredientsText;
    SearchResultsAdapter adapter;
    List<MainFoodDesc> mSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        mSearchResults = null;
        mMainFoodDescDao = NutritionInformationDatabase.getDatabase(this).getMainFoodDescDao();
        searchIngredientsText = (EditText) findViewById(R.id.searchText);
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
        adapter = new SearchResultsAdapter(mSearchResults);
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
}
