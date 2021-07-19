package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

public class HomeScreenActivity extends AppCompatActivity {
    TextView personalCookBookTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        personalCookBookTitle = findViewById(R.id.homeScreen_cookBookCard_title);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    public void cookBookButtonClicked(View view) {
        Intent intent = new Intent(this, CookBookHomeActivity.class);
        startActivity(intent);
    }


    public void recommendedRecipeClicked(View view) {
        Intent intent = new Intent(this, RecipeActivity.class);
        startActivity(intent);
    }

    public void shoppingListClicked(View view) {
        // TODO
    }

    public void mealPlannerClicked(View view) {
        Intent intent = new Intent(this, MealPlannerActivity.class);
        startActivity(intent);
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            final String givenName = account.getFamilyName();

            String cookBookTitle = getString(R.string.personal_cook_book_title, givenName);
            personalCookBookTitle.setText(cookBookTitle);
        }
    }
}
