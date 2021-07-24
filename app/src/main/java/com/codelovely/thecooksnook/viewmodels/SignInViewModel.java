package com.codelovely.thecooksnook.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.codelovely.thecooksnook.DatabaseRepository;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.models.UserModel;

public class SignInViewModel extends AndroidViewModel {
    private DatabaseRepository mRepository;

    public SignInViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DatabaseRepository(application);
    }

    public void insertUser(final UserModel user) {
        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
           @Override
           public void run() {
               mRepository.insertUser(user);
               mRepository.initializeUserRecipes(user);
           }
        });
    }
}
