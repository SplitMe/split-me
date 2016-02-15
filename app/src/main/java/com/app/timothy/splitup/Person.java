package com.app.timothy.splitup;

/**
 * Created by Timothy on 2/14/2016.
 */
public class Person
{
    private String name;
    private double debt;

    public Person(String name, double debt)
    {
        this.name = name;
        this.debt = debt;
    }

    public String getName()
    {
        return name;
    }

    public double getDebt()
    {
        return debt;
    }
}
