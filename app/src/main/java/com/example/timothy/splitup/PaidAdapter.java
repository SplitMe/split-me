package com.example.timothy.splitup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.blackcat.currencyedittext.CurrencyEditText;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Timothy on 2/10/2016.
 */
public class PaidAdapter //extends RecyclerView.Adapter<PaidAdapter.ViewHolder>
{
    private Context mContext;
    private List<Person> mDataSet;

    public PaidAdapter(Context context, List<Person> data)
    {
        mContext = context;
        mDataSet = data;
    }
    //@Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int vType)
    {
        View v = LayoutInflater.from(mContext).inflate(R.layout.pay_list_items, parent, false);
        return new ViewHolder(v);
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.group_member) EditText member;
        @Bind(R.id.share) CurrencyEditText share;

        public ViewHolder(View v)
        {
            super(v);
        }
    }
    //@Override
    public int getItemCount()
    {
        return mDataSet.size();
    }

}
