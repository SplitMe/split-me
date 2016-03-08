package com.app.timothy.splitup;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Timothy on 3/5/2016.
 */
public class Split
{
    private Person paidBy;
    private double cost;
    private double costEach;
    private String reason;
    private String date;
    private Group group;

    public Split(Person pb, double c, String s, String d, Group g)
    {
        paidBy = pb;
        cost = c;
        reason = s;
        date = d;
        group = g;
        costEach = Math.round(cost/group.getMembers().size());
        for(Person p:group.getMembers())
        {
            p.addDebt(new Debt(pb, costEach));
        }
    }

    public ArrayList<Person> getMembers() {return group.getMembers();}
    public String getDate() {return date;}
    public double getCostEach(){return costEach;}
    public double getCost() {return cost;}
    public Person getPaidBy() {return paidBy;}
    public String getReason() {return reason;}

}
