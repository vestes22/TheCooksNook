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
import com.codelovely.thecooksnook.models.Nutrient;

public class NutrientListAdapter extends ListAdapter<Nutrient, NutrientListAdapter.NutrientViewHolder> {


    public NutrientListAdapter(@NonNull DiffUtil.ItemCallback<Nutrient> diffCallback, RecipeActivity recipeActivity) {
        super(diffCallback);
    }


    /*
    onCreateViewHolder() is called right when the adapter is created and is used to initialize the ViewHolder.
     */
    @Override
    public NutrientListAdapter.NutrientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View nutrientView = inflater.inflate(R.layout.recipe_nutrition_list_item, parent, false);
        NutrientListAdapter.NutrientViewHolder holder = new NutrientListAdapter.NutrientViewHolder(nutrientView);
        return holder;
    }

    /*
    onBindViewHolder is called for each ViewHolder to bind it to the adapter.
    This is where we populate our individual RecyclerView items with data.
     */
    @Override
    public void onBindViewHolder(NutrientListAdapter.NutrientViewHolder holder, int position) {
        Nutrient current = getItem(position);
        holder.bind(current);
    }


    public static class NutrientsDiff extends DiffUtil.ItemCallback<Nutrient> {

        @Override
        public boolean areItemsTheSame(@NonNull Nutrient oldItem, @NonNull Nutrient newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Nutrient oldItem, @NonNull Nutrient newItem) {
            return oldItem.getNutrientCode() == newItem.getNutrientCode();
        }
    }

    /*
    This is the ViewHolder class for the adapter.
    The ViewHolder's job is to describe an item view and metadata about its place in the RecyclerView.
    It caches results for View.findViewById, which can otherwise be expensive.
     */
    class NutrientViewHolder extends RecyclerView.ViewHolder {
        private TextView nutrientName;
        private TextView nutrientValue;

        public NutrientViewHolder(View itemView) {
            super(itemView);

            nutrientName = (TextView) itemView.findViewById(R.id.recipeNutritionListItem_nutrientName);
            nutrientValue = (TextView) itemView.findViewById(R.id.recipeNutritionListItem_nutrientValue);
        }

        public void bind(Nutrient nutrient) {
            nutrientName.setText(nutrient.getNutrientDescription());
            nutrientValue.setText(Float.toString(nutrient.getNutrientValue()) + nutrient.getUnit());
        }
    }
}

