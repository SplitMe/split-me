package com.app.timothy.splitup;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.prashantsolanki3.snaplibrary.snap.SnapAdapter;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.blackcat.currencyedittext.CurrencyEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;


public class NewSplit extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, GroupSelectDialog.GroupSelectDialogListener, PaidByDialog.PaidByDialogListener {
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.date_split) EditText date;
    @Bind(R.id.payer) EditText paid;
    @Bind(R.id.cost) CurrencyEditText cost;
    @Bind(R.id.paid_rv) RecyclerView paidFor;
    @Bind(R.id.split_group) EditText splitGroup;
    @Bind(R.id.description) EditText reason;

    private SnapAdapter<Person, SplitsMemVH> adapter;
    private SimpleDateFormat dFormat;
    private String currentDate;
    private TinyDB tinyDB;
    private final Calendar calendar = Calendar.getInstance();
    private Group selected;
    private Person paidBy;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_split);
        ButterKnife.bind(this);

        tinyDB = new TinyDB(getApplicationContext());
        String objID = tinyDB.getString("Current group");

        paidFor.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new SnapAdapter<Person, SplitsMemVH>(getApplicationContext(), Person.class, R.layout.pay_list_items, SplitsMemVH.class);
        paidFor.setAdapter(adapter);

        Backendless.Persistence.of(Group.class).findById(objID, new AsyncCallback<Group>() {
            @Override
            public void handleResponse(Group response) {
                selected = response;
                splitGroup.setText(selected.getGroupName());
                adapter.addAll(selected.getMembers());
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

        String format = "MM/dd/yyyy";

        dFormat = new SimpleDateFormat(format, Locale.getDefault());
        currentDate = dFormat.format(calendar.getTime());

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Split");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected != null) {
                    PaidByDialog pbd = new PaidByDialog();
                    pbd.members = selected.getMembers();
                    pbd.show(getFragmentManager(), "PaidBySelect");
                } else {
                    NoGroupDialog ngd = new NoGroupDialog();
                    ngd.show(getFragmentManager(), "NoGroup");
                }
            }
        });
        date.setText(currentDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpb = DatePickerDialog.newInstance(NewSplit.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dpb.show(getFragmentManager(), "DatePickerDialog");
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pay_menu, menu);
        return true;
    }

    public void finishUp()
    {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.confirm) {
            if (checkValues()) {

                double c = cost.getRawValue() / 100.0;
                Split s = new Split(paidBy, c, reason.getText().toString(), date.getText().toString(), selected);
                selected.addSplit(s);
                Backendless.Persistence.mapTableToClass("Split", Split.class);
                Backendless.Persistence.of(Group.class).save(selected, new AsyncCallback<Group>() {
                    @Override
                    public void handleResponse(Group response) {
                        tinyDB.putString("Current group", response.getObjectId());
                        finishUp();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(NewSplit.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean checkValues() {
        boolean isValid = true;
        if (cost.getRawValue() < 100) {
            cost.setError("Please enter $1 or more");
            isValid = false;
        }
        if (paid.getText().toString().isEmpty()) {
            paid.setError("Please choose the person who paid");
            isValid = false;
        }
        if (reason.getText().toString().isEmpty()) {
            reason.setError(
                    "Please enter a reason");
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String test = monthOfYear + 1 + "/" + dayOfMonth + "/" + year;
        try {
            currentDate = dFormat.format(dFormat.parse(test));
        } catch (ParseException ex) {
            Toast.makeText(NewSplit.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        date.setText(currentDate.toString());
    }

    @Override
    public void onListSelection(Group g) {
        selected = g;
        splitGroup.setText(selected.getGroupName());
        adapter.clear();
        adapter.addAll(selected.getMembers());
    }

    @Override
    public void onListSelection(Person p) {
        paidBy = p;
        paid.setText(paidBy.getName());
        tinyDB.putString("Paid by", p.getName());
    }

}
