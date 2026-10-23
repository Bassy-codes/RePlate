package com.example.spm_replate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryHolder> {
    private List<PantryItem> pantryItems = new ArrayList<>();

    @NonNull

    @Override
    public PantryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);
        return new PantryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryHolder holder, int position) {
        PantryItem currentItem = pantryItems.get(position);
        holder.textViewName.setText(currentItem.name);
        holder.textViewQuantityUnit.setText(currentItem.quantity + " " + currentItem.unit);
        holder.textViewExpiry.setText("Expires: " + currentItem.expiryDate);

        // Long-press to delete item
        holder.itemView.setOnLongClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();

            // 1. Grab the item on the main thread
            PantryItem itemToDelete = pantryItems.get(currentPosition);

            // 2. Delete from database using Executor (bypasses the Thread error)
            java.util.concurrent.Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase.getInstance(v.getContext()).pantryDao().delete(itemToDelete);
            });

            // 3. Remove from the visual list
            pantryItems.remove(currentPosition);
            notifyItemRemoved(currentPosition);
            return true;
        });
    }


    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public void setPantryItems(List<PantryItem> pantryItems) {
        this.pantryItems = pantryItems;
        notifyDataSetChanged(); // Refreshes the list when data changes
    }

    class PantryHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewQuantityUnit;
        private TextView textViewExpiry;

        public PantryHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_item_name);
            textViewQuantityUnit = itemView.findViewById(R.id.text_item_quantity_unit);
            textViewExpiry = itemView.findViewById(R.id.text_item_expiry);
        }
    }
}