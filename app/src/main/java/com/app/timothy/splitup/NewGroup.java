package com.app.timothy.splitup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.ContactsContract;
import android.net.Uri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.FileOutputStream;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewGroup extends AppCompatActivity
{
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.group_name) EditText groupName;
    @Bind(R.id.add_members)TextView addMembers;
    @Bind(R.id.group_layout)LinearLayout groupLayout;

    private final int RESULT = 2016;
    private ArrayList<TextView> membersText;
    private TinyDB tinydb;
    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Group");

        group = new Group();
        membersText = new ArrayList<TextView>();

        tinydb = new TinyDB(getApplicationContext());

        TextView tv = new TextView(getApplicationContext());
        tv.setText("Myself");

        group.add(new Person("Myself"));

        if (Build.VERSION.SDK_INT < 23)
            tv.setTextAppearance(getApplicationContext(), R.style.GroupMemberTextView);
        else
            tv.setTextAppearance(R.style.GroupMemberTextView);
        membersText.add(tv);
        groupLayout.addView(tv);

        addMembers.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, RESULT);
            }
        });
        groupName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                group.setGroupName(s.toString());
            }
        });

    }
    @Override
    protected  void onActivityResult(int reqCode, int resCode, Intent data)
    {
        if(resCode == RESULT_OK)
        {
            if (reqCode == RESULT)
                addMember(data);
        }
        else
            Log.e("Group", "Failed to pick contact");

    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.group_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.save)
        {
            if(groupName.getText().length() > 0) {
                try
                {
                    ArrayList groups = tinydb.getListObject("groups", Group.class);
                    groups.add(group);
                    tinydb.putListObject("groups", groups);
                    Toast.makeText(NewGroup.this, "Group created", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    ArrayList groups = new ArrayList();
                    groups.add(group);
                    tinydb.putListObject("groups", groups);
                    Toast.makeText(NewGroup.this, "Group created", Toast.LENGTH_SHORT).show();
                }
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                this.finish();
            }
            else
            {
                groupName.setError("Can not be empty.");
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void addMember(Intent data)
    {
        Cursor cursor = null;
        try
        {
            String name = null;
            Uri uri = data.getData();
            Bitmap bmap;
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            int photoIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO);
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            name = cursor.getString(nameIndex);

            TextView tv = new TextView(getApplicationContext());
            tv.setText(name);


            if (Build.VERSION.SDK_INT < 23)
                tv.setTextAppearance(getApplicationContext(), R.style.GroupMemberTextView);
            else
                tv.setTextAppearance(R.style.GroupMemberTextView);

            //tv.setCompoundDrawablesWithIntrinsicBounds();
            Boolean isValid = true;
            for(Person p:group.getMembers())
            {
                if(p.getName().equals(name.toString()))
                {
                    isValid = false;
                }
            }
            if(isValid)
            {
                membersText.add(tv);
                groupLayout.addView(tv);
                group.add(new Person(name.toString()));
            }
            else
            {
                ContactExistsDialog ced = new ContactExistsDialog();
                ced.show(getFragmentManager(), "ContactExists");
            }
            cursor.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
