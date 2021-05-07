package com.codelovely.thecooksnook;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.codelovely.thecooksnook.data.MainFoodDesc;
import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder> {

    private List<MainFoodDesc> mSearchResults;
    private SearchResultsListener mSearchResultsListener;

    public class SearchResultsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView ingredientTextView;
        private Button addIngredientButton;
        SearchResultsListener listener;

        private SearchResultsViewHolder(View itemView, SearchResultsListener listener) {
            super(itemView);
            this.listener = listener;
            ingredientTextView = (TextView) itemView.findViewById(R.id.ingredient_name);
            addIngredientButton = (Button) itemView.findViewById(R.id.add_ingredient_button);

            itemView.setOnClickListener(this);
        }

        public void bind(String text) {
            ingredientTextView.setText(text);
        }

        @Override
        public void onClick(View view) {
            listener.onSearchResultClicked(getAdapterPosition());
        }
    }

    public SearchResultsAdapter (List<MainFoodDesc> searchResults, SearchResultsListener searchResultsListener) {
        mSearchResults = searchResults;
        mSearchResultsListener = searchResultsListener;
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
        SearchResultsViewHolder holder = new SearchResultsViewHolder(searchResultView, mSearchResultsListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(SearchResultsViewHolder holder, int position) {
        MainFoodDesc current = mSearchResults.get(position);
        holder.bind(current.getMainFoodDesc());
    }

    public interface SearchResultsListener {
        void onSearchResultClicked(int position);
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
