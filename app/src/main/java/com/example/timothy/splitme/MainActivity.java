package com.example.timothy.splitme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;

import com.astuetz.PagerSlidingTabStrip;
import com.backendless.Backendless;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.facebook.appevents.AppEventsLogger;


public class MainActivity extends AppCompatActivity
{
    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.viewpager)ViewPager viewPager;
    @Bind(R.id.tabs)PagerSlidingTabStrip tabStrip;
    @Bind(R.id.fab_pay)FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Intent login = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(login);

        setSupportActionBar(toolbar);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));

        tabStrip.setViewPager(viewPager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewSplit.class);
                startActivity(i);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.action_settings) return true;

        return super.onOptionsItemSelected(item);
    }
    /*
    @Override
    protected void onResume()
    {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }
    */
}
