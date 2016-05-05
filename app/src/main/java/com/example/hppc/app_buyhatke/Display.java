package com.example.hppc.app_buyhatke;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class Display extends AppCompatActivity {

    private RecyclerView mDisplayRecycleView;
    private RecyclerView.Adapter mDisplayAdapter;
    private RecyclerView.LayoutManager mDisplayLayoutManager;
    private View mDisplaylayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDisplayRecycleView = (RecyclerView) findViewById(R.id.displayRecView);
        mDisplayRecycleView.setHasFixedSize(true);
        mDisplaylayout = findViewById(R.id.display_layout);
        mDisplayLayoutManager = new LinearLayoutManager(this);
        mDisplayRecycleView.setLayoutManager(mDisplayLayoutManager);

        Intent in = getIntent();
        ArrayList<Message> msg = in.getParcelableArrayListExtra("Message");

        mDisplayAdapter = new DisplayAdapter(msg);
        mDisplayRecycleView.setAdapter(mDisplayAdapter);

    }

}
