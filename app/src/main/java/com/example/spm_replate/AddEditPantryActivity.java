package com.example.spm_replate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditPantryActivity extends AppCompatActivity {
    private EditText editTextName, editTextQuantity, editTextUnit, editTextExpiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_pantry);

        editTextName = findViewById(R.id.edit_text_name);
        editTextQuantity = findViewById(R.id.edit_text_quantity);
        editTextUnit = findViewById(R.id.edit_text_unit);
        editTextExpiry = findViewById(R.id.edit_text_expiry);
        Button buttonSave = findViewById(R.id.button_save);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIngredient();
            }
        });
    }

    private void saveIngredient() {
        String name = editTextName.getText().toString().trim();
        String quantityStr = editTextQuantity.getText().toString().trim();
        String unit = editTextUnit.getText().toString().trim();
        String expiry = editTextExpiry.getText().toString().trim();

        // Strict input validation
        if (name.isEmpty() || quantityStr.isEmpty() || unit.isEmpty() || expiry.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double quantity = Double.parseDouble(quantityStr);

        // Create new item and save to database
        PantryItem newItem = new PantryItem(name, quantity, unit, expiry);
        AppDatabase.getInstance(this).pantryDao().insert(newItem);

        Toast.makeText(this, "Ingredient Saved", Toast.LENGTH_SHORT).show();
        finish(); // Closes this screen and returns to the Pantry list
    }
}