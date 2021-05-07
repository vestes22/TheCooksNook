package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CookBookHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book_home);
    }

    public void onBreakfastButtonClicked(View view) {
        Intent intent = new Intent(this, CookBookActivity.class);
        startActivity(intent);
    }

    public void addRecipeButtonClicked(View view) {
        Intent intent = new Intent(this, AddRecipeActivity.class);
        startActivity(intent);
    }
}
