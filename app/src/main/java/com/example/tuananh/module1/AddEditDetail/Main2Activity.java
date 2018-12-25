package com.example.tuananh.module1.AddEditDetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.tuananh.module1.DatabaseHandle;
import com.example.tuananh.module1.Map.Main3Activity;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.Model.ModelAddress;
import com.example.tuananh.module1.Model.Relationship;
import com.example.tuananh.module1.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements IMain2Activity {
    String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mode = getIntent().getStringExtra("mode");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mode.equals("add")){
            AddFragment addFragment = new AddFragment();
            fragmentTransaction.replace(R.id.container,addFragment,"AddFragment");
        }
        else if (mode.equals("addNew")){
            Bundle bundle = new Bundle();
            bundle.putInt("id",getIntent().getIntExtra("id",-1));
            AddFragment addFragment = new AddFragment();
            addFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.container,addFragment,"AddFragment");
        }
        else if (mode.equals("view")) {
            int id = getIntent().getIntExtra("id",-1);
            Bundle bundle = new Bundle();
            bundle.putInt("id",id);
            EditFragment editFragment = new EditFragment();
            editFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.container,editFragment,"EditFragment");
        }
        else{
            //"addExisting"
            int id = getIntent().getIntExtra("id",-1);
            Bundle bundle = new Bundle();
            bundle.putInt("id",id);
            bundle.putBoolean("isEdit",true);
            EditFragment editFragment = new EditFragment();
            editFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.container,editFragment,"EditFragment");
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onDataBack(String name, ArrayList<ModelRela> modelRela, Bitmap bitmap, ModelAddress modelAddress) {
        if (name!=null && !name.equals("")){
            Model model = new Model(Model.createId(),name);
            DatabaseHandle databaseHandle = DatabaseHandle.getInstance(getBaseContext());
            databaseHandle.addPeople(model);
            databaseHandle.addAddress(model.getId(),modelAddress);
            if (modelRela!=null){
                for (ModelRela m : modelRela){
                    if (m.relationship!=null && m.model!=null){
                        databaseHandle.addRelative(model,m.model, Relationship.convertRelationship(m.relationship));
                    }
                }
            }
            if (bitmap!=null){
                saveBitmap(model.getId(),bitmap);
            }
        }
        onBackPressed();
    }

    @Override
    public void saveBitmap(int id,Bitmap bitmap) {
        String name = Integer.toString(id);
        Context context = getBaseContext();
        File file = new File(context.getFilesDir(),name);
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackListener() {
        onBackPressed();
    }

    @Override
    public void onImageBack(Bitmap bitmap, int mode) {
        if (mode==0){
            AddFragment addFragment = (AddFragment) getSupportFragmentManager().findFragmentByTag("AddFragment");
            addFragment.setImage(bitmap);
        }
        else {
            EditFragment editFragment = (EditFragment) getSupportFragmentManager().findFragmentByTag("EditFragment");
            editFragment.setImage(bitmap);
        }
    }

    @Override
    public void reload(int id, Boolean isEdit) {
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        bundle.putBoolean("isEdit",isEdit);
        EditFragment editFragment = new EditFragment();
        editFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,editFragment,"EditFragment").commit();
    }

    @Override
    public void handleDelete(int id) {
        String name = Integer.toString(id);
        DatabaseHandle.getInstance(getBaseContext()).removePerson(id);
        getBaseContext().deleteFile(name);
        onBackPressed();
    }

    @Override
    public void onPickAddress() {

        Intent intent = new Intent(getBaseContext(), Main3Activity.class);
        intent.putExtra("mode","pick");
        startActivityForResult(intent,11335);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            switch (requestCode){
                case 11335 :
                    if (mode.equals("add") || mode.equals("addNew")){
                        AddFragment addFragment = (AddFragment) getSupportFragmentManager().findFragmentByTag("AddFragment");
                        addFragment.setAddress(data.getStringExtra("address"),new LatLng(data.getDoubleExtra("latitude",0),data.getDoubleExtra("longitude",0)));
                    }
                    else {
                        EditFragment editFragment = (EditFragment) getSupportFragmentManager().findFragmentByTag("EditFragment");
                        editFragment.setAddress(data.getStringExtra("address"),new LatLng(data.getDoubleExtra("latitude",0),data.getDoubleExtra("longitude",0)));
                    }
                    break;
            }
        }
    }
}
