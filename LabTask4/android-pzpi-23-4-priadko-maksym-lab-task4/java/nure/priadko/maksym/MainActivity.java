package nure.priadko.maksym;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private nure.priadko.maksym.DBHelper db;
    private NoteAdapter noteAdapter;
    private String currentSubstring = "";
    private RecyclerView notesRecycleView;
    private Spinner importanceFilter;
    private nure.priadko.maksym.Notes notesInstance; // Instance of Notes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Handle insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        notesInstance = ((NotesApplication) getApplication()).GetNotes();
        db = notesInstance.getDBHelper();
        Button newNoteButton = findViewById(R.id.newNoteButton);
        notesRecycleView = findViewById(R.id.notesRecycleView);
        SearchView searchView = findViewById(R.id.noteSearchView);
        importanceFilter = findViewById(R.id.importanceFilter);

        notesRecycleView.setLayoutManager(new LinearLayoutManager(this));
        notesInstance.updateNotes();
        noteAdapter = new NoteAdapter(notesInstance.getNotes(), this, getApplication());
        notesRecycleView.setAdapter(noteAdapter);

        String[] importanceLevels = new String[]{getString(R.string.all), getString(R.string.lowImportance), getString(R.string.mediumImportance), getString(R.string.highImportance)};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, importanceLevels);
        importanceFilter.setAdapter(arrayAdapter);
        importanceFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Filter(currentSubstring, importanceFilter.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Set up the search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentSubstring = newText;
                Filter(newText, importanceFilter.getSelectedItemPosition());
                return true;
            }
        });

        newNoteButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateAndEditNoteActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Filter(currentSubstring, importanceFilter.getSelectedItemPosition());
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (noteAdapter.currentIndex == -1 || noteAdapter.currentIndex >= notesInstance.getNotes().size()) {
            return super.onContextItemSelected(item);
        }

        if (item.getItemId() == R.id.edit) {
            Intent intent = new Intent(MainActivity.this, CreateAndEditNoteActivity.class);
            intent.putExtra("edit", true);
            intent.putExtra("view", false);
            intent.putExtra("index", noteAdapter.currentIndex);
            startActivityForResult(intent, 1);
        } else if (item.getItemId() == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.deleteNote);
            builder.setMessage(R.string.deleteConfirmation);
            builder.setPositiveButton(R.string.yes, (dialog, which) -> {
                notesInstance.DeleteNote(noteAdapter.currentIndex, notesInstance.getNotes().get(noteAdapter.currentIndex).getNumber());
                noteAdapter.notifyDataSetChanged();
            });
            builder.setNegativeButton(R.string.no, (dialog, which) -> {
            });
            builder.create().show();
        }
        return super.onContextItemSelected(item);
    }

    public void Filter(String newText, int importancePosition) {
        noteAdapter = new NoteAdapter(notesInstance.Filter(newText, newText, importancePosition != 0, importancePosition - 1), this, getApplication());
        notesRecycleView.setAdapter(noteAdapter);
    }
}
