package com.example.timothy.splitme;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


import butterknife.Bind;
import butterknife.ButterKnife;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewSplit extends AppCompatActivity
{
    @Bind(R.id.toolbar) Toolbar toolbar;
    //@Bind(R.id.calendar_view)MaterialCalendarView calendarView;
    @Bind(R.id.date_split)EditText date;
    protected SimpleDateFormat dFormat;
    protected String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_split);
        ButterKnife.bind(this);

        String format = "MMMM dd,yyyy";
        Calendar calendar = Calendar.getInstance();

        dFormat = new SimpleDateFormat(format, Locale.getDefault());
        currentDate = dFormat.format(calendar.getTime());

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Split");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        date.setText(currentDate);

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pay_menu, menu);
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
        if (id == R.id.confirm) return true;

        return super.onOptionsItemSelected(item);
    }
}
