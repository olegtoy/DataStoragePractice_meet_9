package com.practice.olegtojgildin.datastoragepractice_meet_9;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.olegtojgildin.datastoragepractice_meet_9.DataNotes.DBManager;
import com.practice.olegtojgildin.datastoragepractice_meet_9.DataNotes.DbHelper;
import com.practice.olegtojgildin.datastoragepractice_meet_9.DataNotes.SettingDataStore;

/**
 * Created by olegtojgildin on 15/01/2019.
 */

public class ReadNoteActivity extends AppCompatActivity {

    private TextView mTitleNote;
    private EditText mTextNote;
    private Button mChangeNote;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_note);
        initViews();
        initListener();
        initNote();
        initSetting();
    }

    private void initSetting() {
        SettingDataStore settingNote = new SettingDataStore(ReadNoteActivity.this);
        try {
            mTextNote.setTextSize(settingNote.getTextSize());
            mTextNote.setTextColor(Color.parseColor(settingNote.getTextColor()));
        } catch (RuntimeException ex) {
            Toast.makeText(this, "Неверные настройки текста", Toast.LENGTH_SHORT).show();
        }
    }

    public void initListener() {
        mChangeNote.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                if (mTitleNote.getText() != null && mTextNote.getText() != null) {
                    Note tempNote = new Note(mTitleNote.getText().toString(), mTextNote.getText().toString());
                    new AsyckTaskUpdate().execute(tempNote);
                }
            }
        });
    }

    public void initViews() {
        mTextNote = findViewById(R.id.readText);
        mTitleNote = findViewById(R.id.readTitle);
        mChangeNote = findViewById(R.id.changeNote);
    }

    public void initNote() {
        DBManager dbManager = new DBManager(ReadNoteActivity.this);
        Note newNote = dbManager.getNote(getIntent().getStringExtra(DbHelper.TITLE));
        if (newNote.getTitle() != null && newNote.getText_note() != null) {
            mTitleNote.setText(newNote.getTitle());
            mTextNote.setText(newNote.getText_note());
        }
    }

    public static final Intent newIntent(Context context) {
        Intent intent = new Intent(context, ReadNoteActivity.class);
        return intent;
    }

    private class AsyckTaskUpdate extends AsyncTask<Note, Void, Void> {
        @Override
        protected java.lang.Void doInBackground(com.practice.olegtojgildin.datastoragepractice_meet_9.Note... notes) {
            DBManager dbManager = new DBManager(ReadNoteActivity.this);
            dbManager.updateNote(notes[0]);
            return null;
        }
    }
}


