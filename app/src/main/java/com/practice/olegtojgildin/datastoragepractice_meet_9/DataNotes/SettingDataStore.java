package com.practice.olegtojgildin.datastoragepractice_meet_9.DataNotes;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by olegtojgildin on 17/01/2019.
 */

public class SettingDataStore {
    public static final String SETT_NOTE = "SETT_NOTE";
    public static final String TEXT_SIZE = "TEXT_SIZE";
    public static final String TEXT_COLOR = "TEXT_COLOR";

    private Context mContext;

    public SettingDataStore(Context context) {
        this.mContext = context;
    }

    public float getTextSize() {
        SharedPreferences pref = mContext.getSharedPreferences(SETT_NOTE, Context.MODE_PRIVATE);
        return pref.getFloat(TEXT_SIZE, 80.f);
    }

    public void setTextSize(Float size) {
        SharedPreferences pref = mContext.getSharedPreferences(SETT_NOTE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(TEXT_SIZE, size);
        editor.commit();
    }

    public String getTextColor() {
        SharedPreferences pref = mContext.getSharedPreferences(SETT_NOTE, Context.MODE_PRIVATE);
        return pref.getString(TEXT_COLOR, "#303F9F");
    }

    public void setTextColor(String color) {
        SharedPreferences pref = mContext.getSharedPreferences(SETT_NOTE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(TEXT_COLOR, color);
        editor.commit();
    }

}
