package com.app.timothy.splitup;

import java.util.ArrayList;

/**
 * Created by Timothy on 2/3/2016.
 */
public class Group
{
    private ArrayList<String> people;
    private String groupName;

    public Group()
    {
        people = new ArrayList<String>();
    }

    public void add(String person)
    {
        people.add(person);
    }
    public ArrayList<String> getMembers(){return people; }
    public void setGroupName(String s)
    {
        groupName = s;
    }
    public void remove(String person)
    {
        people.remove(person);
    }
    public String getGroupName()
    {
        return groupName;
    }
}
