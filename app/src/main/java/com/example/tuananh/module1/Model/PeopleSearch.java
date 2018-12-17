package com.example.tuananh.module1.Model;

import android.content.Context;

import com.example.tuananh.module1.DatabaseHandle;

import java.util.ArrayList;

public class PeopleSearch {
    ArrayList<Model> models;
    ArrayList<Model> temp;

    public PeopleSearch(Context context) {
        models = DatabaseHandle.getInstance(context).showPeople();
        temp = new ArrayList<>();
    }

    public ArrayList<Model> onSearchListener(String query,int limit){
        temp.clear();
        for (Model m : models){
            if (m.getName().toLowerCase().contains(query.toLowerCase())){
                if (temp.size()<limit){
                    temp.add(m);
                }
            }
        }
        return temp;
    }

    public ArrayList<Model> getModels() {
        return models;
    }
}
