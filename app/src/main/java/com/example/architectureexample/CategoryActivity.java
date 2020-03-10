package com.example.architectureexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    public static final int ADD_CATEGORY_REQUEST = 1;

    private CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        setTitle("Kategorien");

        FloatingActionButton buttonAddCategory = findViewById(R.id.button_add_category);
        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, AddCategoryActivity.class);
                startActivityForResult(intent, ADD_CATEGORY_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_cat_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CategoryAdapter adapter = new CategoryAdapter();
        recyclerView.setAdapter(adapter);

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                adapter.submitList(categories);
            }
        });



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                categoryViewModel.delete(adapter.getAt(viewHolder.getAdapterPosition()));
                Toast.makeText(CategoryActivity.this, "Kategorie gelöscht", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

/*        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(CategoryActivity.this, AddEditNoteActivity.class);

                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());

                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

 */
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CATEGORY_REQUEST && resultCode == RESULT_OK) {
            String categorytext = data.getStringExtra(AddCategoryActivity.EXTRA_CATEGORY);
            Category category = new Category(categorytext);
            categoryViewModel.insert(category);
            Toast.makeText(this, "Kategorie gespeichert", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Kategorie NICHT gespeichert", Toast.LENGTH_SHORT).show();
        }

    }
}
