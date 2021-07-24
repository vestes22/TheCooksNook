package com.codelovely.thecooksnook.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codelovely.thecooksnook.DatabaseRepository;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.models.MealPlan;
import com.codelovely.thecooksnook.models.UserModel;

import java.util.List;

public class MealPlannerViewModel extends AndroidViewModel {
    DatabaseRepository mRepository;
    MutableLiveData<List<MealPlan>> mealPlans;
    UserModel user;

    public MealPlannerViewModel(@NonNull Application application) {
        super(application);

        mRepository = new DatabaseRepository(application);
        mealPlans = new MutableLiveData<>();
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public LiveData<List<MealPlan>> getUserMealPlans() {
        return mealPlans;

    }

    public void setUserMealPlans() {
        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
           @Override
           public void run() {
               List<MealPlan> userMealPlans = mRepository.getUserMealPlans(user);
               mealPlans.postValue(userMealPlans);
           }
        });
    }
}
