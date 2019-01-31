package com.practice.olegtojgildin.datastoragepractice_meet_9;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.olegtojgildin.datastoragepractice_meet_9.DataNotes.DBManager;

/**
 * Created by olegtojgildin on 14/01/2019.
 */

public class CreateNoteActivity extends AppCompatActivity {
    private TextView mTitleNote;
    private TextView mTextNote;
    private Button mSaveNote;
    private Button mCloseNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_note);
        initViews();
        initListener();
    }

    public void initListener() {
        mSaveNote.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                if (mTitleNote.getText() != null && mTextNote.getText() != null) {
                    Note newNote = new Note(mTitleNote.getText().toString(), mTextNote.getText().toString());
                    try {
                        new AsyckTaskSave().execute(newNote);
                        Toast.makeText(CreateNoteActivity.this, "Заметка сохранена", Toast.LENGTH_SHORT).show();
                    } catch (android.database.sqlite.SQLiteConstraintException e) {
                        Toast.makeText(CreateNoteActivity.this, "Заметка с таким заголовком уже существует", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        mCloseNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initViews() {
        mTextNote = findViewById(R.id.addText);
        mTitleNote = findViewById(R.id.addTitle);
        mSaveNote = findViewById(R.id.saveNote);
        mCloseNote = findViewById(R.id.close_note);
    }


    public static final Intent newIntent(Context context) {
        return new Intent(context, CreateNoteActivity.class);
    }

    private class AsyckTaskSave extends AsyncTask<Note, Void, Void> {
        @Override
        protected Void doInBackground(Note... notes) {
            DBManager dbManager = new DBManager(CreateNoteActivity.this);
            dbManager.addNote(notes[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setResult(RESULT_OK);
        }
    }
}
