package com.app.timothy.splitup;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Timothy on 3/5/2016.
 */
public class Split
{
    private Person paidBy;
    private double cost;
    private double costEach;
    private String reason;
    private String datePaid;
    private ArrayList<Person> members;
    private String objectId;
    private String ownerId;

    public Split(Person pb, double c, String s, String d, Group g)
    {
        paidBy = pb;
        cost = c;
        reason = s;
        datePaid = d;
        members = g.getMembers();
        costEach = Math.round(cost / members.size());
    }
    public Split() {}

    public List<Person> getMembers() {return members;}
    public String getDatePaid() {return datePaid;}
    public double getCostEach(){return costEach;}
    public double getCost() {return cost;}
    public Person getPaidBy() {return paidBy;}
    public String getReason() {return reason;}

    public String getObjectId() {return objectId;}

    public String getOwnerId() {return ownerId;}

    public void setOwnerId(String ownerId) {this.ownerId = ownerId;}

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setCostEach(double costEach) {this.costEach = costEach;}

    public void setMembers(ArrayList<Person> members) {this.members = members;}

    public void setDatePaid(String datePaid) {this.datePaid = datePaid;}

    public void setPaidBy(Person paidBy) {
        this.paidBy = paidBy;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setObjectId(String objectId) {this.objectId = objectId;}

    public Split save()
    {
        return Backendless.Data.of( Split.class ).save( this );
    }

    public Future<Split> saveAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Split> future = new Future<Split>();
            Backendless.Data.of( Split.class ).save( this, future );

            return future;
        }
    }

    public void saveAsync( AsyncCallback<Split> callback )
    {
        Backendless.Data.of( Split.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Split.class ).remove( this );
    }

    public Future<Long> removeAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Long> future = new Future<Long>();
            Backendless.Data.of( Split.class ).remove( this, future );

            return future;
        }
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Split.class ).remove( this, callback );
    }

    public static Split findById( String id )
    {
        return Backendless.Data.of( Split.class ).findById( id );
    }

    public static Future<Split> findByIdAsync( String id )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Split> future = new Future<Split>();
            Backendless.Data.of( Split.class ).findById( id, future );

            return future;
        }
    }

    public static void findByIdAsync( String id, AsyncCallback<Split> callback )
    {
        Backendless.Data.of( Split.class ).findById( id, callback );
    }

    public static Split findFirst()
    {
        return Backendless.Data.of( Split.class ).findFirst();
    }

    public static Future<Split> findFirstAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Split> future = new Future<Split>();
            Backendless.Data.of( Split.class ).findFirst( future );

            return future;
        }
    }

    public static void findFirstAsync( AsyncCallback<Split> callback )
    {
        Backendless.Data.of( Split.class ).findFirst( callback );
    }

    public static Split findLast()
    {
        return Backendless.Data.of( Split.class ).findLast();
    }

    public static Future<Split> findLastAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Split> future = new Future<Split>();
            Backendless.Data.of( Split.class ).findLast( future );

            return future;
        }
    }

    public static void findLastAsync( AsyncCallback<Split> callback )
    {
        Backendless.Data.of( Split.class ).findLast( callback );
    }

    public static BackendlessCollection<Split> find( BackendlessDataQuery query )
    {
        return Backendless.Data.of( Split.class ).find( query );
    }

    public static Future<BackendlessCollection<Split>> findAsync( BackendlessDataQuery query )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<BackendlessCollection<Split>> future = new Future<BackendlessCollection<Split>>();
            Backendless.Data.of( Split.class ).find( query, future );

            return future;
        }
    }

    public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Split>> callback )
    {
        Backendless.Data.of( Split.class ).find( query, callback );
    }
}
