package com.app.timothy.splitup;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Timothy on 2/3/2016.
 */
public class Group
{
    private ArrayList<Person> members;
    private String groupName;
    private ArrayList<Split> splits;
    private String objectId;
    private String ownerId;

    public Group()
    {
        members = new ArrayList<Person>();
    }

    public void add(Person person)
    {
        members.add(person);
    }
    public ArrayList<Person> getMembers(){return members; }
    public String toString()
    {
        String members = "";
        if(!this.members.isEmpty())
        {
            for (Person p : this.members)
            {
                members += p.getName() + ",";
            }
            members = members.substring(0, members.length()-1);
        }
        return members;
    }
    public ArrayList<Split> getSplits() {return splits;}
    public void setGroupName(String s)
    {
        groupName = s;
    }
    public void remove(String person)
    {
        members.remove(person);
    }
    public String getGroupName()
    {
        return groupName;
    }
    public void setMembers(ArrayList<Person> p){members = p;}
    public void addSplit(Split s) {splits.add(s);}
    public void setSplits(ArrayList<Split> s){splits = s;}
    public String getObjectId() {return objectId;}
    public void setObjectId( String objectId ) {this.objectId = objectId;}

    public String getOwnerId() {return ownerId;}

    public void setOwnerId(String ownerId) {this.ownerId = ownerId;}

    public Group save()
    {
        return Backendless.Data.of( Group.class ).save( this );
    }

    public Future<Group> saveAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Group> future = new Future<Group>();
            Backendless.Data.of( Group.class ).save( this, future );

            return future;
        }
    }

    public void saveAsync( AsyncCallback<Group> callback )
    {
        Backendless.Data.of( Group.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Group.class ).remove( this );
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
            Backendless.Data.of( Group.class ).remove( this, future );

            return future;
        }
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Group.class ).remove( this, callback );
    }

    public static Group findById( String id )
    {
        return Backendless.Data.of( Group.class ).findById( id );
    }

    public static Future<Group> findByIdAsync( String id )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Group> future = new Future<Group>();
            Backendless.Data.of( Group.class ).findById( id, future );

            return future;
        }
    }

    public static void findByIdAsync( String id, AsyncCallback<Group> callback )
    {
        Backendless.Data.of( Group.class ).findById( id, callback );
    }

    public static Group findFirst()
    {
        return Backendless.Data.of( Group.class ).findFirst();
    }

    public static Future<Group> findFirstAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Group> future = new Future<Group>();
            Backendless.Data.of( Group.class ).findFirst( future );

            return future;
        }
    }

    public static void findFirstAsync( AsyncCallback<Group> callback )
    {
        Backendless.Data.of( Group.class ).findFirst( callback );
    }

    public static Group findLast()
    {
        return Backendless.Data.of( Group.class ).findLast();
    }

    public static Future<Group> findLastAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Group> future = new Future<Group>();
            Backendless.Data.of( Group.class ).findLast( future );

            return future;
        }
    }

    public static void findLastAsync( AsyncCallback<Group> callback )
    {
        Backendless.Data.of( Group.class ).findLast( callback );
    }

    public static BackendlessCollection<Group> find( BackendlessDataQuery query )
    {
        return Backendless.Data.of( Group.class ).find( query );
    }

    public static Future<BackendlessCollection<Group>> findAsync( BackendlessDataQuery query )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<BackendlessCollection<Group>> future = new Future<BackendlessCollection<Group>>();
            Backendless.Data.of( Group.class ).find( query, future );

            return future;
        }
    }

    public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Group>> callback )
    {
        Backendless.Data.of( Group.class ).find( query, callback );
    }
}
