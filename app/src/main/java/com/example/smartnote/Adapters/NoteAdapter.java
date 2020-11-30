package com.example.smartnote.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartnote.Database.Notes;
import com.example.smartnote.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.TaskViewHolder>{
    private List<Notes> notesList;
    private Context mContext;
    private OnItemClickListener itemClickListener;

    public NoteAdapter(Context context,OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        mContext = context;
    }


    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.note_list, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Notes notes = notesList.get(position);

        if (notesList.get(position).getNotePriority().equals("Normal")){
            holder.star.setVisibility(View.GONE);
        }

        if (notes.getNoteType().equals("Text")){
            holder.title.setText(notes.getTitle());
            holder.date.setText(notes.getDate());
            holder.imageType.setImageResource(R.drawable.ic_baseline_event_note_24);
        }else if (notes.getNoteType().equals("Image")){
            holder.title.setText(notes.getTitle());
            holder.date.setText(notes.getDate());
            holder.imageType.setImageResource(R.drawable.ic_baseline_image_24);
        }


    }

    @Override
    public int getItemCount() {
        if (notesList == null) {
            return 0;
        }
        return notesList.size();
    }

    public List<Notes> getTasks() {
        return notesList;
    }

    public void setTasks(List<Notes> taskEntries) {
        notesList = taskEntries;
        notifyDataSetChanged();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder  {

        com.google.android.material.textview.MaterialTextView title;
        com.google.android.material.textview.MaterialTextView date;
        androidx.constraintlayout.utils.widget.ImageFilterView imageType;
        androidx.constraintlayout.utils.widget.ImageFilterView star;

        public TaskViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.normalListTitle);
            date = itemView.findViewById(R.id.normalListDate);
            imageType = itemView.findViewById(R.id.normalListImage);
            star = itemView.findViewById(R.id.star);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    if (itemClickListener!=null && index != RecyclerView.NO_POSITION){
                        itemClickListener.onItemClick(notesList.get(index));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int index = getAdapterPosition();
                    if (itemClickListener!=null && index != RecyclerView.NO_POSITION){
                        itemClickListener.onLongPress(notesList.get(index));
                    }
                    return false;
                }
            });
        }

    }

    public interface OnItemClickListener{
        void onItemClick(Notes notes);
        void onLongPress(Notes notes);
    }

//    public void OnItemClickListener(OnItemClickListener listener){
//        itemClickListener = listener;
//    }

    public Notes getNoteAt(int pos){
        return notesList.get(pos);
    }
}
