package com.practice.olegtojgildin.datastoragepractice_meet_9;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by olegtojgildin on 18/01/2019.
 */

public class MyItemTouchHelper extends ItemTouchHelper.Callback {
    private ItemTouchHelperAdapter mHelperAdapter;

    public MyItemTouchHelper(ItemTouchHelperAdapter mHelperAdapter) {
        this.mHelperAdapter = mHelperAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mHelperAdapter.onViewMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mHelperAdapter.onViewSwiped(viewHolder.getAdapterPosition());
    }


    public interface ItemTouchHelperAdapter {
        void onViewMoved(int oldPosition, int newPosition);

        void onViewSwiped(int position);
    }
}
