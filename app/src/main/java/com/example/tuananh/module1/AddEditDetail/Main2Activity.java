package com.example.tuananh.module1.AddEditDetail;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tuananh.module1.DatabaseHandle;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.Model.Relationship;
import com.example.tuananh.module1.R;

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
    public void onDataBack(String name, ArrayList<ModelRela> modelRela) {
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
        }
        onBackPressed();
    }

    @Override
    public void onBackListener() {
        onBackPressed();
    }
}
