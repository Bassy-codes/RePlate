package com.example.spm_replate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {
    private List<Recipe> recipes = new ArrayList<>();

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new RecipeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        Recipe currentRecipe = recipes.get(position);
        holder.textViewTitle.setText(currentRecipe.title);
        holder.textViewIngredients.setText("Requires: " + currentRecipe.ingredientsList);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    class RecipeHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewIngredients;

        public RecipeHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_recipe_title);
            textViewIngredients = itemView.findViewById(R.id.text_recipe_ingredients);
        }
    }
}
