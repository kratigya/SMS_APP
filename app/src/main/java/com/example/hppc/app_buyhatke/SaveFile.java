package com.example.hppc.app_buyhatke;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hppc on 05-May-16.
 */
public class SaveFile {

    Context context;

    public SaveFile(Context context) {
        this.context = context;
    }

    public void saveData(Message msg) {

        String filename = "data.txt";
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(filename, Context.MODE_APPEND | Context.MODE_PRIVATE);
            fileOutputStream.write(msg.toString().getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
