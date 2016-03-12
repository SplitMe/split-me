package com.app.timothy.splitup;

import android.os.Parcelable;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.ArrayList;

/**
 * Created by Timothy on 2/14/2016.
 */
public class Person
{
    private String name;
    //private ArrayList<Debt> debt;
    private String objectId;
    private String ownerId;

    public Person(String name)
    {
        this.name = name;
    }

    public Person() {}

    public String getName() {return name;}

    //public ArrayList<Debt> getDebt() {return debt;}

   // public void setDebt(ArrayList<Debt> debt) {this.debt = debt;}

    public void setName(String name) {
        this.name = name;
    }

   // public void addDebt(Debt d) {debt.add(d);}
/*
    public void settleDebt(Person lender)
    {
        for(Debt d:debt)
        {
            if(d.getLender().getName().equals(lender.getName()))
                d.setPaid(true);
        }
    }
*/
    public String getObjectId() {
        return objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getOwnerId() {return ownerId;}

    public void setOwnerId(String ownerId) {this.ownerId = ownerId;}

    public Person save()
    {
        return Backendless.Data.of( Person.class ).save( this );
    }

    public Future<Person> saveAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Person> future = new Future<Person>();
            Backendless.Data.of( Person.class ).save( this, future );

            return future;
        }
    }

    public void saveAsync( AsyncCallback<Person> callback )
    {
        Backendless.Data.of( Person.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Person.class ).remove( this );
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
            Backendless.Data.of( Person.class ).remove( this, future );

            return future;
        }
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Person.class ).remove( this, callback );
    }

    public static Person findById( String id )
    {
        return Backendless.Data.of( Person.class ).findById( id );
    }

    public static Future<Person> findByIdAsync( String id )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Person> future = new Future<Person>();
            Backendless.Data.of( Person.class ).findById( id, future );

            return future;
        }
    }

    public static void findByIdAsync( String id, AsyncCallback<Person> callback )
    {
        Backendless.Data.of( Person.class ).findById( id, callback );
    }

    public static Person findFirst()
    {
        return Backendless.Data.of( Person.class ).findFirst();
    }

    public static Future<Person> findFirstAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Person> future = new Future<Person>();
            Backendless.Data.of( Person.class ).findFirst( future );

            return future;
        }
    }

    public static void findFirstAsync( AsyncCallback<Person> callback )
    {
        Backendless.Data.of( Person.class ).findFirst( callback );
    }

    public static Person findLast()
    {
        return Backendless.Data.of( Person.class ).findLast();
    }

    public static Future<Person> findLastAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Person> future = new Future<Person>();
            Backendless.Data.of( Person.class ).findLast( future );

            return future;
        }
    }

    public static void findLastAsync( AsyncCallback<Person> callback )
    {
        Backendless.Data.of( Person.class ).findLast( callback );
    }

    public static BackendlessCollection<Person> find( BackendlessDataQuery query )
    {
        return Backendless.Data.of( Person.class ).find( query );
    }

    public static Future<BackendlessCollection<Person>> findAsync( BackendlessDataQuery query )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<BackendlessCollection<Person>> future = new Future<BackendlessCollection<Person>>();
            Backendless.Data.of( Person.class ).find( query, future );

            return future;
        }
    }

    public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Person>> callback )
    {
        Backendless.Data.of( Person.class ).find( query, callback );
    }
}
