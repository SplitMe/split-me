package com.example.timothy.splitup;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
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
import android.widget.AbsoluteLayout;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


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


        TextView tv = new TextView(getApplicationContext());
        tv.setText("Myself");

        group.add(tv.toString());

        if (Build.VERSION.SDK_INT < 23)
            tv.setTextAppearance(getApplicationContext(), R.style.GroupMemberTextView);
        else
            tv.setTextAppearance(R.style.GroupMemberTextView);
        membersText.add(tv);
        groupLayout.addView(tv);
        group.add(tv.toString());

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save)
        {
            if(groupName.getText().length() > 0)
            {
                String filename = "groups.txt";
                String gName = groupName.getText().toString() + "\n";
                FileOutputStream outputStream;

                try {
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(gName.getBytes());
                    for(String s: group.getMembers())
                    {
                        outputStream.write((s+"\n").getBytes());
                    }
                    outputStream.write("\n".getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            membersText.add(tv);
            groupLayout.addView(tv);
            group.add(name);
            cursor.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
