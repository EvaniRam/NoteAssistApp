package com.evani.noteassist.model.db;

public class Note {
    private int id;
    private String note_title;
    private String note_content;
    public Note() {}

    public Note(String note_title, String note_content) {

        this.note_title = note_title;
        this.note_content = note_content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getNote_content() {
        return note_content;
    }

    public void setNote_content(String note_content) {
        this.note_content = note_content;
    }



    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + NotesContract.NoteEntry.TABLE_NAME + "("
                    + NotesContract.NoteEntry.COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + NotesContract.NoteEntry.COLUMN_NOTE_TITLE + " TEXT,"
                    + NotesContract.NoteEntry.COLUMN_NOTE_TITLE + " TEXT ";
}
