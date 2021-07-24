package com.codelovely.thecooksnook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codelovely.thecooksnook.adapters.MealPlanAdapter;
import com.codelovely.thecooksnook.adapters.RecipeAdapter;
import com.codelovely.thecooksnook.models.MealPlan;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.UserModel;
import com.codelovely.thecooksnook.viewmodels.MealPlannerViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

public class MealPlannerActivity extends AppCompatActivity implements MealPlanAdapter.MealPlanListener {
    MealPlannerViewModel mMealPlannerViewModel;
    MealPlanAdapter mMealPlanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_planner);

        mMealPlannerViewModel = new ViewModelProvider(this).get(MealPlannerViewModel.class);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        UserModel user = new UserModel();
        user.setFirstName(account.getGivenName());
        user.setLastName(account.getFamilyName());
        user.setUserId(account.getId());
        mMealPlannerViewModel.setUser(user);
        mMealPlannerViewModel.setUserMealPlans();

        RecyclerView mealPlanRv = findViewById(R.id.mealPlanner_mealPlanRecyclerView);
        mMealPlanAdapter = new MealPlanAdapter(new MealPlanAdapter.MealPlanDiff(), this);
        mealPlanRv.setAdapter(mMealPlanAdapter);
        mealPlanRv.setLayoutManager(new LinearLayoutManager(this));

        final Observer<List<MealPlan>> mealPlanListObserver = new Observer<List<MealPlan>> () {
            @Override
            public void onChanged(@Nullable final List<MealPlan> mealPlanList) {
                mMealPlanAdapter.submitList(null);
                mMealPlanAdapter.submitList(mealPlanList);
            }
        };

        mMealPlannerViewModel.getUserMealPlans().observe(this, mealPlanListObserver);
    }

    public void addMealPlanButtonClickedClicked(View view) {
        Intent intent = new Intent(this, AddMealPlanActivity.class);
        startActivity(intent);
    }

    public void onMealPlanClicked(int position) {
        List<MealPlan> mealPlans = mMealPlanAdapter.getCurrentList();
        int mealPlanId = mealPlans.get(position).getId();
        Intent intent = new Intent(this, DailyMealPlanActivity.class);
        intent.putExtra("mealPlanId", mealPlanId);
        startActivity(intent);
    }
}
