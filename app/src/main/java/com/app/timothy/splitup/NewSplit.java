package com.app.timothy.splitup;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

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

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;


public class NewSplit extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, GroupSelectDialog.GroupSelectDialogListener, PaidByDialog.PaidByDialogListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.date_split)
    EditText date;
    @Bind(R.id.payer)
    EditText paid;
    @Bind(R.id.cost)
    CurrencyEditText cost;
    @Bind(R.id.paid_rv)
    RecyclerView paidFor;
    @Bind(R.id.split_group)
    EditText splitGroup;
    @Bind(R.id.description)
    EditText reason;

    private SnapAdapter<Person, SplitsMemVH> adapter;
    private SimpleDateFormat dFormat;
    private String currentDate;
    private TinyDB tinyDB;
    private final Calendar calendar = Calendar.getInstance();
    private Group selected;
    private Person paidBy;
    private ArrayList<Boolean> checked;
    private int num;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_split);
        ButterKnife.bind(this);

        tinyDB = new TinyDB(getApplicationContext());
        selected = (Group) tinyDB.getObject("Current group", Group.class);
        checked = new ArrayList<Boolean>(selected.getMembers().size());
        for (Boolean b : checked) {
            b = true;
        }
        tinyDB.putListBoolean("Checked", checked);
        tinyDB.putInt("Num checked", checked.size());

        String format = "MM/dd/yyyy";

        dFormat = new SimpleDateFormat(format, Locale.getDefault());
        currentDate = dFormat.format(calendar.getTime());

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Split");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paidFor.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new SnapAdapter<Person, SplitsMemVH>(getApplicationContext(), Person.class, R.layout.pay_list_items, SplitsMemVH.class);
        paidFor.setAdapter(adapter);

        splitGroup.setText(selected.getGroupName());
        adapter.addAll(selected.getMembers());

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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pay_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.confirm) {
            if (checkValues()) {
                tinyDB = new TinyDB(getApplicationContext());
                Double c = cost.getRawValue() / 100.0;
                Split s = new Split(paidBy, c, reason.getText().toString(), date.getText().toString(), selected);
                selected.addSplit(s);
                tinyDB.putObject("Current group", selected);
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
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
            reason.setText("Please enter a reason");
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "NewSplit Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.app.timothy.splitup/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "NewSplit Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.app.timothy.splitup/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
