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
import com.codelovely.thecooksnook.RecipeActivity;
import com.codelovely.thecooksnook.models.Ingredient;

public class SavedIngredientsListAdapter extends ListAdapter<Ingredient, SavedIngredientsListAdapter.IngredientsViewHolder> {


    public SavedIngredientsListAdapter(@NonNull DiffUtil.ItemCallback<Ingredient> diffCallback, RecipeActivity recipeActivity) {
        super(diffCallback);
    }


    /*
    onCreateViewHolder() is called right when the adapter is created and is used to initialize the ViewHolder.
     */
    @Override
    public SavedIngredientsListAdapter.IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ingredientView = inflater.inflate(R.layout.saved_ingredients_list_item, parent, false);
        SavedIngredientsListAdapter.IngredientsViewHolder holder = new SavedIngredientsListAdapter.IngredientsViewHolder(ingredientView);
        return holder;
    }

    /*
    onBindViewHolder is called for each ViewHolder to bind it to the adapter.
    This is where we populate our individual RecyclerView items with data.
     */
    @Override
    public void onBindViewHolder(SavedIngredientsListAdapter.IngredientsViewHolder holder, int position) {
        Ingredient current = getItem(position);
        holder.bind(current);
    }


    public static class IngredientsDiff extends DiffUtil.ItemCallback<Ingredient> {

        @Override
        public boolean areItemsTheSame(@NonNull Ingredient oldItem, @NonNull Ingredient newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Ingredient oldItem, @NonNull Ingredient newItem) {
            return oldItem.getFoodId() == newItem.getFoodId();
        }
    }

    /*
    This is the ViewHolder class for the adapter.
    The ViewHolder's job is to describe an item view and metadata about its place in the RecyclerView.
    It caches results for View.findViewById, which can otherwise be expensive.
     */
    class IngredientsViewHolder extends RecyclerView.ViewHolder {
        private TextView ingredientName;
        private TextView ingredientQty;
        private TextView unit;

        public IngredientsViewHolder(View itemView) {
            super(itemView);

            ingredientName = (TextView) itemView.findViewById(R.id.savedIngredientsListItem_ingredientName);
            ingredientQty = (TextView) itemView.findViewById(R.id.savedIngredientsListItem_ingredientQty);
            unit = (TextView) itemView.findViewById(R.id.savedIngredientsListItem_ingredientPortion);
        }

        public void bind(Ingredient ingredient) {
            ingredientName.setText(ingredient.getFoodName());
            ingredientQty.setText(Float.toString(ingredient.getQty()));
            unit.setText(ingredient.getSelectedPortion().getPortionDesc());
        }
    }
}

