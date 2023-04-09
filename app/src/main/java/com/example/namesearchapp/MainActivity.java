package com.example.namesearchapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput;
    private Button saveButton, updateButton, deleteButton, searchButton;
    private TextView searchResult;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.name_input);
        saveButton = findViewById(R.id.save_button);
        updateButton = findViewById(R.id.update_button);
        deleteButton = findViewById(R.id.delete_button);
        searchButton = findViewById(R.id.search_button);
        searchResult = findViewById(R.id.search_result);

        // Create or open the database
        database = openOrCreateDatabase("names", MODE_PRIVATE, null);

        // Create the table if it doesn't exist
        database.execSQL("CREATE TABLE IF NOT EXISTS names(name TEXT PRIMARY KEY);");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                if (!name.isEmpty()) {
                    // Insert the name into the database
                    database.execSQL("INSERT INTO names VALUES('" + name + "');");
                    Toast.makeText(MainActivity.this, "Name saved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a name.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                if (!name.isEmpty()) {
                    // Update the name in the database
                    database.execSQL("UPDATE names SET name='" + name + "' WHERE name='" + name + "';");
                    Toast.makeText(MainActivity.this, "Name updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a name.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                if (!name.isEmpty()) {
                    // Delete the name from the database
                    database.execSQL("DELETE FROM names WHERE name='" + name + "';");
                    Toast.makeText(MainActivity.this, "Name deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a name.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                if (!name.isEmpty()) {
                    // Search for the name in the database
                    Cursor cursor = database.rawQuery("SELECT * FROM names WHERE name='" + name + "';", null);
                    if (cursor.moveToFirst()) {
                        searchResult.setText("Name found!");
                    } else {
                        searchResult.setText("Name not found.");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a name.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setContentView(int activity_main) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database when the activity is destroyed
        database.close();
    }
}
