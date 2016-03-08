package com.app.timothy.splitup;

import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Timothy on 2/14/2016.
 */
public class Person
{
    private String name;
    private ArrayList<Debt> debt;

    public Person(String name)
    {
        this.name = name;
        debt = new ArrayList<Debt>();
    }

    public String getName() {return name;}

    public ArrayList<Debt> getDebt() {return debt;}

    public void addDebt(Debt d) {debt.add(d);}

    public void settleDebt(Person lender)
    {
        for(Debt d:debt)
        {
            if(d.getLender().getName().equals(lender.getName()))
                d.setPaid(true);
        }
    }
}
