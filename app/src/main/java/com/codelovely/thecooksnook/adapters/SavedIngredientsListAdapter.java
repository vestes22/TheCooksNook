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
import com.codelovely.thecooksnook.models.IngredientModel;

public class SavedIngredientsListAdapter extends ListAdapter<IngredientModel, SavedIngredientsListAdapter.IngredientsViewHolder> {

    private IngredientsListener mIngredientsListener;


    public SavedIngredientsListAdapter(@NonNull DiffUtil.ItemCallback<IngredientModel> diffCallback, IngredientsListener ingredientsListener) {
        super(diffCallback);
        mIngredientsListener = ingredientsListener;
    }


    /*
    onCreateViewHolder() is called right when the adapter is created and is used to initialize the ViewHolder.
     */
    @NonNull
    @Override
    public SavedIngredientsListAdapter.IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ingredientView = inflater.inflate(R.layout.saved_ingredients_list_item, parent, false);
        return new IngredientsViewHolder(ingredientView, mIngredientsListener);
    }

    /*
    onBindViewHolder is called for each ViewHolder to bind it to the adapter.
    This is where we populate our individual RecyclerView items with data.
     */
    @Override
    public void onBindViewHolder(SavedIngredientsListAdapter.IngredientsViewHolder holder, int position) {
        IngredientModel current = getItem(position);
        holder.bind(current);
    }


    public interface IngredientsListener {
        void onIngredientClicked(int position);
    }

    public static class IngredientsDiff extends DiffUtil.ItemCallback<IngredientModel> {

        @Override
        public boolean areItemsTheSame(@NonNull IngredientModel oldItem, @NonNull IngredientModel newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull IngredientModel oldItem, @NonNull IngredientModel newItem) {
            return oldItem.getFdcId() == newItem.getFdcId();
        }
    }

    /*
    This is the ViewHolder class for the adapter.
    The ViewHolder's job is to describe an item view and metadata about its place in the RecyclerView.
    It caches results for View.findViewById, which can otherwise be expensive.
     */
    static class IngredientsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView ingredientName;
        private TextView ingredientQty;
        IngredientsListener listener;

        IngredientsViewHolder(View itemView, IngredientsListener listener) {
            super(itemView);

            this.listener = listener;
            ingredientName = itemView.findViewById(R.id.savedIngredientsListItem_ingredientName);
            ingredientQty = itemView.findViewById(R.id.savedIngredientsListItem_ingredientQty);
            itemView.setOnClickListener(this);
        }

        public void bind(IngredientModel ingredient) {
            ingredientName.setText(ingredient.getDescription());
            ingredientQty.setText(Float.toString(ingredient.getAmountInRecipe()) + ingredient.getServingSizeUnit());
        }

        @Override
        public void onClick(View view) {
            listener.onIngredientClicked(getBindingAdapterPosition());
        }
    }
}

