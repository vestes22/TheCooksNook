package com.codelovely.thecooksnook;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.ViewHolder> {
    private String recipeTitles[];
    private String recipeDescriptions[];

    public RecipeCardAdapter(String[] recipeTitles, String[] recipeDescriptions) {
        this.recipeTitles = recipeTitles;
        this.recipeDescriptions = recipeDescriptions;
    }

    @NonNull
    @Override
    public RecipeCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        // TODO
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeCardAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return recipeTitles.length;
    }
}
