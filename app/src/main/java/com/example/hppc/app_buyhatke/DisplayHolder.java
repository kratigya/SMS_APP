package com.example.hppc.app_buyhatke;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by hppc on 03-May-16.
 */
public class DisplayHolder extends RecyclerView.ViewHolder {

    private TextView dtView, msgView;

    public DisplayHolder(View itemView) {
        super(itemView);

        dtView = (TextView) itemView.findViewById(R.id.dateview);
        msgView = (TextView) itemView.findViewById(R.id.msgview);
    }

    public TextView getDtView() {
        return dtView;
    }

    public TextView getMsgView() {
        return msgView;
    }

    public void setDtView(TextView dtView) {
        this.dtView = dtView;
    }

    public void setMsgView(TextView msgView) {
        this.msgView = msgView;
    }
}
