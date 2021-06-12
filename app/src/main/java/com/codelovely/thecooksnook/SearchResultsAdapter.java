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


public class SearchResultsAdapter extends ListAdapter<MainFoodDesc, SearchResultsAdapter.SearchResultsViewHolder> {

    // The listener allows us to detect when an item in the RecyclerView is clicked.
    private SearchResultsListener mSearchResultsListener;



    public SearchResultsAdapter (@NonNull DiffUtil.ItemCallback<MainFoodDesc> diffCallback, SearchResultsListener searchResultsListener) {
        super(diffCallback);
        mSearchResultsListener = searchResultsListener;
    }

    /*
    onCreateViewHolder is called right when the adapter is created and is used to initialize the ViewHolder.
     */
    @Override
    public SearchResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View searchResultView = inflater.inflate(R.layout.search_results_item, parent, false);
        SearchResultsViewHolder holder = new SearchResultsViewHolder(searchResultView, mSearchResultsListener);
        return holder;
    }

    /*
    onBindViewHolder is called for each ViewHOlder to bind it to the adapter.
    This is where we populate our individual RecyclerView items with data.
     */
    @Override
    public void onBindViewHolder(SearchResultsViewHolder holder, int position) {
        MainFoodDesc current = getItem(position);
        holder.bind(current.getMainFoodDesc());
    }

    /*
    This interface is used to detect when a user clicks on a RecyclerView item.
    The SearchResultsAdapter uses it by allowing the user to search for ingredients,
    and click on an ingredient in the search results to add it to their ingredients list for the recipe.
     */
    public interface SearchResultsListener {
        void onSearchResultClicked(int position);
    }

    /*
    DiffUtil classes are still an enigma to me... I will def spend some more time learning about them.
    For now, I understand they are used for efficiently updating lists of data by comparing values,
    and only updating positions where the values do not match.
     */
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

    /*
    This is the ViewHolder class for the adapter.
    The ViewHolder's job i to describe an item view and metadata about its place in the RecyclerView.
    It caches results for View.findViewById, which can otherwise be expensive.
     */
    public class SearchResultsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView ingredientTextView;
        //private Button addIngredientButton;
        SearchResultsListener listener;

        private SearchResultsViewHolder(View itemView, SearchResultsListener listener) {
            super(itemView);
            this.listener = listener;
            ingredientTextView = (TextView) itemView.findViewById(R.id.ingredient_name);
            //addIngredientButton = (Button) itemView.findViewById(R.id.add_ingredient_button);

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
}
