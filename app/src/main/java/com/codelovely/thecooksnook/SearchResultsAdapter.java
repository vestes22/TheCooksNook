package com.codelovely.thecooksnook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codelovely.thecooksnook.data.MainFoodDesc;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    // Provide a direct reference to each view within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // The ViewHolder class should contain a member variable for
        // any view that will be set as you render a row
        public TextView ingredientTextView;
        public Button addIngredientButton;

        // A public constructor to accept the entire item row and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            super(itemView);

            ingredientTextView = (TextView) itemView.findViewById(R.id.ingredient_name);
            addIngredientButton = (Button) itemView.findViewById(R.id.add_ingredient_button);
        }
    }

    private List<MainFoodDesc> mSearchResults;
    public SearchResultsAdapter(List<MainFoodDesc> searchResults) {
        mSearchResults = searchResults;
    }

    @Override
    public int getItemCount() {
        return mSearchResults.size();
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public SearchResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View searchResultsView = inflater.inflate(R.layout.search_results_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(searchResultsView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(SearchResultsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        MainFoodDesc ingredient = mSearchResults.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.ingredientTextView;
        textView.setText(ingredient.getMainFoodDesc());
    }
}
