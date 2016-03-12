package com.app.timothy.splitup;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import io.github.prashantsolanki3.snaplibrary.snap.SnapAdapter;

/**
 * Created by Timothy on 3/11/2016.
 */
public class SplitTouchHelper extends ItemTouchHelper.SimpleCallback
{
    private SnapAdapter<Split, SplitsVH> adapter;

    public SplitTouchHelper(SnapAdapter<Split, SplitsVH> sAdapter)
    {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = sAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Split remove = adapter.getItem(viewHolder.getAdapterPosition());
        remove.removeAsync(new AsyncCallback<Long>() {
                               @Override
                               public void handleResponse(Long response) {
                                   Toast.makeText(adapter.getContext(), "Deleted split", Toast.LENGTH_SHORT);
                               }

                               @Override
                               public void handleFault(BackendlessFault fault) {
                               }
                           }
        );
        adapter.remove(viewHolder.getAdapterPosition());

    }
}
