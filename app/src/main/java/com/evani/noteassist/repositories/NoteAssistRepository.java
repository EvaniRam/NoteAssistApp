package com.evani.noteassist.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;


import com.evani.noteassist.room.NoteAssistRoomDatabase;
import com.evani.noteassist.room.dao.NoteDao;
import com.evani.noteassist.room.entities.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton Pattern
 */
public class NoteAssistRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> dataSet;

    private static NoteAssistRepository instance;

    public NoteAssistRepository(Application application) {
        NoteAssistRoomDatabase db = NoteAssistRoomDatabase.getDataBase(application);
        noteDao = db.noteDao();
        dataSet = noteDao.getAllNotes();
    }

    /*public static NoteAssistRepository getInstance() {
        if(instance == null) {
            instance = new NoteAssistRepository(application);
        }
        return instance;
    }*/

    public LiveData<List<Note>> getAllNotes() {
        return dataSet;
    }

    //NOTE : Call this on a Non-UI thread or else app will crash
    public void insert (Note word) {
        new insertAsyncTask(noteDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Note,Void,Void> {

        private NoteDao myAsyncTaskDao;

        insertAsyncTask(NoteDao dao) {
            myAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... notes) {
            myAsyncTaskDao.insert(notes[0]);
            return null;
        }
    }
}



