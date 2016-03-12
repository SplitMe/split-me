package com.app.timothy.splitup;

import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.local.UserIdStorageFactory;
import com.backendless.persistence.local.UserTokenStorageFactory;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.prashantsolanki3.snaplibrary.snap.SnapAdapter;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{
    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.groups_recycler) RecyclerView rv;
    @Bind(R.id.group_refresh) SwipeRefreshLayout srl;
    SnapAdapter<Group, GroupVH> adapter;
    private int RESULT = 2016;

    private ArrayList<Group> usersGroups;
    private BackendlessUser user;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String appID = getString(R.string.backendless_app_id);
        String appKey = getString(R.string.backendless_app_key);
        String appVer = getString(R.string.backendless_app_ver);

        Backendless.initApp(getApplicationContext(), appID, appKey, appVer);

        tinyDB = new TinyDB(getApplicationContext());

        loginScreen();

        setSupportActionBar(toolbar);

        usersGroups = new ArrayList<Group>();
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void refreshContent()
    {
        String whereClause = "ownerId = '" + Backendless.UserService.CurrentUser().getUserId() + "'";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);
        Group.findAsync(dataQuery, new BackendlessCallback<BackendlessCollection<Group>>() {
            @Override
            public void handleResponse(BackendlessCollection<Group> response) {
                usersGroups.clear();
                usersGroups.addAll(response.getData());
                adapter = new SnapAdapter<Group, GroupVH>(getApplicationContext(), Group.class, R.layout.groupview_layout, GroupVH.class);
                rv.setAdapter(adapter);
                ItemTouchHelper.Callback callback = new GroupTouchHelper(adapter);
                ItemTouchHelper helper = new ItemTouchHelper(callback);
                helper.attachToRecyclerView(rv);
                adapter.clear();
                adapter.addAll(usersGroups);
                if(srl.isRefreshing())
                    srl.setRefreshing(false);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.new_group)
        {
            Intent group = new Intent(MainActivity.this, NewGroup.class);
            startActivityForResult(group, RESULT);
            return true;
        }

        else if(id == R.id.logout)
        {
            Backendless.UserService.logout(new AsyncCallback<Void>() {
                @Override
                public void handleResponse(Void response) {
                    loginScreen();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(MainActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected  void onActivityResult(int reqCode, int resCode, Intent data)
    {
        if(resCode == RESULT_OK)
        {
            refreshContent();
        }
        else
            Log.e("Group", "");

    }
    public void loginScreen()
    {
        AsyncCallback<Boolean> isValidLoginCallBack = new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response)
            {
                String userId = UserIdStorageFactory.instance().getStorage().get();
                Backendless.UserService.findById(userId, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Backendless.UserService.setCurrentUser(response);
                        refreshContent();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Intent login = new Intent(MainActivity.this, LoginActivity.class);
                        startActivityForResult(login, 1);
                    }
                });
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(login, 1);
            }
        };
        Backendless.UserService.isValidLogin(isValidLoginCallBack);
    }

    @Override
    public void onRefresh()
    {
        refreshContent();
    }

}
