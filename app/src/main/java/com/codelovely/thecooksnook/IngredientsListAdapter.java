package com.codelovely.thecooksnook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codelovely.thecooksnook.data.MainFoodDesc;

import org.w3c.dom.Text;

import java.util.List;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientsViewHolder> {
    private List<MainFoodDesc> mIngredients;

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        private TextView ingredientName;
        private EditText ingredientQty;
        private Spinner unit;

        public IngredientsViewHolder(View itemView) {
            super(itemView);

            ingredientName = (TextView) itemView.findViewById(R.id.ingredient_name);
            ingredientQty = (EditText) itemView.findViewById(R.id.qtyEditText);
            unit = (Spinner) itemView.findViewById(R.id.unitSpinner);
        }

    }

    public IngredientsListAdapter(List<MainFoodDesc> ingredients) {
        mIngredients = ingredients;
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    @Override
    public IngredientsListAdapter.IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ingredientView = inflater.inflate(R.layout.ingredients_list_item, parent, false);
        IngredientsViewHolder holder = new IngredientsViewHolder(ingredientView);
        return holder;
    }

    @Override
    public void onBindViewHolder(IngredientsListAdapter.IngredientsViewHolder holder, int position) {
        MainFoodDesc ingredient = mIngredients.get(position);

        TextView ingredientName = holder.ingredientName;
        ingredientName.setText(ingredient.getMainFoodDesc());
        List<String> spinnerData;


        // TODO - POPULATE THE UNITS SPINNER
        // Then move on to "Binding the adapter to the recyclerview"
    }
}
