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
import android.widget.Button;

import com.codelovely.thecooksnook.adapters.RecipeAdapter;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.UserModel;
import com.codelovely.thecooksnook.viewmodels.CookBookViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

public class
CookBookActivity extends AppCompatActivity implements RecipeAdapter.RecipeListener {
    CookBookViewModel mCookBookViewModel;
    RecipeAdapter recipeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book);

        mCookBookViewModel = new ViewModelProvider(this).get(CookBookViewModel.class);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        UserModel user = new UserModel();
        user.setFirstName(account.getGivenName());
        user.setLastName(account.getFamilyName());
        user.setUserId(account.getId());
        mCookBookViewModel.setUser(user);

        // Gets the category passed from the previous intent.
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        String category = extras.getString("category");
        if (category.equals("ALL")) {
            mCookBookViewModel.setAllRecipes();
        }
        else {
            mCookBookViewModel.setRecipesByCategory(category);
        }

        RecyclerView recipeRv = findViewById(R.id.cookBook_recipeRecyclerview);
        recipeAdapter = new RecipeAdapter(new RecipeAdapter.RecipeDiff(), this);
        recipeRv.setAdapter(recipeAdapter);
        recipeRv.setLayoutManager(new LinearLayoutManager(this));

        final Observer<List<RecipeModel>> recipeListObserver = new Observer<List<RecipeModel>> () {
            @Override
            public void onChanged(@Nullable final List<RecipeModel> recipeList) {
                recipeAdapter.submitList(null);
                recipeAdapter.submitList(recipeList);
            }
        };

        mCookBookViewModel.getRecipesByCategory().observe(this, recipeListObserver);

        final Button button = findViewById(R.id.cookbook_closeButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CookBookActivity.this, CookBookHomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRecipeClicked(int position) {
        List<RecipeModel> currentList =  recipeAdapter.getCurrentList();
        RecipeModel recipe = currentList.get(position);
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("recipeId", recipe.getId());
        startActivity(intent);
    }
}
