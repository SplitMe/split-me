package com.example.timothy.splitup;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;


public class NewSplit extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.date_split)EditText date;
    @Bind(R.id.names)MultiAutoCompleteTextView paid;
    @Bind(R.id.cost)CurrencyEditText cost;

    @Bind(R.id.paid_for)RecyclerView paidFor;
    protected PaidAdapter adapter;
    protected LinearLayoutManager lManager;

    protected SimpleDateFormat dFormat;
    protected String currentDate;
    protected final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_split);
        ButterKnife.bind(this);

        String format = "MM/dd/yyyy";

        dFormat = new SimpleDateFormat(format, Locale.getDefault());
        currentDate = dFormat.format(calendar.getTime());

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Split");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        date.setText(currentDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpb = DatePickerDialog.newInstance(NewSplit.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dpb.show(getFragmentManager(), "DatePickerDialog");
            }
        });
        lManager = new LinearLayoutManager(this);


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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
    {
        String test = monthOfYear+1 + "/" + dayOfMonth + "/" + year;
        try
        {
            currentDate = dFormat.format(dFormat.parse(test));
        }
        catch (ParseException ex)
        {
            Toast.makeText(NewSplit.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        date.setText(currentDate.toString());
    }
}
