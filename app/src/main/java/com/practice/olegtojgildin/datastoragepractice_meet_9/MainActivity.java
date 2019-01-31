package com.practice.olegtojgildin.datastoragepractice_meet_9;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.practice.olegtojgildin.datastoragepractice_meet_9.DataNotes.DBManager;
import com.practice.olegtojgildin.datastoragepractice_meet_9.DataNotes.DbHelper;
import com.practice.olegtojgildin.datastoragepractice_meet_9.RecyclerViewNotes.ItemOffsetDecoration;
import com.practice.olegtojgildin.datastoragepractice_meet_9.RecyclerViewNotes.MyItemTouchHelper;
import com.practice.olegtojgildin.datastoragepractice_meet_9.RecyclerViewNotes.NotesAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesAdapter.OnNoteListener {

    public static final int REQUEST_CODE = 1;

    private RecyclerView mRecyclerView;
    private NotesAdapter mAdapter;
    private LinearLayoutManager mManager;
    private Button mCreateNote;
    private Button mSetSetting;
    private List<Note> mNote;

    private MyItemTouchHelper mMyItemTouchHelper;
    private ItemTouchHelper mItemTouchHelper;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initListener();
        initRecycler();
        new AsyckTaskAllNotes().execute();
    }

    public void initview() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mCreateNote = findViewById(R.id.createNote);
        mSetSetting = findViewById(R.id.setSetting);
    }

    public void initListener() {
        mCreateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(CreateNoteActivity.newIntent(MainActivity.this), REQUEST_CODE);
            }
        });
        mSetSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SettingActivity.newIntent(MainActivity.this));
            }
        });
    }

    public void initRecycler() {
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                DBManager dbManager = new DBManager(MainActivity.this);
                mNote.clear();
                mNote = dbManager.getAllNotes();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mAdapter.onNewNote(mNote);
            }
        }.execute();


    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = ReadNoteActivity.newIntent(this);
        intent.putExtra(DbHelper.TITLE, mNote.get(position).getTitle());
        startActivity(intent);
    }

    private class AsyckTaskAllNotes extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            DBManager dbManager = new DBManager(MainActivity.this);
            mNote = dbManager.getAllNotes();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mAdapter = new NotesAdapter(mNote, MainActivity.this, MainActivity.this);
            mRecyclerView.setAdapter(mAdapter);
            mMyItemTouchHelper = new MyItemTouchHelper((MyItemTouchHelper.ItemTouchHelperAdapter) mAdapter);
            mItemTouchHelper = new ItemTouchHelper(mMyItemTouchHelper);
            mItemTouchHelper.attachToRecyclerView(mRecyclerView);
            mRecyclerView.addItemDecoration(new ItemOffsetDecoration(20));
        }
    }
}
