package com.practice.olegtojgildin.datastoragepractice_meet_9;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olegtojgildin on 13/01/2019.
 */

public class DBManager {


    private DbHelper dbHelper;

    public DBManager(Context context) {
        this.dbHelper = new DbHelper(context);
    }

    public void addNote(Note note) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues contentValues = getContentValues(note);
            db.beginTransaction();
            addNoteInternal(db, contentValues);
            db.setTransactionSuccessful();

        } catch (SQLiteConstraintException e) {
            //   Toast.makeText("Заметка сохранена",Toast.LENGTH_SHORT).show();

        } catch (SQLiteException e) {
            Log.v("SQLiteExeption", e.getMessage());
        } finally {
            if (db != null) {
                db.endTransaction();
            }
            db.close();
        }
    }

    public void updateNote(Note note) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbHelper.TITLE, note.getTitle());
            contentValues.put(DbHelper.TEXT_NOTE, note.getText_note());

            db.beginTransaction();

            db.update(DbHelper.NOTES_TABLE, contentValues, String.format("%1$s= '%2$s'", DbHelper.TITLE, note.getTitle()), null);
            db.setTransactionSuccessful();

        } catch (SQLiteConstraintException e) {
            //   Toast.makeText("Заметка сохранена",Toast.LENGTH_SHORT).show();

        } catch (SQLiteException e) {
            Log.v("SQLiteExeption", e.getMessage());
        } finally {
            if (db != null) {
                db.endTransaction();
            }
            db.close();
        }
    }

    public List<Note> getAllNotes() {
        List<Note> mNoteList = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            db.beginTransaction();
            String selectQuery = "SELECT  * FROM " + DbHelper.NOTES_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mNoteList.add(new Note(cursor.getString(1), cursor.getString(2)));
                } while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.v("SQLiteExeption", e.getMessage());
        } finally {
            if (db != null) {
                db.endTransaction();
            }
            db.close();
        }
        return mNoteList;
    }


    public Note getNote(String title) {
        Note note = new Note();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            String selectQuery = String.format("SELECT * FROM %1$s where %2$s = '%3$s' ", DbHelper.NOTES_TABLE, DbHelper.TITLE, title);
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Log.d("getNote_cursor", cursor.getString(1));
                    note = new Note(cursor.getString(1), cursor.getString(2));
                } while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.v("SQLiteExeption", e.getMessage());
        } finally {
            if (db != null) {
                db.endTransaction();
            }
            db.close();
        }
        return note;
    }

    private ContentValues getContentValues(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.TEXT_NOTE, note.getText_note());
        contentValues.put(DbHelper.TITLE, note.getTitle());
        return contentValues;
    }

    private void addNoteInternal(SQLiteDatabase db, ContentValues contentValues) {
        db.insertOrThrow(DbHelper.NOTES_TABLE, null, contentValues);
    }
}
