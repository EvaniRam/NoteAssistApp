package com.evani.noteassist.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class NotesDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "NoteAssistDB.db";

    public NotesDBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Note.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + NotesContract.NoteEntry.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertNote(String noteTitle, String noteContent) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        //ContentValues() is used to define the column name and its data to be stored
        ContentValues values = new ContentValues();
        values.put(NotesContract.NoteEntry.COLUMN_NOTE_TITLE,noteTitle);
        values.put(NotesContract.NoteEntry.COLUMN_NOTE_CONTENT,noteContent);

        //insert row
        long id = db.insert(NotesContract.NoteEntry.TABLE_NAME,null,values);

        //close db connection
        db.close();

        //return the id
        return id;

    }

    public Note getNote(long id) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(NotesContract.NoteEntry.TABLE_NAME,
                new String[]{NotesContract.NoteEntry.COLUMN_NOTE_ID, NotesContract.NoteEntry.COLUMN_NOTE_TITLE,
                        NotesContract.NoteEntry.COLUMN_NOTE_CONTENT}
                , NotesContract.NoteEntry.COLUMN_NOTE_ID+"=?",
                new String[] {String.valueOf(id)},null,null,null,null);

        if(null != cursor) {
            cursor.moveToFirst();
        }

        // prepare note object
        Note note = new Note(
                //cursor.getInt(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_NOTE_ID)),
                cursor.getString(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_NOTE_TITLE)),
                cursor.getString(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_NOTE_CONTENT)));

        // close the db connection
        cursor.close();

        return note;

    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + NotesContract.NoteEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(NotesContract.NoteEntry.COLUMN_NOTE_ID, note.getId());
        values.put(NotesContract.NoteEntry.COLUMN_NOTE_TITLE, note.getNote_title());
        values.put(NotesContract.NoteEntry.COLUMN_NOTE_CONTENT, note.getNote_content());

        // updating row
        return db.update(NotesContract.NoteEntry.TABLE_NAME, values, NotesContract.NoteEntry.COLUMN_NOTE_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NotesContract.NoteEntry.TABLE_NAME, NotesContract.NoteEntry.COLUMN_NOTE_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + NotesContract.NoteEntry.TABLE_NAME + " ORDER BY " +
                NotesContract.NoteEntry.COLUMN_NOTE_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                //note.setId(cursor.getInt(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_NOTE_ID)));
                note.setNote_title(cursor.getString(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_NOTE_TITLE)));
                note.setNote_content(cursor.getString(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_NOTE_CONTENT)));

                //notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }
}
