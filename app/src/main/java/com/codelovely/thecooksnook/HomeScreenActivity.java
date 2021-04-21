package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;
import androidx.room.Room;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.codelovely.thecooksnook.data.*;

import com.codelovely.thecooksnook.data.AddFoodDescDao;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void cookBookButtonClicked(View view) {
        Intent intent = new Intent(this, CookBookHomeActivity.class);
        startActivity(intent);
    }

    public void databaseStuff(View view) {

        NutritionInformationDatabase db = NutritionInformationDatabase.getDatabase(this);
        final DerivDescDao derivDescDao = db.getDerivDescDao();
        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run()  {
                List<DerivDesc> derivDescs = derivDescDao.getAll();
                System.out.println("Results returned: " + derivDescs.size());
                for(int i = 0; i < derivDescs.size(); i++) {
                    System.out.println("Deriv code: " + derivDescs.get(i).getDerivationCode());
                    System.out.println("Description: " + derivDescs.get(i).getDerivationDesc());
                }
            }
        });
    }

    public void recommendedRecipeClicked(View view) {
        Intent intent = new Intent(this, RecipeActivity.class);
        startActivity(intent);
    }
}
