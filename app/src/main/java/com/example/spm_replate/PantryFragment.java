package com.example.spm_replate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class PantryFragment extends Fragment {
    private PantryAdapter adapter;
    private AppDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pantry, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_pantry);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PantryAdapter();
        recyclerView.setAdapter(adapter);

        database = AppDatabase.getInstance(getContext());

        FloatingActionButton fab = view.findViewById(R.id.fab_add_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEditPantryActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Fetches updated database items every time you open this tab
        List<PantryItem> items = database.pantryDao().getAllItems();
        adapter.setPantryItems(items);
    }
}