package com.example.todolist;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView RecyclerViewNotes;
    public static final ArrayList<Note> notes = new ArrayList<>();


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
        if (notes.isEmpty()) {
            notes.add(new Note("Backetball", "2,", "3", 2));
            notes.add(new Note("1", "2,", "3", 2));
            notes.add(new Note("1", "2,", "3", 3));
            notes.add(new Note("1", "2,", "3", 2));
            notes.add(new Note("1", "2,", "3", 1));
            notes.add(new Note("1", "2,", "3", 2));
        }
        RecyclerViewNotes = findViewById(R.id.RecyclerViewNotes);
        NotesAdapter adapter = new NotesAdapter(notes);
        RecyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewNotes.setAdapter(adapter);
        adapter.setOnNoteClickListener(new NotesAdapter.onNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(getApplicationContext(), "Номер поз: " + position, LENGTH_SHORT).show();
            }
        });
        
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNewActivity.class);
            startActivity(intent);
    }
}