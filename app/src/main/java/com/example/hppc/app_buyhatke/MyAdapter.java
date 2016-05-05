package com.example.hppc.app_buyhatke;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by hppc on 30-Apr-16.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<MessageThread> smsList;
    Context context;
    public MyAdapter(ArrayList<MessageThread> obj, Context context) {
        this.smsList = obj;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.my_viewholder, parent, false);
        vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder vh = (MyViewHolder) holder;

        MessageThread mt = (MessageThread) smsList.get(position);
        ArrayList<Message> msglt = mt.getMs();
        int c = msglt.size();
        Message msg = msglt.get(0);
        vh.getBody().setText(msg.getMsg());
        vh.getDate().setText(msg.getDate());
        if(c!=1) {
            vh.getSender().setText(msg.getSender() + "   (" + c + ")");
        }else {
            vh.getSender().setText(msg.getSender());
        }
        vh.getBody().setOnClickListener(clickListener);
        vh.getSender().setOnClickListener(clickListener);
        vh.getBody().setTag(vh);
        vh.getSender().setTag(vh);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            MyViewHolder mvh = (MyViewHolder) v.getTag();
            int position = mvh.getAdapterPosition();

            Intent intent = new Intent(context,Display.class);
            intent.putParcelableArrayListExtra("Message", (ArrayList<? extends Parcelable>) smsList.get(position).getMs());
            context.startActivity(intent);
        }
    };

    @Override
    public int getItemCount() {
        return smsList.size();
    }
}
