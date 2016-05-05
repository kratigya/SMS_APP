package com.example.hppc.app_buyhatke;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by hppc on 30-Apr-16.
 */
public class Message implements Parcelable, Serializable {

    private String id;
    private String msg;
    private String sender;
    private String readState;
    private String time;

    public Message() {
        id = "";
        msg = "";
        sender = "";
        readState = "";
        time = "";
    }

    protected Message(Parcel in) {
        id = in.readString();
        msg = in.readString();
        sender = in.readString();
        readState = in.readString();
        time = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    public String getReadState() {
        return readState;
    }

    public String getSender() {
        return sender;
    }

    public String getDate() {
        return time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setReadState(String readState) {
        this.readState = readState;
    }

    public void setDate(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(msg);
        dest.writeString(sender);
        dest.writeString(readState);
        dest.writeString(time);
    }

    @Override
    public String toString() {
        String str = "ID: " + id + "\n" + "Sender: " + sender + "\n" + "Date: " + time + "\n" + "Message: " + msg + "\n\n";
        return str;
    }
}
