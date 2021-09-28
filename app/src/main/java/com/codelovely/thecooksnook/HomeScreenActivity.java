package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

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

    public void userProfileCardClicked(View view) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

    public void cookBookButtonClicked(View view) {
        Intent intent = new Intent(this, CookBookHomeActivity.class);
        startActivity(intent);
    }

    public void mealPlannerClicked(View view) {
        Intent intent = new Intent(this, MealPlannerActivity.class);
        startActivity(intent);
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            final String givenName = account.getGivenName();

            String cookBookTitle = getString(R.string.personal_cook_book_title, givenName);
            personalCookBookTitle.setText(cookBookTitle);
        }
    }
}
