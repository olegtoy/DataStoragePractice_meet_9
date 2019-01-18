package com.practice.olegtojgildin.datastoragepractice_meet_9;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by olegtojgildin on 17/01/2019.
 */

public class SettingActivity extends AppCompatActivity {

    EditText textSize;
    Button saveSet;
    EditText textColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_note);
        initview();
        initListener();
    }

    public void initview() {
        textSize = findViewById(R.id.TextSize);
        saveSet = findViewById(R.id.SaveSettingBtn);
        textColor = findViewById(R.id.TextColor);
    }

    public void initListener() {
        saveSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingDataStore settingNote = new SettingDataStore(getApplicationContext());
                settingNote.setTextSize(Float.parseFloat(textSize.getText().toString()));
                settingNote.setTextColor(textColor.getText().toString());
                Toast.makeText(SettingActivity.this, "Настроки сохранены", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static final Intent newIntent(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        return intent;
    }

}
