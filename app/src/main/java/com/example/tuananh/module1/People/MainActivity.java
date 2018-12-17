package com.example.tuananh.module1.People;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tuananh.module1.AddEditDetail.Main2Activity;
import com.example.tuananh.module1.DatabaseHandle;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IMainActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Model> models = DatabaseHandle.getInstance(getBaseContext()).showPeople();
    }

    @Override
    public void onAddListener() {
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("mode","add");
        startActivity(intent);
    }

    @Override
    public void onEditListener(int id) {
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("mode","view");
        intent.putExtra("id",id);
        startActivity(intent);

    }
}
