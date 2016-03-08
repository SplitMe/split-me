package com.app.timothy.splitup;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
        current = (Group)tinyDB.getObject("Current group", Group.class);

        toolbar.setTitle(current.getGroupName());
        setSupportActionBar(toolbar);

        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new SnapAdapter<Split, SplitsVH>(getApplicationContext(), Split.class, R.layout.debt_items, SplitsVH.class);
        rv.setAdapter(adapter);

        updateList();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GroupActivity.this, NewSplit.class);
                startActivityForResult(i, 2016);
            }
        });
    }
    public void updateList()
    {
        try
        {
            adapter.clear();
            adapter.add(current.getSplits());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Split s = (Split)tinyDB.getObject("Ret split", Split.class);
                current.addSplit(s);
                updateList();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
}
