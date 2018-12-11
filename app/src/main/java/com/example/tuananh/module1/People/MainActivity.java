package com.example.tuananh.module1.People;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tuananh.module1.AddEditDetail.Main2Activity;
import com.example.tuananh.module1.R;

public class MainActivity extends AppCompatActivity implements IMainActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onAddListener() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
