package com.practice.olegtojgildin.datastoragepractice_meet_9.RecyclerViewNotes;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
    private int mOffset;

    public ItemOffsetDecoration(int offset) {
        this.mOffset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
            outRect.right = mOffset;
            outRect.left = mOffset;
            outRect.top = mOffset;
            outRect.bottom = mOffset;

    }
}
