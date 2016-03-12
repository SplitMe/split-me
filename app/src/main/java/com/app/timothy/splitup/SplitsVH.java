package com.app.timothy.splitup;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import io.github.prashantsolanki3.snaplibrary.snap.SnapViewHolder;

/**
 * Created by Timothy on 3/7/2016.
 */
public class SplitsVH extends SnapViewHolder<Split>
{
    TextView description, members, total, costEach;

    public SplitsVH(View itemView, Context context)
    {
        super(itemView, context);
        initViews();
    }
    public void initViews()
    {
        description = (TextView)itemView.findViewById(R.id.why);
        members = (TextView)itemView.findViewById(R.id.debt_members);
        total = (TextView)itemView.findViewById(R.id.total);
        costEach = (TextView)itemView.findViewById(R.id.cost_each);
    }

    @Override
    public void populateViewHolder(Split split, int i)
    {
        description.setText(split.getReason());
        String tot = "$" + split.getCost(), ce = "$" + split.getCostEach() + "/each";
        total.setText(tot);
        costEach.setText(ce);
        String s = "";

        for (Person p : split.getMembers()) {
            s += p.getName() + ",";
        }
        s = s.substring(0, s.length() - 1);
        members.setText(s);
    }


    @Override
    public void animateViewHolder(SnapViewHolder snapViewHolder, int i) {

    }

    @Override
    public void attachOnClickListeners(SnapViewHolder snapViewHolder, Split split, int i) {

    }
}
