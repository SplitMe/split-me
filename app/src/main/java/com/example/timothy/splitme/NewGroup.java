package com.example.timothy.splitme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewGroup extends AppCompatActivity
{
    @Bind(R.id.group_name) EditText groupName;
    @Bind(R.id.self_member)EditText self;
    @Bind(R.id.add_members)Button addMembers;
    @Bind(R.id.group_layout)LinearLayout groupLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        ButterKnife.bind(this);

        addMembers.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

    }

    public void addMember()
    {

    }
}
