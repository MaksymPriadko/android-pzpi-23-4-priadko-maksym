package nure.priadko.maksym;

import android.app.Application;
import nure.priadko.maksym.Notes;

public class NotesApplication extends Application {
    private Notes notes;

    @Override
    public void onCreate() {
        super.onCreate();
        notes = new Notes(this);
    }

    public Notes GetNotes() {
        return notes;
    }

}



