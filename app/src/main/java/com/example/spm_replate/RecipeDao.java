package com.example.spm_replate;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface RecipeDao {
    @Insert
    void insert(Recipe recipe);
    @Insert
    void insertAll(List<Recipe> recipes);

    @Query("SELECT * FROM recipe_table")
    List<Recipe> getAllRecipes();
}
