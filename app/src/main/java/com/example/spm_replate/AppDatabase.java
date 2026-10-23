package com.example.spm_replate;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {PantryItem.class, Recipe.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PantryDao pantryDao();
    public abstract RecipeDao recipeDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "pantry_database")
                            .addCallback(roomCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(() -> {
                RecipeDao dao = INSTANCE.recipeDao();
                List<Recipe> seededRecipes = new ArrayList<>();

                seededRecipes.add(new Recipe("Scrambled Eggs", "Whisk eggs, cook on skillet.", "egg:2,butter:1"));
                seededRecipes.add(new Recipe("Pancakes", "Mix flour, milk, egg and fry.", "flour:1,milk:1,egg:1"));

                // Note: You must add 13 more recipes here later to meet the 15-20 recipe requirement
                dao.insertAll(seededRecipes);
            });
        }
    };
}