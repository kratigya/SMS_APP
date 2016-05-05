package com.example.hppc.sms_app;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by hppc on 30-Apr-16.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {

    private TextView sender, body, date;

    public MyViewHolder(View itemView) {
        super(itemView);
        sender = (TextView) itemView.findViewById(R.id.sender);
        body = (TextView) itemView.findViewById(R.id.body);
        date = (TextView) itemView.findViewById(R.id.date);
    }

    public TextView getBody() {
        return body;
    }

    public TextView getDate() {
        return date;
    }

    public TextView getSender() {
        return sender;
    }

    public void setSender(TextView sender) {
        this.sender = sender;
    }

    public void setBody(TextView body) {
        this.body = body;
    }

    public void setDate(TextView date) {
        this.date = date;
    }
}
