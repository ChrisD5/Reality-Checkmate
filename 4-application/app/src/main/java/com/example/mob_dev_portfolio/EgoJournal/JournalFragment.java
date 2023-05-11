package com.example.mob_dev_portfolio.EgoJournal;
import static android.content.ContentValues.TAG;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.mob_dev_portfolio.Database.Note;
import com.example.mob_dev_portfolio.Database.NoteDao;
import com.example.mob_dev_portfolio.Database.NoteDatabase;
import com.example.mob_dev_portfolio.R;


import java.util.List;

public class JournalFragment extends Fragment {

    private NoteDao noteDao;
    private NoteAdapter noteAdapter;
    private LiveData<List<Note>> notesLiveData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_journal, container, false);

        NoteDatabase noteDatabase = Room.databaseBuilder(getContext(), NoteDatabase.class, "my database2")
                .allowMainThreadQueries()
                .build();
        noteDao = noteDatabase.noteDao();

        Button addNoteBtn = view.findViewById(R.id.addnewnotebtn);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNoteActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        noteAdapter = new NoteAdapter(getActivity(), noteDao.NotesList(), noteDao);
        recyclerView.setAdapter(noteAdapter);

        notesLiveData = noteDao.getNotes();
        notesLiveData.observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // Update the RecyclerView when the data changes
                notes.sort((o1, o2) -> (int) (o2.getCreatedTime() - o1.getCreatedTime()));
                Log.d(TAG, "onChanged() called with: notes = " + notes);

                noteAdapter.setNotesList(notes);
                noteAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        noteAdapter.setNotesList(noteDao.NotesList());
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        // Remove the observer when the view is destroyed to avoid memory leaks
        super.onDestroyView();
        notesLiveData.removeObservers(getViewLifecycleOwner());
    }
}
