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
import com.codelovely.thecooksnook.models.restmodels.FoodNutrient;

public class NutrientListAdapter extends ListAdapter<FoodNutrient, NutrientListAdapter.NutrientViewHolder> {


    public NutrientListAdapter(@NonNull DiffUtil.ItemCallback<FoodNutrient> diffCallback) {
        super(diffCallback);
    }


    /*
    onCreateViewHolder() is called right when the adapter is created and is used to initialize the ViewHolder.
     */
    @NonNull
    @Override
    public NutrientListAdapter.NutrientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View nutrientView = inflater.inflate(R.layout.recipe_nutrition_list_item, parent, false);
        return new NutrientViewHolder(nutrientView);
    }

    /*
    onBindViewHolder is called for each ViewHolder to bind it to the adapter.
    This is where we populate our individual RecyclerView items with data.
     */
    @Override
    public void onBindViewHolder(NutrientListAdapter.NutrientViewHolder holder, int position) {
        FoodNutrient current = getItem(position);
        holder.bind(current);
    }


    public static class NutrientsDiff extends DiffUtil.ItemCallback<FoodNutrient> {

        @Override
        public boolean areItemsTheSame(@NonNull FoodNutrient oldItem, @NonNull FoodNutrient newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull FoodNutrient oldItem, @NonNull FoodNutrient newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }

    /*
    This is the ViewHolder class for the adapter.
    The ViewHolder's job is to describe an item view and metadata about its place in the RecyclerView.
    It caches results for View.findViewById, which can otherwise be expensive.
     */
    static class NutrientViewHolder extends RecyclerView.ViewHolder {
        private TextView nutrientName;
        private TextView nutrientValue;

        NutrientViewHolder(View itemView) {
            super(itemView);

            nutrientName = itemView.findViewById(R.id.recipeNutritionListItem_nutrientName);
            nutrientValue = itemView.findViewById(R.id.recipeNutritionListItem_nutrientValue);
        }

        public void bind(FoodNutrient nutrient) {
            nutrientName.setText(nutrient.getNutrient().getName());
            nutrientValue.setText(nutrient.getAmount() + nutrient.getNutrient().getUnitName());
        }
    }
}

