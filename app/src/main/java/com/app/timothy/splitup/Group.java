package com.app.timothy.splitup;

import java.util.ArrayList;

/**
 * Created by Timothy on 2/3/2016.
 */
public class Group
{
    private ArrayList<Person> people;
    private String groupName;
    private Split splits;

    public Group()
    {
        people = new ArrayList<Person>();
    }

    public void add(Person person)
    {
        people.add(person);
    }
    public ArrayList<Person> getMembers(){return people; }
    public String toString()
    {
        String members = "";
        for(Person p:people)
        {
            members += p.getName() + ",";
        }
        return members.substring(0, members.length()-1);
    }
    public Split getSplits()
    {
        return splits;
    }
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
    public void addSplit(Split s)
    {
        splits = s;
    }
}
