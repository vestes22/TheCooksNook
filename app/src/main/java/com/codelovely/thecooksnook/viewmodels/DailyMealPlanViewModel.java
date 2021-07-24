package com.codelovely.thecooksnook.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.codelovely.thecooksnook.DatabaseRepository;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.models.MealPlan;

import java.util.ArrayList;
import java.util.List;

public class DailyMealPlanViewModel extends AndroidViewModel {
    DatabaseRepository mRepository;
    MealPlan mMealPlan;

    public DailyMealPlanViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DatabaseRepository(application);
    }

    public MealPlan getMealPlanById(final int id) {
        final List<MealPlan> mealPlan = new ArrayList<>();
        final Boolean[] condition = {false};
        final Object lockObject = new Object();


        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (lockObject) {
                    MealPlan newMealPlan = mRepository.getMealPlanById(id);
                    mealPlan.add(newMealPlan);
                    condition[0] = true;
                    lockObject.notify();
                }
            }
        });


        synchronized (lockObject) {
            while (!condition[0]) {
                try {
                    lockObject.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return mealPlan.get(0);
    }

    public void setMealPlan(int mealPlanId) {
        mMealPlan = getMealPlanById(mealPlanId);
    }

    public MealPlan getMealPlan() {
        return mMealPlan;
    }
}
