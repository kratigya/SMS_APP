package com.example.hppc.app_buyhatke;

import java.util.ArrayList;

/**
 * Created by hppc on 02-May-16.
 */
public class MessageThread {

    private String name;
    private ArrayList<Message> ms;

    public ArrayList<Message> getMs() {
        return ms;
    }

    public String getName() {
        return name;
    }

    public void setMs(ArrayList<Message> ms) {
        this.ms = ms;
    }

    public void setName(String name) {
        this.name = name;
    }
}
