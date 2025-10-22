package com.example.todolist;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.todolist.NotesDBHelper;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView RecyclerViewNotes;
    public static final ArrayList<Note> notes = new ArrayList<>();
    ArrayList<Note> notesFromDB = new ArrayList<>();
    NotesAdapter adapter = new NotesAdapter(notesFromDB);

    private NotesDBHelper dbHelper;






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
        //Работа с базой данных.
        dbHelper = new NotesDBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();



        if (notes.isEmpty()) {
            notes.add(new Note("Backetball", "2,", "3", 2));
            notes.add(new Note("1", "2,", "3", 2));
            notes.add(new Note("1", "2,", "3", 3));
            notes.add(new Note("1", "2,", "3", 2));
            notes.add(new Note("1", "2,", "3", 1));
            notes.add(new Note("1", "2,", "3", 2));
        }
        for (Note note : notes){
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, note.getTitle());
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, note.getDescription());
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, note.getDayOfWeek());
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, note.getPriority());
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
        }

        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME, null,null,null,null,null,null);



        while(cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            String dayOfWeek = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            int priority = cursor.getInt(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_PRIORITY));
            Note note = new Note(title, description, dayOfWeek, priority);
            notesFromDB.add(note);
        }
        cursor.close();

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

        //Помошник для осущестлвения обработки свайпа в Re.. View
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








    //Методы вне onCreate();



    public void remove(int position){
        notes.remove(position);
        adapter.notifyDataSetChanged();
    }
    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNewActivity.class);
            startActivity(intent);
    }
}