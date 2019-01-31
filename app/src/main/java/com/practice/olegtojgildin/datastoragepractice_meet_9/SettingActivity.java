package com.practice.olegtojgildin.datastoragepractice_meet_9;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.practice.olegtojgildin.datastoragepractice_meet_9.DataNotes.SettingDataStore;

/**
 * Created by olegtojgildin on 17/01/2019.
 */

public class SettingActivity extends AppCompatActivity {

    private EditText mTextSize;
    private Button mSaveSet;
    private EditText mTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_note);
        initview();
        initListener();
    }

    public void initview() {
        mTextSize = findViewById(R.id.TextSize);
        mSaveSet = findViewById(R.id.SaveSettingBtn);
        mTextColor = findViewById(R.id.TextColor);
    }

    public void initListener() {
        mSaveSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingDataStore settingNote = new SettingDataStore(getApplicationContext());
                settingNote.setTextSize(Float.parseFloat(mTextSize.getText().toString()));
                settingNote.setTextColor(mTextColor.getText().toString());
                Toast.makeText(SettingActivity.this, "Настроки сохранены", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static final Intent newIntent(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        return intent;
    }

}
