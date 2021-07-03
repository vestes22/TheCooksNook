package com.codelovely.thecooksnook.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codelovely.thecooksnook.R;
import com.codelovely.thecooksnook.models.IngredientModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class EditableIngredientsListAdapter extends ListAdapter<IngredientModel, EditableIngredientsListAdapter.IngredientsViewHolder> {

    // Returns a list of ingredients for the recipe, including the values grabbed from the EditTexts
    private List<IngredientModel> _ingredients;

    // Called by
    public void updateIngredients(List<IngredientModel> ingredients) {
        _ingredients = ingredients;
    }

    public List<IngredientModel> getIngredients() {
        return _ingredients;
    }


    public EditableIngredientsListAdapter(@NonNull DiffUtil.ItemCallback<IngredientModel> diffCallback) {
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
        IngredientModel current = getItem(position);
        holder.ingredientQty.setText(String.valueOf(_ingredients.get(position).getAmountInRecipe()));
        holder.bind(current);
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
    class IngredientsViewHolder extends RecyclerView.ViewHolder {
        private TextView ingredientName;
        private EditText ingredientQty;
        private TextView unit;
        private Context context;

        public IngredientsViewHolder(View itemView) {
            super(itemView);

            ingredientName = (TextView) itemView.findViewById(R.id.ingredientListItem_ingredientName);
            ingredientQty = (TextInputEditText) itemView.findViewById(R.id.ingredientListItem_qtyEditText);
            unit =  itemView.findViewById(R.id.ingredientListItem_servingUnit);
            context = itemView.getContext();
        }

        public void bind(IngredientModel ingredient) {
            ingredientName.setText(ingredient.getDescription());
            unit.setText(ingredient.getServingSizeUnit());


            ingredientQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(!ingredientQty.getText().toString().equals("")) {
                        _ingredients.get(getAdapterPosition()).setAmountInRecipe(Float.parseFloat(ingredientQty.getText().toString()));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
}
