package com.example.tuananh.module1.Model;

import android.graphics.Bitmap;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;

public class Model {
    int id;
    String name;

    public Model(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Model() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static int createId(){
        Calendar c = Calendar.getInstance();
        int id = (int) c.getTimeInMillis();
        return id;
    }
}
