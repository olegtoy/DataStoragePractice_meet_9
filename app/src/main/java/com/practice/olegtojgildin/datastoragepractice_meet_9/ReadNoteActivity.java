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

/**
 * Created by olegtojgildin on 15/01/2019.
 */

public class ReadNoteActivity extends AppCompatActivity {

    TextView titleNote;
    EditText textNote;
    Button changeNote;


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
        try{
            textNote.setTextSize(settingNote.getTextSize());
            textNote.setTextColor(Color.parseColor(settingNote.getTextColor()));
        }
        catch (RuntimeException ex){
            Toast.makeText(this,"Неверные настройки текста",Toast.LENGTH_SHORT).show();
        }

    }

    public void initListener() {
        changeNote.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {

                if (titleNote.getText() != null && textNote.getText() != null)
                {
                    Note tempNote=new Note(titleNote.getText().toString(), textNote.getText().toString());
                    new AsyncTask<Note,Void,Void>(){
                        @Override
                        protected Void doInBackground(Note... notes) {
                            DBManager dbManager = new DBManager(ReadNoteActivity.this);
                            dbManager.updateNote(notes[0]);

                            return null;
                        }
                    }.execute(tempNote);
                }
            }
        });
    }

    public void initViews() {
        textNote = findViewById(R.id.readText);
        titleNote = findViewById(R.id.readTitle);
        changeNote = findViewById(R.id.changeNote);
    }

    public void initNote() {
        DBManager dbManager = new DBManager(ReadNoteActivity.this);
        Note newNote = dbManager.getNote(getIntent().getStringExtra(DbHelper.TITLE));
        if (newNote.getTitle() != null && newNote.getText_note() != null) {
            titleNote.setText(newNote.getTitle());
            textNote.setText(newNote.getText_note());
        }
    }

    public static final Intent newIntent(Context context) {
        Intent intent = new Intent(context, ReadNoteActivity.class);
        return intent;
    }
}


