package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MealPlannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_planner);
    }

    public void addMealPlanButtonClickedClicked(View view) {
        Intent intent = new Intent(this, AddMealPlanActivity.class);
        startActivity(intent);
    }
}
