package nure.priadko.maksym;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes;
    private Context context;
    public int currentIndex;
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View noteViewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(noteViewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(holder.getAdapterPosition()); // Use holder.getAdapterPosition() here
        holder.titleTextView.setText(note.getTitle());
        holder.descriptionTextView.setText(note.getDescription());
        byte[] imageBytes = StandartIconCheck(note.getImageData(), context);
        holder.iconImageView.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
        int importance = note.getImportance();
        int imageSRC;
        if (importance == 0) {
            imageSRC = R.drawable.not_important_icon;
        } else if (importance == 1) {
            imageSRC = R.drawable.important_icon;
        } else {
            imageSRC = R.drawable.very_important_icon;
        }
        holder.importanceImageView.setImageResource(imageSRC);
        holder.creationDateTextView.setText(note.getCreationDate());

        // Use holder.getAdapterPosition() inside listeners
        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add(0, R.id.edit, 0, R.string.edit);
            menu.add(1, R.id.delete, 0, R.string.delete);
            currentIndex = holder.getAdapterPosition(); // Use holder.getAdapterPosition() here
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CreateAndEditNoteActivity.class);
            intent.putExtra("view", true);
            intent.putExtra("index", holder.getAdapterPosition()); // Use holder.getAdapterPosition() here
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return notes.size();
    }

    public NoteAdapter(List<Note> notes, MainActivity context, Application application){
        this.notes = notes;
        this.context = context;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{
        public TextView titleTextView;
        public TextView descriptionTextView;
        public ImageView iconImageView;
        public ImageView importanceImageView;
        public TextView creationDateTextView;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            importanceImageView = itemView.findViewById(R.id.importanceImageView);
            creationDateTextView = itemView.findViewById(R.id.creationDateTextView);
        }
    }

    public static byte[] StandartIconCheck(byte[] imageBytes, Context context){
        if (imageBytes == null){
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gallery_icon);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageBytes = stream.toByteArray();
        }
        return imageBytes;
    }

}
