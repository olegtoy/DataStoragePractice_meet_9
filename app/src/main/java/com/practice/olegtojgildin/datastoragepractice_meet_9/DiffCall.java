package com.practice.olegtojgildin.datastoragepractice_meet_9;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.practice.olegtojgildin.datastoragepractice_meet_9.DataNotes.DBManager;
import com.practice.olegtojgildin.datastoragepractice_meet_9.DataNotes.DbHelper;

import java.util.List;

/**
 * Created by olegtojgildin on 31/01/2019.
 */

public class DiffCall extends DiffUtil.Callback {
    private List<Note> mOldList;
    private List<Note> mNewList;

    public DiffCall(List<Note> oldList, List<Note> newList) {
        mOldList = oldList;
        mNewList = newList;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).getTitle() == mNewList.get(newItemPosition).getTitle();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).equals(mNewList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Note newNote = mNewList.get(newItemPosition);
        Note oldNote = mOldList.get(oldItemPosition);
        Bundle diff = new Bundle();
        if (!newNote.getTitle().equals(oldNote.getTitle())) {
            diff.putString(DbHelper.TITLE, newNote.getTitle());
        }

        if (diff.size() == 0) {
            return null;
        }
        return diff;
    }
}