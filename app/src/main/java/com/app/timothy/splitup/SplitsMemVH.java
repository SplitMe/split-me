package com.app.timothy.splitup;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;

import io.github.prashantsolanki3.snaplibrary.snap.SnapViewHolder;

/**
 * Created by Timothy on 3/6/2016.
 */
public class SplitsMemVH extends SnapViewHolder<Person>
{
    TextView name;
    CurrencyEditText share;
    CheckBox included;

    public SplitsMemVH(View itemView, Context context)
    {
        super(itemView, context);
        initViews();
    }
    private void initViews()
    {
        name = (TextView) itemView.findViewById(R.id.debtor);
        share = (CurrencyEditText) itemView.findViewById(R.id.share);
        share.setVisibility(View.INVISIBLE);
        included = (CheckBox) itemView.findViewById(R.id.included);
    }

    @Override
    public void populateViewHolder(Person data, int pos)
    {
        name.setText(data.getName());
        for(Debt d:data.getDebt()) {
            share.setText(String.valueOf(d.getAmount()));
        }
    }

    @Override
    public void animateViewHolder(SnapViewHolder viewHolder, int position) {}

    @Override
    public void attachOnClickListeners(SnapViewHolder snapViewHolder,Person p, final int i)
    {
        included.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TinyDB tinyDB = new TinyDB(getContext());
                String n = tinyDB.getString("Paid by");
                if(name.getText().toString().equals(n))
                {
                    included.setChecked(true);
                    Toast.makeText(getContext(), "The person who paid for the item cannot be removed.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    included.setClickable(true);
                }
            }
        });
        /*
        snapViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                included.toggle();
            }
        });
        */
    }
}
