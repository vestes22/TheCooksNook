package com.codelovely.thecooksnook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codelovely.thecooksnook.data.MainFoodDesc;

public class IngredientsListAdapter extends ListAdapter<MainFoodDesc, IngredientsListAdapter.IngredientsViewHolder> {



    /*
    A static class, SearchResultsDiff is described in the SearchResultsAdapter class.
    Do we need to write an inner DiffUtil class for each adapter?
    Or can that static class be reused?
     */
    public IngredientsListAdapter(@NonNull DiffUtil.ItemCallback<MainFoodDesc> diffCallback) {
        super(diffCallback);
    }


    /*
    onCreateViewHolder() is called right when the adapter is created and is used to initialize the ViewHolder.
     */
    @Override
    public IngredientsListAdapter.IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ingredientView = inflater.inflate(R.layout.ingredients_list_item, parent, false);
        IngredientsViewHolder holder = new IngredientsViewHolder(ingredientView);
        return holder;
    }

    /*
    onBindViewHolder is called for each ViewHolder to bind it to the adapter.
    This is where we populate our individual RecyclerView items with data.
     */
    @Override
    public void onBindViewHolder(IngredientsListAdapter.IngredientsViewHolder holder, int position) {
        MainFoodDesc current = getItem(position);
        holder.bind(current);

    }

    /*
    This is the ViewHolder class for the adapter.
    The ViewHolder's job is to describe an item view and metadata about its place in the RecyclerView.
    It caches results for View.findViewById, which can otherwise be expensive.
     */
    class IngredientsViewHolder extends RecyclerView.ViewHolder {
        private TextView ingredientName;
        private EditText ingredientQty;
        private Spinner unit;

        public IngredientsViewHolder(View itemView) {
            super(itemView);

            ingredientName = (TextView) itemView.findViewById(R.id.ingredient_name);
            ingredientQty = (EditText) itemView.findViewById(R.id.qtyEditText);
            unit = (Spinner) itemView.findViewById(R.id.unitSpinner);
        }

        public void bind(MainFoodDesc mainFoodDesc) {
            ingredientName.setText(mainFoodDesc.getMainFoodDesc());
            // The EditText, ingredientQty, is initially left blank for the user to fill out.
            // TODO - set the data for the portions spinner.
        }
    }
}
