package com.codelovely.thecooksnook.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import com.codelovely.thecooksnook.DatabaseRepository;
import com.codelovely.thecooksnook.data.MainFoodDesc;

import java.util.List;

public class AddRecipeViewModel extends AndroidViewModel {
    private DatabaseRepository mRepository;
    private List<MainFoodDesc> mSearchResults;
    private final List<MainFoodDesc> mAllFood;


    public AddRecipeViewModel(Application application) {
        super(application);

        mRepository = new DatabaseRepository(application);
        mAllFood = mRepository.getAllFood();
    }

    public List<MainFoodDesc> search(String query) {
        if(query.trim().isEmpty()) {
            mSearchResults = mRepository.getAllFood();
        }
        else {
            mSearchResults = mRepository.search("*" + query + "*");
        }
        return mSearchResults;
    }

}

