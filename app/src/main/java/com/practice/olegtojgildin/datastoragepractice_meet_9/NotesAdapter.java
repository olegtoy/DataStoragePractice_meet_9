package com.practice.olegtojgildin.datastoragepractice_meet_9;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olegtojgildin on 14/01/2019.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> implements
        MyItemTouchHelper.ItemTouchHelperAdapter {

    private OnNoteListener onNoteListener;
    private List<Note> mNote = new ArrayList<>();
    Context mContext;

    public NotesAdapter(List<Note> list, OnNoteListener onNoteListener, Context context) {
        mNote = list;
        this.onNoteListener = onNoteListener;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note, viewGroup, false);
        return new MyViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        myViewHolder.title.setText(mNote.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mNote.size();
    }

    @Override
    public void onViewMoved(int oldPosition, int newPosition) {

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onViewSwiped(int position) {
        DBManager dbManager = new DBManager(mContext);
        mNote = dbManager.getAllNotes();

        if (position < mNote.size()) {
            Note noteForRemove = mNote.get(position);

            dbManager.deleteNote(noteForRemove);
            mNote.remove(position);
        }

        notifyDataSetChanged();
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mNote.size());

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        LinearLayout parentLayout;
        OnNoteListener onNoteListener;

        public MyViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            title = itemView.findViewById(R.id.titleNote);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}
