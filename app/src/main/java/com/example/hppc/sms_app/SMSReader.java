package com.example.hppc.sms_app;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by hppc on 05-May-16.
 */

public class SMSReader {

    private ArrayList<MessageThread> msgList = new ArrayList<MessageThread>();
    private HashMap<String, ArrayList<Message>> hm = new HashMap<String, ArrayList<Message>>();

    public HashMap<String, ArrayList<Message>> getHm() {
        return hm;
    }

    public ArrayList<MessageThread> getMsgList() {
        return msgList;
    }

    public void ReadInbox(Context context) {

        ContentResolver cr = context.getContentResolver();
        SaveFile sf = new SaveFile(context);

        Cursor inboxCursor = cr.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int count = inboxCursor.getCount();
        String nm, id, body;
        String[] arr;
        MessageThread ob;

        if (inboxCursor != null && inboxCursor.moveToFirst()) {
            for (int i = 0; i < count; i++) {

                int flag = 0, flag1 = 0;
                Message msgs = new Message();
                Message tempMsg, tempMsg1;
                ArrayList<Message> temp = new ArrayList<Message>();
                ArrayList<Message> temp1;

                try {
                    msgs.setId(inboxCursor.getString(inboxCursor.getColumnIndexOrThrow("_id")));
                    nm = inboxCursor.getString(inboxCursor.getColumnIndexOrThrow("address"));
                    msgs.setSender(nm);
                    body = inboxCursor.getString(inboxCursor.getColumnIndexOrThrow("body"));
                    msgs.setMsg(body);
                    msgs.setReadState(inboxCursor.getString(inboxCursor.getColumnIndexOrThrow("read")));
                    Long milliTime = inboxCursor.getLong(inboxCursor.getColumnIndexOrThrow("date"));
                    String date = DateFormat.format("MMMM dd, yyyy", new Date(milliTime)).toString();
                    msgs.setDate(date);

                    sf.saveData(msgs);

                    arr = body.split(" ");

                    for (i = 0; i < arr.length; i++) {
                        temp1 = new ArrayList<Message>();
                        if (hm.containsKey(arr[i].toLowerCase())) {
                            temp1 = hm.get(arr[i].toLowerCase());
                            Iterator itr = temp1.iterator();
                            while (itr.hasNext()) {
                                tempMsg1 = (Message) itr.next();
                                if (tempMsg1.getId().equals(inboxCursor.getString(inboxCursor.getColumnIndexOrThrow("_id")))) {
                                    flag1 = 1;
                                }
                            }
                            if (flag1 != 1) {
                                temp1.add(msgs);
                            }
                        } else {
                            temp1.add(msgs);
                            hm.put(arr[i].toLowerCase(), temp1);
                        }
                    }

                    temp1 = new ArrayList<Message>();

                    if (hm.containsKey(nm.toLowerCase())) {
                        temp1 = hm.get(nm.toLowerCase());
                        Iterator itr = temp1.iterator();
                        while (itr.hasNext()) {
                            tempMsg1 = (Message) itr.next();
                            if (tempMsg1.getId().equals(inboxCursor.getString(inboxCursor.getColumnIndexOrThrow("_id")))) {
                                flag1 = 1;
                                break;
                            }
                        }
                        if (flag1 != 1) {
                            temp1.add(msgs);
                        }
                    } else {
                        temp1.add(msgs);
                        hm.put(nm.toLowerCase(), temp1);
                    }

                    Iterator itout = msgList.iterator();
                    outer:
                    while (itout.hasNext()) {
                        ob = (MessageThread) itout.next();
                        if (ob.getName().equals(nm)) {
                            temp = ob.getMs();
                            Iterator itin = temp.iterator();
                            while (itin.hasNext()) {
                                tempMsg = (Message) itin.next();
                                if (tempMsg.getId().equals(inboxCursor.getString(inboxCursor.getColumnIndexOrThrow("_id")))) {
                                    flag = 1;
                                    break;
                                }
                            }
                            if (flag != 1) {
                                temp.add(msgs);
                                flag = 1;
                                break outer;
                            }
                        }
                    }

                    if (flag != 1) {
                        ob = new MessageThread();
                        temp.add(msgs);
                        ob.setName(nm);
                        ob.setMs(temp);
                        msgList.add(ob);
                    }
                } catch (Exception e) {
                }
                inboxCursor.moveToNext();
            }
        }
        inboxCursor.close();
    }
}
