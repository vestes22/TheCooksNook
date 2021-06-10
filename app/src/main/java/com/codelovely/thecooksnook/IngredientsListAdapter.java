package com.codelovely.thecooksnook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codelovely.thecooksnook.data.MainFoodDesc;
import com.codelovely.thecooksnook.models.FoodOption;
import com.codelovely.thecooksnook.models.FoodPortion;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class IngredientsListAdapter extends ListAdapter<FoodOption, IngredientsListAdapter.IngredientsViewHolder> {


    public IngredientsListAdapter(@NonNull DiffUtil.ItemCallback<FoodOption> diffCallback) {
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
        FoodOption current = getItem(position);
        holder.bind(current);

    }


    static class IngredientsDiff extends DiffUtil.ItemCallback<FoodOption> {

        @Override
        public boolean areItemsTheSame(@NonNull FoodOption oldItem, @NonNull FoodOption newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull FoodOption oldItem, @NonNull FoodOption newItem) {
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
        private EditText ingredientQty;
        private AutoCompleteTextView unit;
        private Context context;

        public IngredientsViewHolder(View itemView) {
            super(itemView);

            ingredientName = (TextView) itemView.findViewById(R.id.ingredient_name);
            ingredientQty = (TextInputEditText) itemView.findViewById(R.id.qty_edit_text);
            unit = (AutoCompleteTextView) itemView.findViewById(R.id.my_unit_spinner);
            context = itemView.getContext();
        }

        public void bind(FoodOption foodOption) {
            ingredientName.setText(foodOption.getFoodName());
            List<FoodPortion> foodPortions = foodOption.getFoodPortions();
            List<String> portionStrings = new ArrayList<>();
            for (FoodPortion portion : foodPortions) {
                portionStrings.add(portion.getPortionDesc());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, portionStrings);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            unit.setAdapter(adapter);
        }
    }
}
