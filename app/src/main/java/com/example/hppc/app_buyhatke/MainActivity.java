package com.example.hppc.app_buyhatke;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefresh;
    private View mlayout;
    private static final int REQUEST_SMS = 0;
    private TextView mErr;
    private ArrayList<MessageThread> msgList = new ArrayList<MessageThread>();
    private HashMap<String, ArrayList<Message>> hm = new HashMap<String, ArrayList<Message>>();
    private String str;
    private SMSReader reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recView);
        mRecyclerView.setHasFixedSize(true);
        mlayout = findViewById(R.id.root_layout);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        reader = new SMSReader();

        permissionCheck();

        hm = reader.getHm();
        msgList = reader.getMsgList();
        mAdapter = new MyAdapter(msgList, this);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reader.ReadInbox(MainActivity.this);
                mAdapter = new MyAdapter(msgList, MainActivity.this);
                mRecyclerView.setAdapter(mAdapter);
                mSwipeRefresh.setRefreshing(false);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cmp = new Intent(MainActivity.this, Compose.class);
                startActivity(cmp);
            }
        });
    }

    public void permissionCheck() {

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                com.example.hppc.app_buyhatke.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestSMSPermission();
        } else {
            reader.ReadInbox(MainActivity.this);
        }
    }

    private void requestSMSPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                com.example.hppc.app_buyhatke.Manifest.permission.READ_SMS)) {

            Snackbar.make(mlayout, "Permision to read SMS is required", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{com.example.hppc.app_buyhatke.Manifest.permission.READ_SMS}, REQUEST_SMS);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{com.example.hppc.app_buyhatke.Manifest.permission.READ_SMS}, REQUEST_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    reader.ReadInbox(MainActivity.this);
                } else {
                    mErr = (TextView) findViewById(R.id.error);
                    mErr.setText("No Message To Display");
                }
            }
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.menu_search) {

            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.search_dialog);
            dialog.setTitle("Search");
            final EditText edt = (EditText) dialog.findViewById(R.id.editText);
            final Button searchButton = (Button) dialog.findViewById(R.id.button);
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int flag = 0;
                    str = edt.getText().toString().toLowerCase();
                    if (hm.containsKey(str)) {

                        Intent searchIntent = new Intent(MainActivity.this, SearchResult.class);
                        searchIntent.putParcelableArrayListExtra("Message", (ArrayList<? extends Parcelable>) hm.get(str));
                        startActivity(searchIntent);
                    }else{
                        Snackbar.make(mlayout, "No Message Found", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        return super.onOptionsItemSelected(item);

    }
}
