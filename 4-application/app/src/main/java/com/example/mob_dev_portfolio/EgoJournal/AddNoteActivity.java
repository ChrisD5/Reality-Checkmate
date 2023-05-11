package com.example.mob_dev_portfolio.EgoJournal;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.mob_dev_portfolio.R;
import com.example.mob_dev_portfolio.Database.Note;
import com.example.mob_dev_portfolio.Database.NoteDatabase;
import com.example.mob_dev_portfolio.databinding.ActivityAddNoteBinding;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddNoteActivity extends AppCompatActivity {


    private ActivityAddNoteBinding binding;
    private ExecutorService executor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        EditText titleInput = findViewById(R.id.titleinput);
        EditText descriptionInput = findViewById(R.id.descriptioninput);
        Button saveBtn = findViewById(R.id.savebtn);

        NoteDatabase db = Room.databaseBuilder(getApplicationContext(),
                NoteDatabase.class, "my database2").fallbackToDestructiveMigration().build();

        this.executor = Executors.newFixedThreadPool(4);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();
                long createdTime = System.currentTimeMillis();

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        db.noteDao().insertAll(new Note(title, description, createdTime));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddNoteActivity.this, R.string.save_note, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                });
            }
        });

    }
}