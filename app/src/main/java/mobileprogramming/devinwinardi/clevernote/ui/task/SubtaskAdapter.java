package mobileprogramming.devinwinardi.clevernote.ui.task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.ui.cs.mobileprogramming.devinwinardi.clevernote.R;

public class SubtaskAdapter extends ListAdapter<Subtask, SubtaskAdapter.SubtaskHolder> {
    private SubtaskAdapter.OnItemClickListener listener;

    public SubtaskAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Subtask> DIFF_CALLBACK = new DiffUtil.ItemCallback<Subtask>() {
        @Override
        public boolean areItemsTheSame(Subtask oldItem, Subtask newItem) {
            return oldItem.getSubtaskId() == newItem.getSubtaskId();
        }
        @Override
        public boolean areContentsTheSame(Subtask oldItem, Subtask newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    };

    @NonNull
    @Override
    public SubtaskAdapter.SubtaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new SubtaskAdapter.SubtaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubtaskAdapter.SubtaskHolder holder, int position) {
        Subtask currentSubtask = getItem(position);
        holder.textViewTitle.setText(currentSubtask.getTitle());
    }

    public Subtask getSubtaskAt(int position) {
        return getItem(position);
    }

    class SubtaskHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;

        public SubtaskHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Subtask task);
    }

    public void setOnItemClickListener(SubtaskAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
