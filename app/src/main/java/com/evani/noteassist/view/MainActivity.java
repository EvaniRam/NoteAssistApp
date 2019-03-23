package com.evani.noteassist.view;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.evani.noteassist.R;
import com.evani.noteassist.room.entities.Note;
import com.evani.noteassist.view.adapters.NoteCustomAdapter;
import com.evani.noteassist.viewmodels.NotesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<Note> notes = new ArrayList<>();
    private NotesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        invalidateOptionsMenu();

        //Fab Animation
        final Animation fabAnimation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fab_anim);

        //Floating Action Button click
        final FloatingActionButton addNoteBtn  = (FloatingActionButton) findViewById(R.id.fab);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNoteBtn.startAnimation(fabAnimation);
            }
        });

        //instanciating viewmodel
        viewModel = ViewModelProviders.of(this).get(NotesViewModel.class);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        //hide done button in Main Activity
        MenuItem item = menu.findItem(R.id.save_note);

        boolean itemVisible = item.isVisible();
        if(itemVisible) {
            item.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.add_note) {
            //startActivity(new Intent(MainActivity.this,AddNoteActivity.class));
            initNotesList();
        }

        return true;
    }


    protected void initNotesList() {
        Intent i = new Intent(this,AddNoteActivity.class);
        startActivity(i);
        initRecyclerView();

    }

    private void initRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.note_recycle_view);
        final NoteCustomAdapter adapter = new NoteCustomAdapter(this, notes);

        viewModel.getAllNotes().observe(this, new Observer<List<com.evani.noteassist.room.entities.Note>>() {
            @Override
            public void onChanged(@Nullable final List<Note> notes) {
            adapter.setNotes(notes);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(adapter.getItemCount() > 0 ){
            hideNoItemsTextView();
        }

    }

    private void hideNoItemsTextView() {

    }
}




