package com.app.timothy.splitup;

/**
 * Created by Timothy on 3/6/2016.
 */
public class Debt
{
    private Person lender;
    private double amount;
    private boolean paid;
    private String objectId;

    public Debt(Person l, double cost)
    {
        lender = l;
        amount = cost;
        paid = false;
    }

    public  Debt(){}

    public double getAmount() {return amount;}

    public Person getLender() {return lender;}

    public boolean getPaid() {return paid;}

    public void setAmount(double amount) {this.amount = amount;}

    public void setLender(Person lender) {this.lender = lender;}

    public void setPaid(boolean paid) {this.paid = paid;}

    public String getObjectId() {return objectId;}

    public void setObjectId(String objectId) {this.objectId = objectId;}
}
