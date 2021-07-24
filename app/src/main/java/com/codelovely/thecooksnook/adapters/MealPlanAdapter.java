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
import com.codelovely.thecooksnook.models.MealPlan;

public class MealPlanAdapter extends ListAdapter<MealPlan, MealPlanAdapter.MealPlanViewHolder> {
    private MealPlanAdapter.MealPlanListener mMealPlanListener;

    public MealPlanAdapter (@NonNull DiffUtil.ItemCallback<MealPlan> diffCallback, MealPlanListener listener) {
        super(diffCallback);
        mMealPlanListener = listener;
    }

    @NonNull
    @Override
    public MealPlanAdapter.MealPlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View mealPlanView = inflater.inflate(R.layout.meal_plan_item, parent, false);
        return new MealPlanViewHolder(mealPlanView, mMealPlanListener);
    }

    @Override
    public void onBindViewHolder(MealPlanAdapter.MealPlanViewHolder holder, int position) {
        MealPlan current = getItem(position);
        holder.bind(current);
    }


    public interface MealPlanListener {
        void onMealPlanClicked(int position);
    }

    public static class MealPlanDiff extends DiffUtil.ItemCallback<MealPlan> {

        @Override
        public boolean areItemsTheSame(@NonNull MealPlan oldItem, @NonNull MealPlan newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull MealPlan oldItem, @NonNull MealPlan newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }

    public static class MealPlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MealPlanListener mealPlanListener;
        TextView dateText;
        TextView dayOfWeekText;

        private MealPlanViewHolder(View itemView, MealPlanListener listener) {
            super(itemView);
            mealPlanListener = listener;
            dateText = itemView.findViewById(R.id.mealPlanItem_dateText);
            dayOfWeekText = itemView.findViewById(R.id.mealPlanItem_dayOfWeekText);

            itemView.setOnClickListener(this);
        }

        public void bind(MealPlan mealPlan) {
            dateText.setText(mealPlan.getMonth() + " " + mealPlan.getDay() + ", " + mealPlan.getYear());
            dayOfWeekText.setText(mealPlan.getDayOfWeek());
        }

        @Override
        public void onClick(View view) {
            mealPlanListener.onMealPlanClicked(getBindingAdapterPosition());
        }
    }


}
