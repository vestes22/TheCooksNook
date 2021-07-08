package com.codelovely.thecooksnook.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codelovely.thecooksnook.R;
import com.codelovely.thecooksnook.models.restmodels.SearchResultFood;

import java.util.ArrayList;
import java.util.List;


public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder> {
    private List<SearchResultFood> results = new ArrayList<>();
    private SearchResultsListener mSearchResultsListener;


    public SearchResultsAdapter (SearchResultsListener searchResultsListener) {
        mSearchResultsListener = searchResultsListener;
    }


    @NonNull
    @Override
    public SearchResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View searchResultView = inflater.inflate(R.layout.search_results_item, parent, false);
        return new SearchResultsViewHolder(searchResultView, mSearchResultsListener);
    }


    @Override
    public int getItemCount() {
        return results.size();
    }


    @Override
    public void onBindViewHolder(@NonNull SearchResultsViewHolder holder, int position) {
        SearchResultFood current = results.get(position);
        if (current.getDescription() != null) {
            if (current.getAdditionalDescriptions() != null && current.getBrandOwner() != null) {
                holder.bind(current.getDescription() + " | " + current.getAdditionalDescriptions() + " | " + current.getBrandOwner());
            }
            else if (current.getAdditionalDescriptions() != null) {
                holder.bind(current.getDescription() + " | " + current.getAdditionalDescriptions());
            }
            else if (current.getBrandOwner() != null) {
                holder.bind(current.getDescription() + " | " + current.getBrandOwner());
            }
            else {
                holder.bind(current.getDescription());
            }
        }
    }


    public void setResults(List<SearchResultFood> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public List<SearchResultFood> getResults() {
        return results;
    }


    /*
    This interface is used to detect when a user clicks on a RecyclerView item.
    The SearchResultsAdapter uses it by allowing the user to search for ingredients,
    and click on an ingredient in the search results to add it to their ingredients list for the recipe.
     */
    public interface SearchResultsListener {
        void onSearchResultClicked(int position);
    }


    public static class SearchResultsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView ingredientTextView;
        SearchResultsListener listener;

        private SearchResultsViewHolder(@NonNull View itemView, SearchResultsListener listener) {
            super(itemView);
            this.listener = listener;
            ingredientTextView = itemView.findViewById(R.id.searchResultsItem_ingredientName);

            itemView.setOnClickListener(this);
        }

        public void bind(String text) {
            ingredientTextView.setText(text);
        }

        @Override
        public void onClick(View view) {
            listener.onSearchResultClicked(getBindingAdapterPosition());
        }
    }
}
