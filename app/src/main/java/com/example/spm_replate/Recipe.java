package com.example.spm_replate;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe_table")
class Recipe {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String instructions;
    public String ingredientsList; // Stored as comma-separated string e.g., "egg:2,milk:1"

    public Recipe() {}
    public Recipe(String title, String instructions, String ingredientsList) {
        this.title = title;
        this.instructions = instructions;
        this.ingredientsList = ingredientsList;
    }
}
