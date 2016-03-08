package com.app.timothy.splitup;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import io.github.prashantsolanki3.snaplibrary.snap.SnapViewHolder;

/**
 * Created by Timothy on 3/4/2016.
 */
public class GroupVH extends SnapViewHolder<Group>
{

    TextView groupName;
    TextView members;
    private TinyDB tinyDB;
    private Context context;

    public GroupVH(View itemView, Context context)
    {
        super(itemView, context);
        this.context = context;
        initViews();

    }
    private void initViews()
    {
        groupName = (TextView) itemView.findViewById(R.id.gv_group_name);
        members= (TextView) itemView.findViewById(R.id.gv_members);
        tinyDB = new TinyDB(getContext());

    }

    @Override
    public void populateViewHolder(Group data, int pos)
    {
        groupName.setText(data.getGroupName());
        members.setText(data.toString());
    }

    @Override
    public void animateViewHolder(SnapViewHolder viewHolder, int position) {}

    @Override
    public void attachOnClickListeners(SnapViewHolder snapViewHolder,final Group gm, final int i) {
        snapViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinyDB.putObject("Current group", gm);
                Toast.makeText(getContext(), "Selected " + gm.getGroupName(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, GroupActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }
}

