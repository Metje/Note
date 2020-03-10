package com.example.architectureexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddCategoryActivity extends AppCompatActivity {
    public static final String EXTRA_CAT_ID=
            "com.example.architectureexample.EXTRA_CAT_ID";
    public static final String EXTRA_CATEGORY=
            "com.example.architectureexample.EXTRA_CATEGORY";

    private EditText editTextCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        editTextCategory = findViewById(R.id.edit_text_category);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Kategorie eingeben");

    }

    private void saveCategory() {
        String category = editTextCategory.getText().toString();

        if (category.trim().isEmpty()) {
            Toast.makeText(this,"Das gibt nix!!!", Toast.LENGTH_SHORT);
            return;
        }

        //Speichern
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CATEGORY, category);

        setResult(RESULT_OK, intent);
        finish();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveCategory();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
