package com.example.spm_replate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesFragment extends Fragment {
    private RecipeAdapter adapter;
    private AppDatabase database;
    private TextView emptyStateText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggested_recipes, container, false);

        emptyStateText = view.findViewById(R.id.text_empty_state);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_recipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new RecipeAdapter();
        recyclerView.setAdapter(adapter);

        database = AppDatabase.getInstance(getContext());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        calculateMatchingRecipes();
    }

    private void calculateMatchingRecipes() {
        // 1. Fetch data from Room Database
        List<Recipe> allRecipes = database.recipeDao().getAllRecipes();
        List<PantryItem> pantryItems = database.pantryDao().getAllItems();

        List<Recipe> matchingRecipes = new ArrayList<>();

        // 2. Strict-Matching Logic
        for (Recipe recipe : allRecipes) {
            boolean canMake = true;

            if (recipe.ingredientsList != null) {
                // Split the recipe's required ingredients (e.g., "Eggs, Milk")
                String[] requiredIngredients = recipe.ingredientsList.split(",");

                for (String reqIng : requiredIngredients) {
                    boolean hasIngredient = false;
                    String cleanReq = reqIng.trim().toLowerCase();

                    // Check if this required ingredient exists in the user's pantry
                    for (PantryItem pItem : pantryItems) {
                        if (pItem.name.toLowerCase().contains(cleanReq)) {
                            hasIngredient = true;
                            break; // Found it, move to the next requirement
                        }
                    }

                    // If we finish checking the pantry and didn't find it, we can't make this recipe
                    if (!hasIngredient) {
                        canMake = false;
                        break;
                    }
                }
            } else {
                canMake = false;
            }

            // If all ingredients were found, add to the display list
            if (canMake) {
                matchingRecipes.add(recipe);
            }
        }

        // 3. Update UI: Show recipes OR show the empty state message
        if (matchingRecipes.isEmpty()) {
            emptyStateText.setVisibility(View.VISIBLE);
            adapter.setRecipes(new ArrayList<>());
        } else {
            emptyStateText.setVisibility(View.GONE);
            adapter.setRecipes(matchingRecipes);
        }
    }
}