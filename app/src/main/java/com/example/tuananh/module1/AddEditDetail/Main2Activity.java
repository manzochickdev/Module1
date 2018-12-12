package com.example.tuananh.module1.AddEditDetail;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tuananh.module1.DatabaseHandle;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.R;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements IMain2Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ArrayList<Model> models = DatabaseHandle.getInstance(getBaseContext()).showPeople();

        String mode = getIntent().getStringExtra("mode");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mode.equals("add")){
            AddFragment addFragment = new AddFragment();
            fragmentTransaction.replace(R.id.container,addFragment,"AddFragment");
        }
        else {
            EditFragment editFragment = new EditFragment();
            fragmentTransaction.replace(R.id.container,editFragment,"EditFragment");
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onDataBack(String name, ArrayList<ModelRela> modelRela) {
        if (name!=null && !name.equals("")){
            Model model = new Model(Model.createId(),name);
            DatabaseHandle.getInstance(getBaseContext()).addPeople(model);
        }
        onBackPressed();
    }
}
