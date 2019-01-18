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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesAdapter.OnNoteListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mManager;
    Button createNote;
    Button setSetting;
    List<Note> mNote;
    private MyItemTouchHelper myItemTouchHelper;
    private ItemTouchHelper itemTouchHelper;
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initListener();
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);

        DBManager dbManager = new DBManager(MainActivity.this);
        mNote = dbManager.getAllNotes();
        mAdapter = new NotesAdapter(mNote, this, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        myItemTouchHelper = new MyItemTouchHelper((MyItemTouchHelper.ItemTouchHelperAdapter) mAdapter);
        itemTouchHelper = new ItemTouchHelper(myItemTouchHelper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addItemDecoration(new ItemOffsetDecoration(20));

    }


    public void initview() {
        mRecyclerView = findViewById(R.id.recycler_view);
        createNote = findViewById(R.id.createNote);
        setSetting = findViewById(R.id.setSetting);
    }

    public void initListener() {
        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(CreateNoteActivity.newIntent(MainActivity.this), REQUEST_CODE);
            }
        });
        setSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SettingActivity.newIntent(MainActivity.this));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mNote = new DBManager(MainActivity.this).getAllNotes();
        mAdapter = new NotesAdapter(mNote, this, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, ReadNoteActivity.class);
        intent.putExtra(DbHelper.TITLE, mNote.get(position).getTitle());
        startActivity(intent);
    }
}
