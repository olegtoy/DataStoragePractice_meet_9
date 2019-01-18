package com.practice.olegtojgildin.datastoragepractice_meet_9;

import android.annotation.SuppressLint;
import android.app.AppComponentFactory;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by olegtojgildin on 14/01/2019.
 */

public class CreateNoteActivity extends AppCompatActivity {
    TextView titleNote;
    TextView textNote;
    Button saveNote;
    Button closeNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_note);
        initViews();
        initListener();
    }

    public void initListener() {
        saveNote.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                if (titleNote.getText() != null && textNote.getText() != null) {

                    Note newNote = new Note(titleNote.getText().toString(), textNote.getText().toString());
                    try {
                        new AsyncTask<Note, Void, Void>() {
                            @Override
                            protected Void doInBackground(Note... notes) {
                                DBManager dbManager = new DBManager(CreateNoteActivity.this);
                                dbManager.addNote(notes[0]);
                                return null;
                            }
                        }.execute(newNote);
                        setResult(RESULT_OK);
                        Toast.makeText(CreateNoteActivity.this, "Заметка сохранена", Toast.LENGTH_SHORT).show();
                    } catch (android.database.sqlite.SQLiteConstraintException e) {
                        Toast.makeText(CreateNoteActivity.this, "Заметка с таким заголовком уже существует", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        closeNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initViews() {
        textNote = findViewById(R.id.addText);
        titleNote = findViewById(R.id.addTitle);
        saveNote = findViewById(R.id.saveNote);
        closeNote = findViewById(R.id.close_note);
    }


    public static final Intent newIntent(Context context) {
        Intent intent = new Intent(context, CreateNoteActivity.class);
        return intent;
    }
}
