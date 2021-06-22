package com.codelovely.thecooksnook.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codelovely.thecooksnook.R;
import com.codelovely.thecooksnook.models.Ingredient;
import com.codelovely.thecooksnook.models.FoodPortion;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class EditableIngredientsListAdapter extends ListAdapter<Ingredient, EditableIngredientsListAdapter.IngredientsViewHolder> {

    // Returns a list of ingredients for the recipe, including the values grabbed from the EditTexts
    private List<Ingredient> _ingredients;

    public void updateIngredients(List<Ingredient> ingredients) {
        _ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return _ingredients;
    }


    public EditableIngredientsListAdapter(@NonNull DiffUtil.ItemCallback<Ingredient> diffCallback) {
        super(diffCallback);
    }


    /*
    onCreateViewHolder() is called right when the adapter is created and is used to initialize the ViewHolder.
     */
    @Override
    public EditableIngredientsListAdapter.IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ingredientView = inflater.inflate(R.layout.editable_ingredients_list_item, parent, false);
        IngredientsViewHolder holder = new IngredientsViewHolder(ingredientView);
        return holder;
    }

    /*
    onBindViewHolder is called for each ViewHolder to bind it to the adapter.
    This is where we populate our individual RecyclerView items with data.
     */
    @Override
    public void onBindViewHolder(EditableIngredientsListAdapter.IngredientsViewHolder holder, int position) {
        Ingredient current = getItem(position);
        holder.ingredientQty.setText(String.valueOf(_ingredients.get(position).getQty()));
        holder.unit.setText(_ingredients.get(position).getSelectedPortion().getPortionDesc(), false);
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
        private EditText ingredientQty;
        private AutoCompleteTextView unit;
        private Context context;

        public IngredientsViewHolder(View itemView) {
            super(itemView);

            ingredientName = (TextView) itemView.findViewById(R.id.ingredientListItem_ingredientName);
            ingredientQty = (TextInputEditText) itemView.findViewById(R.id.ingredientListItem_qtyEditText);
            unit = (AutoCompleteTextView) itemView.findViewById(R.id.ingredientListItem_portionOptionMenuItem);
            context = itemView.getContext();
        }

        public void bind(Ingredient ingredient) {
            ingredientName.setText(ingredient.getFoodName());
            final List<FoodPortion> foodPortions = ingredient.getFoodPortions();
            List<String> portionStrings = new ArrayList<>();
            for (FoodPortion portion : foodPortions) {
                portionStrings.add(portion.getPortionDesc());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, portionStrings);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            unit.setAdapter(adapter);

            ingredientQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(!ingredientQty.getText().toString().equals("")) {
                         _ingredients.get(getAdapterPosition()).setQty(Float.parseFloat(ingredientQty.getText().toString()));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            unit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(!unit.getText().toString().equals("")) {
                        String portion = unit.getText().toString();
                        for (FoodPortion foodPortion : foodPortions) {
                            if(portion.equals(foodPortion.getPortionDesc())) {
                                _ingredients.get(getAdapterPosition()).setSelectedPortion(foodPortion);
                            }
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }
    }
}
