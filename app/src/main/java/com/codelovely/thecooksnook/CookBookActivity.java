package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class CookBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book);

        // Gets the category passed from the previous intent.
        Intent intent = getIntent();
        String category = intent.getExtras().getString("category");
    }
}
