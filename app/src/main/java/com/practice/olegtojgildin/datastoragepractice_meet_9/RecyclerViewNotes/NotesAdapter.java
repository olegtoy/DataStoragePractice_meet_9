package com.practice.olegtojgildin.datastoragepractice_meet_9.RecyclerViewNotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.practice.olegtojgildin.datastoragepractice_meet_9.DataNotes.DBManager;
import com.practice.olegtojgildin.datastoragepractice_meet_9.DataNotes.DbHelper;
import com.practice.olegtojgildin.datastoragepractice_meet_9.DiffCall;
import com.practice.olegtojgildin.datastoragepractice_meet_9.Note;
import com.practice.olegtojgildin.datastoragepractice_meet_9.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olegtojgildin on 14/01/2019.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> implements
        MyItemTouchHelper.ItemTouchHelperAdapter {

    private OnNoteListener mOnNoteListener;
    private Context mContext;
    private List<Note> mNote = new ArrayList<>();

    public NotesAdapter(List<Note> list, OnNoteListener onNoteListener, Context context) {
        mNote = list;
        this.mOnNoteListener = onNoteListener;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note, viewGroup, false);
        return new MyViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        myViewHolder.title.setText(mNote.get(position).getTitle());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            Bundle o = (Bundle) payloads.get(0);
            for (String key : o.keySet()) {
                if (key.equals(DbHelper.TITLE))
                 holder.title.setText(mNote.get(position).getTitle());
            }
        }
    }
    public void onNewNote(List<Note> newNotes){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCall(mNote, newNotes));
        diffResult.dispatchUpdatesTo(NotesAdapter.this);
        mNote.clear();
        mNote.addAll(newNotes);
        notifyDataSetChanged();
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
