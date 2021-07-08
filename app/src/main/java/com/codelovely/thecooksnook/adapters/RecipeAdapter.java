package com.codelovely.thecooksnook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codelovely.thecooksnook.R;
import com.codelovely.thecooksnook.models.RecipeModel;

public class RecipeAdapter extends ListAdapter<RecipeModel, RecipeAdapter.RecipeViewHolder> {

    // The listener allows us to detect when an item in the RecyclerView is clicked.
    private RecipeAdapter.RecipeListener mRecipeListener;



    public RecipeAdapter (@NonNull DiffUtil.ItemCallback<RecipeModel> diffCallback, RecipeListener listener) {
        super(diffCallback);
        mRecipeListener = listener;
    }

    /*
    onCreateViewHolder is called right when the adapter is created and is used to initialize the ViewHolder.
     */
    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View recipeView = inflater.inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(recipeView, mRecipeListener);
    }

    /*
    onBindViewHolder is called for each ViewHOlder to bind it to the adapter.
    This is where we populate our individual RecyclerView items with data.
     */
    @Override
    public void onBindViewHolder(RecipeAdapter.RecipeViewHolder holder, int position) {
        RecipeModel current = getItem(position);
        holder.bind(current);
    }

    /*
    This interface is used to detect when a user clicks on a RecyclerView item.
    The SearchResultsAdapter uses it by allowing the user to search for ingredients,
    and click on an ingredient in the search results to add it to their ingredients list for the recipe.
     */
    public interface RecipeListener {
        void onRecipeClicked(int position);
    }

    /*
    DiffUtil classes are still an enigma to me... I will def spend some more time learning about them.
    For now, I understand they are used for efficiently updating lists of data by comparing values,
    and only updating positions where the values do not match.
     */
    public static class RecipeDiff extends DiffUtil.ItemCallback<RecipeModel> {

        @Override
        public boolean areItemsTheSame(@NonNull RecipeModel oldItem, @NonNull RecipeModel newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull RecipeModel oldItem, @NonNull RecipeModel newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }




    /*
    This is the ViewHolder class for the adapter.
    The ViewHolder's job i to describe an item view and metadata about its place in the RecyclerView.
    It caches results for View.findViewById, which can otherwise be expensive.
     */
    public static class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView recipeName;
        private TextView recipeDescription;
        RecipeListener listener;

        private RecipeViewHolder(View itemView, RecipeListener listener) {
            super(itemView);
            this.listener = listener;
            recipeName = itemView.findViewById(R.id.recipeList_recipeTitle);
            recipeDescription = itemView.findViewById(R.id.recipeList_recipeDescription);

            itemView.setOnClickListener(this);
        }

        public void bind(RecipeModel recipe) {

            recipeName.setText(recipe.getName());
            recipeDescription.setText(recipe.getDescription());
        }

        @Override
        public void onClick(View view) {

            listener.onRecipeClicked(getBindingAdapterPosition());
        }
    }
}
