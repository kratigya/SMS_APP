package com.example.hppc.app_buyhatke;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {

    private RecyclerView mSearchRecyclerView;
    private LinearLayoutManager mSearchLayoutManager;
    private SearchAdapter mSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSearchRecyclerView = (RecyclerView) findViewById(R.id.recSearchView);
        mSearchRecyclerView.setHasFixedSize(true);
        mSearchLayoutManager = new LinearLayoutManager(this);
        mSearchRecyclerView.setLayoutManager(mSearchLayoutManager);

        Intent intent = getIntent();
        ArrayList<Message> msgList = intent.getParcelableArrayListExtra("Message");

        mSearchAdapter = new SearchAdapter(msgList);
        mSearchRecyclerView.setAdapter(mSearchAdapter);
    }

}
