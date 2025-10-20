package com.example.todolist;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView RecyclerViewNotes;
    private ArrayList<Note> notes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RecyclerViewNotes = findViewById(R.id.RecyclerViewNotes);
        notes.add(new Note("1", "2,", "3", 2));
        notes.add(new Note("1", "2,", "3", 2));
        notes.add(new Note("1", "2,", "3", 2));
        notes.add(new Note("1", "2,", "3", 2));
        notes.add(new Note("1", "2,", "3", 2));
        notes.add(new Note("1", "2,", "3", 2));
        
    }
}