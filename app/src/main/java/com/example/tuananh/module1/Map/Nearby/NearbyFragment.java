package com.example.tuananh.module1.Map.Nearby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class NearbyFragment extends Fragment {
    ArrayList<Model> models;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);
        getListModel();
        Adapter adapter = new Adapter(models,getContext());
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        return view;
    }

    void getListModel(){
        //todo : get list model here
        models = new ArrayList<>();
    }

    public void onAddressBack(String text, LatLng latLng){

    }
}
