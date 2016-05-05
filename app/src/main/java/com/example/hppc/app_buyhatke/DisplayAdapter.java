package com.example.hppc.app_buyhatke;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by hppc on 03-May-16.
 */
public class DisplayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Message> msgList;

    public DisplayAdapter(ArrayList<Message> ob) {
        this.msgList = ob;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh1;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.display_holder, parent, false);
        vh1 = new DisplayHolder(v);

        return vh1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DisplayHolder dh = (DisplayHolder) holder;

        Message msg = (Message) msgList.get(position);
        dh.getDtView().setText(msg.getDate());
        dh.getMsgView().setText(msg.getMsg());
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
