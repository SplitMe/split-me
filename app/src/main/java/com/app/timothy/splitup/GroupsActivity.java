package com.app.timothy.splitup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.prashantsolanki3.snaplibrary.snap.SnapAdapter;

import java.util.ArrayList;


/**
 * Created by Timothy on 2/15/2016.
 */
public class GroupsActivity extends AppCompatActivity
{
    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.found_text) TextView foundText;
    @Bind(R.id.groups_recycler) RecyclerView rv;
    SnapAdapter<Group, GroupVH> adapter;
    private ArrayList usersGroups;
    private TinyDB tinydb;
    private GroupVH test;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        tinydb = new TinyDB(getApplicationContext());
        usersGroups = new ArrayList<Group>();


        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new SnapAdapter<Group, GroupVH>(getApplicationContext(), Group.class, R.layout.groupview_layout, GroupVH.class);
        rv.setAdapter(adapter);

        try
        {
            usersGroups = tinydb.getListObject("groups", Group.class);
            foundText.setVisibility(View.INVISIBLE);
            adapter.addAll(usersGroups);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            foundText.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.group_act_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit)
        {
            return true;
        }
        if(id == R.id.select)
        {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
