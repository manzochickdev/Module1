package com.example.tuananh.module1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.tuananh.module1.AddEditDetail.ModelRela;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.Model.Relationship;

import java.util.ArrayList;

public class DatabaseHandle extends SQLiteOpenHelper {
    private static String DB_NAME = "People.db";
    private static DatabaseHandle databaseHandle=null;

    public DatabaseHandle(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public static DatabaseHandle getInstance(Context context){
        if (databaseHandle==null){
            databaseHandle = new DatabaseHandle(context);
        }
        return databaseHandle;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String q1 = "create table people(\n" +
                "id integer primary key,\n" +
                "name text\n" +
                ");";
        String q2="create table relative(\n" +
                "main_id integer ,\n" +
                "id integer ,\n" +
                "name text,\n" +
                "rela integer,\n" +
                "foreign key (main_id) references people(id)\n" +
                ");";

        String q3="create table address(main_id integer,latitude double,longitude double,mAddress text,foreign key (main_id) references people(id));";

        //todo q2 add column mother_id
        db.execSQL(q1);
        db.execSQL(q2);
        db.execSQL(q3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion){
            db.execSQL("drop table if exists people ");
            db.execSQL("drop table if exists relative ");
            onCreate(db);
        }
    }

    public void addPeople(Model model){
        SQLiteDatabase db = getWritableDatabase();
        String q = "insert into people values("+model.getId()+",'"+model.getName()+"');";
        db.execSQL(q);
    }

    public ArrayList<Model> showPeople(){
        ArrayList<Model> models = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String q = "select * from people";
        Cursor c = db.rawQuery(q,null);
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            Model model = new Model(id,name);
            models.add(model);
        }
        return models;
    }
    public void addRelative(Model m1,Model m2,int relation){
        //todo if rela = 4 , add wife
        //rela = 2,0 , add son's mother
        SQLiteDatabase db = getWritableDatabase();
        String q = "insert into relative values ("+m1.getId()+","+ m2.getId()+",'"+m2.getName()+"',"+relation+")";
        String q2 = "insert into relative values ("+m2.getId()+","+ m1.getId()+",'"+m1.getName()+"',"+(2-relation)+")";
        db.execSQL(q);
        db.execSQL(q2);

    }

    public ArrayList<Model> getChild(int id, int rela){
        ArrayList<Model> models = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String q = "select * from relative where (main_id = "+id+" and rela = "+rela+");";
        Cursor c = db.rawQuery(q,null);
        while(c.moveToNext()){
            int mId = c.getInt(c.getColumnIndex("id"));
            String mName = c.getString(c.getColumnIndex("name"));
            models.add(new Model(mId,mName));
        }
        if (models.size()>0){
            return models;
        }
        else return null;
    }

    public ArrayList<ModelRela> getAllRelative(int id){
        ArrayList<ModelRela> modelRelas = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String q = "select * from relative where main_id = "+id+";";
        Cursor c = db.rawQuery(q,null);
        while(c.moveToNext()){
            int mId = c.getInt(c.getColumnIndex("id"));
            String mName = c.getString(c.getColumnIndex("name"));
            int rela = c.getInt(c.getColumnIndex("rela"));
            modelRelas.add(new ModelRela(Relationship.convertIntRelationship(rela),new Model(mId,mName)));
        }
        if (modelRelas.size()>0){
            return modelRelas;
        }
        else return null;
    }
//
//    public void addAddress(int id,ModelAddress address){
//        String q = "insert into address values ("+id+","+address.latLng.latitude+","+address.latLng.longitude+",'"+address.address+"');";
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL(q);
//    }

//    public ModelAddress getAddress(int id){
//        SQLiteDatabase db = getReadableDatabase();
//        String q = "select * from address where main_id = "+id+";";
//        Cursor c = db.rawQuery(q,null);
//        while(c.moveToNext()){
//            double latitude = c.getDouble(c.getColumnIndex("latitude"));
//            double longitude = c.getDouble(c.getColumnIndex("longitude"));
//            String mAddress = c.getString(c.getColumnIndex("mAddress"));
//            ModelAddress address = new ModelAddress(new LatLng(latitude,longitude),mAddress);
//            return address;
//        }
//        return null;
//    }

//    public ArrayList<ModelAddress> getAllAddress(){
//        SQLiteDatabase db = getReadableDatabase();
//        ArrayList<ModelAddress> modelAddresses = new ArrayList<>();
//        String q = "select * from address";
//        Cursor c = db.rawQuery(q,null);
//        while(c.moveToNext()){
//            int id = c.getInt(c.getColumnIndex("main_id"));
//            double latitude = c.getDouble(c.getColumnIndex("latitude"));
//            double longitude = c.getDouble(c.getColumnIndex("longitude"));
//            String mAddress = c.getString(c.getColumnIndex("mAddress"));
//            modelAddresses.add(new ModelAddress(id,new LatLng(latitude,longitude),mAddress));
//        }
//        return modelAddresses;
//    }

    public String getName(int id){
        SQLiteDatabase db = getReadableDatabase();
        String q = "select name from people where id="+id+";";
        Cursor c = db.rawQuery(q,null);
        while (c.moveToNext()){
            String name = c.getString(c.getColumnIndex("name"));
            return name;
        }
        return null;
    }

    public Model getPerson(int id){
        SQLiteDatabase db = getReadableDatabase();
        String q = "select * from people where id="+id+";";
        Cursor c = db.rawQuery(q,null);
        while (c.moveToNext()){
            int mId = c.getInt(c.getColumnIndex("id"));
            String mName = c.getString(c.getColumnIndex("name"));
            return new Model(mId,mName);
        }
        return null;
    }

    public void removeRelative(int id1,int id2){
        SQLiteDatabase db = getWritableDatabase();
        String q = "delete from relative where (main_id = "+id1+" and id="+id2+");";
        String q1 = "delete from relative where (main_id = "+id2+" and id="+id1+");";
        db.execSQL(q);
        db.execSQL(q1);
    }

    public void removePerson(int id){
        SQLiteDatabase db = getWritableDatabase();
        String q = "delete from people where (id="+id+");";
        String q1 = "delete from relative where (main_id = "+id+" or id="+id+");";
        db.execSQL(q);
        db.execSQL(q1);
    }
}
