package com.app.timothy.splitup;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import android.os.Handler;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.prashantsolanki3.snaplibrary.snap.SnapAdapter;
import io.github.prashantsolanki3.snaplibrary.snap.SnapViewHolder;

public class GroupActivity extends AppCompatActivity
{
    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.fab_pay)FloatingActionButton fab;
    @Bind(R.id.group_recycle)RecyclerView rv;

    private TinyDB tinyDB;
    private Group current;
    private SnapAdapter<Split, SplitsVH> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        ButterKnife.bind(this);

        tinyDB = new TinyDB(getApplicationContext());

        setSupportActionBar(toolbar);
        adapter = new SnapAdapter<Split, SplitsVH>(getApplicationContext(), Split.class, R.layout.debt_items, SplitsVH.class);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        updateList();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GroupActivity.this, NewSplit.class);
                startActivityForResult(i, 1);
            }
        });
    }

    public void updateList()
    {
        String objID = tinyDB.getString("Current group");

        Group.findByIdAsync(objID, new AsyncCallback<Group>() {
            @Override
            public void handleResponse(Group response) {
                current = response;
                toolbar.setTitle(current.getGroupName());
                ItemTouchHelper.Callback callback = new SplitTouchHelper(adapter);
                ItemTouchHelper helper = new ItemTouchHelper(callback);
                helper.attachToRecyclerView(rv);
                adapter.clear();
                adapter.addAll(current.getSplits());
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(GroupActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == Activity.RESULT_OK){
            updateList();
        }
        if (resultCode == Activity.RESULT_CANCELED) {
        }
    }

}
