package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class CookBookHomeActivity extends AppCompatActivity {
    TextView activityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book_home);
        activityTitle = findViewById(R.id.cookBookHome_activityTitle);
    }

    @Override
    protected void onStart() {
        super.onStart();
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        activityTitle.setText(account.getGivenName() +"'s Cook Book");
    }

    public void onBreakfastButtonClicked(View view) {
        Intent intent = new Intent(this, CookBookActivity.class);
        intent.putExtra("category", RecipeCategory.BREAKFAST.toString());
        startActivity(intent);
    }

    public void addRecipeButtonClicked(View view) {
        Intent intent = new Intent(this, AddRecipeActivity.class);
        startActivity(intent);
    }

    public void onLunchButtonClicked(View view) {
        Intent intent = new Intent(this, CookBookActivity.class);
        intent.putExtra("category", RecipeCategory.LUNCH.toString());
        startActivity(intent);
    }

    public void onDinnerButtonClicked(View view) {
        Intent intent = new Intent(this, CookBookActivity.class);
        intent.putExtra("category", RecipeCategory.DINNER.toString());
        startActivity(intent);
    }

    public void onAppetizerButtonClicked(View view) {
        Intent intent = new Intent(this, CookBookActivity.class);
        intent.putExtra("category", RecipeCategory.APPETIZER.toString());
        startActivity(intent);
    }

    public void onViewAllButtonClicked(View view) {
        Intent intent = new Intent(this, CookBookActivity.class);
        intent.putExtra("category", "ALL");
        startActivity(intent);
    }

    public void onDrinkButtonClicked(View view) {
        Intent intent = new Intent(this, CookBookActivity.class);
        intent.putExtra("category", RecipeCategory.DRINK.toString());
        startActivity(intent);
    }

    public void onDessertButtonClicked(View view) {
        Intent intent = new Intent(this, CookBookActivity.class);
        intent.putExtra("category", RecipeCategory.DESSERT.toString());
        startActivity(intent);
    }
}
