package com.practice.olegtojgildin.datastoragepractice_meet_9.DataNotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.practice.olegtojgildin.datastoragepractice_meet_9.DataNotes.DbHelper;
import com.practice.olegtojgildin.datastoragepractice_meet_9.Note;

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
            db.beginTransaction();
            if(db.inTransaction())
                 addNoteInternal(db, getContentValues(note));
            db.setTransactionSuccessful();
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
            db.beginTransaction();
            if(db.inTransaction())
                db.update(DbHelper.NOTES_TABLE, getContentValues(note), String.format("%1$s= '%2$s'", DbHelper.TITLE, note.getTitle()), null);
            db.setTransactionSuccessful();

        }  catch (SQLiteException e) {
            Log.v("SQLiteExeption", e.getMessage());
        } finally {
            if (db != null) {
                db.endTransaction();
            }
            db.close();
        }
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            if(db.inTransaction())
                db.delete(DbHelper.NOTES_TABLE, String.format("%1$s= '%2$s'", DbHelper.TITLE, note.getTitle()), null);
            db.setTransactionSuccessful();

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
            if (cursor != null && cursor.moveToFirst()&&db.inTransaction()) {
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

            if (cursor != null && cursor.moveToFirst()&&db.inTransaction()) {
                do {
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
