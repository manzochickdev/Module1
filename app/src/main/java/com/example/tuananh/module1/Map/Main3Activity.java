package com.example.tuananh.module1.Map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tuananh.module1.Model.ModelAddress;
import com.example.tuananh.module1.R;

public class Main3Activity extends AppCompatActivity {
    MainFragment mainFragment;
    String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mode = getIntent().getStringExtra("mode");
        //todo remove mode here
        mode="edit";

        Bundle bundle = new Bundle();
        bundle.putString("mode",mode);
        mainFragment = new MainFragment();
        mainFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,mainFragment,mainFragment.getTag()).commit();

        if (mode.equals("view")){
            Button button = findViewById(R.id.btn_ok);
            button.setVisibility(View.GONE);
        }

        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDataBack();
            }
        });
    }

    public void onDataBack() {
        ModelAddress modelAddress = mainFragment.onAddressBack();
        //todo address + latlng here
        Log.d("OK", "onDataBack: "+modelAddress.address);
        Intent intent = new Intent();
        intent.putExtra("address",modelAddress.address);
        intent.putExtra("latitude",modelAddress.latLng.latitude);
        intent.putExtra("longitude",modelAddress.latLng.longitude);
        setResult(RESULT_OK,intent);
        finish();

    }
}
