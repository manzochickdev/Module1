package com.example.tuananh.module1.AddEditDetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.tuananh.module1.DatabaseHandle;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.Model.Relationship;
import com.example.tuananh.module1.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements IMain2Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String mode = getIntent().getStringExtra("mode");
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
    public void onDataBack(String name, ArrayList<ModelRela> modelRela,Bitmap bitmap) {
        if (name!=null && !name.equals("")){
            Model model = new Model(Model.createId(),name);
            DatabaseHandle.getInstance(getBaseContext()).addPeople(model);
            if (modelRela!=null){
                for (ModelRela m : modelRela){
                    if (m.relationship!=null && m.model!=null){
                        DatabaseHandle.getInstance(getBaseContext()).addRelative(model,m.model, Relationship.convertRelationship(m.relationship));
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

}
