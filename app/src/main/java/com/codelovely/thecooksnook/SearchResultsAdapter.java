package com.codelovely.thecooksnook;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codelovely.thecooksnook.data.MainFoodDesc;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder> {

    private List<MainFoodDesc> mSearchResults;

    public class SearchResultsViewHolder extends RecyclerView.ViewHolder {
        private TextView ingredientTextView;
        private Button addIngredientButton;

        private SearchResultsViewHolder(View itemView) {
            super(itemView);

            ingredientTextView = (TextView) itemView.findViewById(R.id.ingredient_name);
            addIngredientButton = (Button) itemView.findViewById(R.id.add_ingredient_button);
        }

        public void bind(String text) {
            ingredientTextView.setText(text);
        }
    }

    public SearchResultsAdapter (List<MainFoodDesc> searchResults) {
        mSearchResults = searchResults;
    }

    @Override
    public int getItemCount() {
        if(mSearchResults == null) {
            return 0;
        }
        else {
            return mSearchResults.size();
        }
    }

    @Override
    public SearchResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View searchResultView = inflater.inflate(R.layout.search_results_item, parent, false);
        SearchResultsViewHolder holder = new SearchResultsViewHolder(searchResultView);
        return holder;
    }

    @Override
    public void onBindViewHolder(SearchResultsViewHolder holder, int position) {
        MainFoodDesc current = mSearchResults.get(position);
        holder.bind(current.getMainFoodDesc());
    }



    static class SearchResultsDiff extends DiffUtil.ItemCallback<MainFoodDesc> {

        @Override
        public boolean areItemsTheSame(@NonNull MainFoodDesc oldItem, @NonNull MainFoodDesc newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull MainFoodDesc oldItem, @NonNull MainFoodDesc newItem) {
            return oldItem.getFoodId() == newItem.getFoodId();
        }
    }
}
