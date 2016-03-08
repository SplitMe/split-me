package com.app.timothy.splitup;

import android.os.Bundle;
import android.provider.Contacts;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.prashantsolanki3.snaplibrary.snap.SnapAdapter;


public class MainActivity extends AppCompatActivity
{
    @Bind(R.id.toolbar)Toolbar toolbar;
    /*
    @Bind(R.id.viewpager)ViewPager viewPager;
    @Bind(R.id.tabs)PagerSlidingTabStrip tabStrip;
    @Bind(R.id.fab_pay)FloatingActionButton fab;
    */
    @Bind(R.id.found_text) TextView foundText;
    @Bind(R.id.groups_recycler) RecyclerView rv;
    SnapAdapter<Group, GroupVH> adapter;
    private int RESULT = 2016;
    private TinyDB tinyDB;

    private ArrayList usersGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tinyDB = new TinyDB(getApplicationContext());

        loginScreen();

        setSupportActionBar(toolbar);
        /*
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));

        tabStrip.setViewPager(viewPager);


        */
        usersGroups = new ArrayList<Group>();

        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new SnapAdapter<Group, GroupVH>(getApplicationContext(), Group.class, R.layout.groupview_layout, GroupVH.class);
        rv.setAdapter(adapter);
        updateList();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void updateList()
    {
        try
        {
            usersGroups = tinyDB.getListObject("groups", Group.class);
            foundText.setVisibility(View.INVISIBLE);
            adapter.clear();
            adapter.addAll(usersGroups);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            foundText.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.new_group)
        {
            Intent group = new Intent(MainActivity.this, NewGroup.class);
            startActivityForResult(group, RESULT);
            return true;
        }
        else if(id == R.id.clear)
        {
            tinyDB.clear();
            Toast.makeText(MainActivity.this, "Cleared data", Toast.LENGTH_SHORT).show();
            updateList();
            return true;
        }


        else if(id == R.id.logout)
        {
            tinyDB.putBoolean("Logged in", false);
            loginScreen();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected  void onActivityResult(int reqCode, int resCode, Intent data)
    {
        if(resCode == RESULT_OK)
        {
            updateList();
        }
        else
            Log.e("Group", "");

    }
    public void loginScreen()
    {
        if(!tinyDB.getBoolean("Logged in") )
        {
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
        }
    }
}
