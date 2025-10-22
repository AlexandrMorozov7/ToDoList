package com.example.todolist;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView RecyclerViewNotes;
    public static final ArrayList<Note> notes = new ArrayList<>();
    NotesAdapter adapter = new NotesAdapter(notes);


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

        RecyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewNotes.setAdapter(adapter);
        adapter.setOnNoteClickListener(new NotesAdapter.onNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
            Toast.makeText(getApplicationContext(), "Clicked", LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                remove(position);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            remove(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(RecyclerViewNotes);

    }

    public void remove(int position){
        notes.remove(position);
        adapter.notifyDataSetChanged();
    }
    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNewActivity.class);
            startActivity(intent);
    }
}