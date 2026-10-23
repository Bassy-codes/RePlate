package com.example.spm_replate;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add test recipes to the database automatically
        seedTestRecipes();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PantryFragment()).commit();
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_pantry) {
                selectedFragment = new PantryFragment();
            } else if (itemId == R.id.nav_recipes) {
                selectedFragment = new SuggestedRecipesFragment();
            } else if (itemId == R.id.nav_settings) {
                selectedFragment = new SettingsFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment).commit();
            }
            return true;
        });
    }

    private void seedTestRecipes() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getInstance(MainActivity.this);
                // Only add recipes if the database is empty
                if (db.recipeDao().getAllRecipes().isEmpty()) {
                    Recipe pancakes = new Recipe();
                    pancakes.title = "Pancakes";
                    pancakes.ingredientsList = "Eggs, Milk, Flour, Butter";

                    Recipe omelette = new Recipe();
                    omelette.title = "Cheese Omelette";
                    omelette.ingredientsList = "Eggs, Cheese, Butter";

                    db.recipeDao().insert(pancakes);
                    db.recipeDao().insert(omelette);
                }
            }
        }).start();
    }
}