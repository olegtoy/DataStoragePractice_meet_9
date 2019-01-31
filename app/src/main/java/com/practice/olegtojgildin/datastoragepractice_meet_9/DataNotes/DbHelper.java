package com.practice.olegtojgildin.datastoragepractice_meet_9.DataNotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by olegtojgildin on 13/01/2019.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final int VERSION_DB=4;
    public static final String DB_NAME="notes_database.db";
    public static final String NOTES_TABLE="NOTES";
    public static final String TITLE="title";
    public static final String ID="id";
    public static final String TEXT_NOTE="text_note";

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DbHelper(Context context){
        this(context,DB_NAME,null,VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createEmptyTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        deleteTables(db);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }

    public void createEmptyTables(SQLiteDatabase database){
        database.execSQL( "CREATE TABLE NOTES(id integer primary key AUTOINCREMENT, title text not null, text_note text)" );
    }
    private void deleteTables(SQLiteDatabase database){
        database.execSQL( "DROP TABLE IF EXISTS NOTES" );
    }
}
