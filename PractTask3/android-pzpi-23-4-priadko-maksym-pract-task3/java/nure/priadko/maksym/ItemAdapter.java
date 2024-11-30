package nure.priadko.maksym;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<String> _list;

    public ItemAdapter(List<String> list) {
        this._list = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == 1) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_purple, parent, false);
        }
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.id.setText(""+position);
        holder.text.setText(_list.get(position) + position);
    }

    @Override
    public int getItemViewType(int position) {
        return position%2;
    }

    @Override
    public int getItemCount() {
        return _list.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView text;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            text = itemView.findViewById(R.id.text);

        }
    }
}