package com.example.hppc.sms_app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by hppc on 04-May-16.
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Message> msgList;

    public SearchAdapter(ArrayList<Message> ob) {
        this.msgList = ob;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh2;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.search_holder, parent, false);
        vh2 = new SearchHolder(v);

        return vh2;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        SearchHolder sh = (SearchHolder) holder;

        Message msg = (Message) msgList.get(position);
        sh.getSender().setText(msg.getSender());
        sh.getBody().setText(msg.getMsg());
        sh.getDate().setText(msg.getDate());
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
