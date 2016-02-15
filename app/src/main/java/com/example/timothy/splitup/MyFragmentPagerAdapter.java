package com.example.timothy.splitup;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by Timothy on 2/1/2016.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter
{
    final int PAGE_COUNT = 3;
    private String[] titles = new String[]{"Total","Owed","Debt"};

    public MyFragmentPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public int getCount()
    {
        return PAGE_COUNT;
    }
    @Override
    public Fragment getItem(int position)
    {
        return PageFragment.newInstance(position+1);
    }
    @Override
    public CharSequence getPageTitle(int position)
    {
        return titles[position];
    }
}
