package com.evani.noteassist.model.db;

import android.provider.BaseColumns;

public class NotesContract {
    //Default constructor
    public NotesContract(){}

    /* Inner class that defines the table contents */
    public static final class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "notestable";
        public static final String COLUMN_NOTE_ID = "id";
        public static final String COLUMN_NOTE_TITLE = "note_title";
        public static final String COLUMN_NOTE_CONTENT = "note_content";

    }

}














