package com.example.tuananh.module1.People;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tuananh.module1.AddEditDetail.Main2Activity;
import com.example.tuananh.module1.DatabaseHandle;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.R;
import com.example.tuananh.module2.MainFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IMainActivity,BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeopleFragment peopleFragment = new PeopleFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_act_container,peopleFragment,peopleFragment.getTag()).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bnv);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.mi_people:
                PeopleFragment peopleFragment = new PeopleFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_act_container,peopleFragment,peopleFragment.getTag()).commit();
                return true;
            case R.id.mi_location:
                Bundle bundle = new Bundle();
                bundle.putString("mode","view");
                MainFragment mainFragment = new MainFragment();
                mainFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_act_container,mainFragment,mainFragment.getTag()).commit();
                return true;
        }
        return false;
    }
}
