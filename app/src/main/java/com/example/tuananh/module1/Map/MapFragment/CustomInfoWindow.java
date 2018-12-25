package com.example.tuananh.module1.Map.MapFragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.LayoutCustomInfoWindowBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
    Context context;
    ArrayList<Model> models;
    View mView;

    public CustomInfoWindow(Context context, ArrayList<Model> models) {
        this.context = context;
        this.models = models;
        mView = LayoutInflater.from(context).inflate(R.layout.layout_custom_info_window,null,false);
    }

    private void onRenderLayout() {
        LayoutCustomInfoWindowBinding layoutCustomInfoWindowBinding = DataBindingUtil.bind(mView);
        //todo setDataHere
        layoutCustomInfoWindowBinding.tvAddress.setText("AAABBB");
        models = new ArrayList<>();
        models.add(new Model());
        models.add(new Model());
        models.add(new Model());

        CustomInfoWindowAdapter customInfoWindowAdapter = new CustomInfoWindowAdapter(models,context);
        layoutCustomInfoWindowBinding.rvPeople.setAdapter(customInfoWindowAdapter);
        layoutCustomInfoWindowBinding.rvPeople.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

    }

    @Override
    public View getInfoWindow(Marker marker) {
        onRenderLayout();
        return mView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        onRenderLayout();
        return mView;
    }
}
