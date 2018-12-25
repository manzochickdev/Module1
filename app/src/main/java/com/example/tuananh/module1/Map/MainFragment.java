package com.example.tuananh.module1.Map;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.Map.MapFragment.MapFragment;
import com.example.tuananh.module1.Map.MapManipulation.MapManipulationFragment;
import com.example.tuananh.module1.Map.Nearby.NearbyFragment;
import com.example.tuananh.module1.Model.ModelAddress;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.FragmentMainBinding;
import com.google.android.gms.maps.model.LatLng;


public class MainFragment extends Fragment implements IModule2 {
    FragmentMainBinding fragmentMainBinding;
    MapFragment mapFragment;
    MapManipulationFragment mapManipulationFragment;
    String mode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle!=null){
            mode = bundle.getString("mode");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapManipulationFragment = (MapManipulationFragment) getChildFragmentManager().findFragmentById(R.id.mapManipulationFragment);
        if (mode.equals("view")){
            setNearbyFragment();
        }
        return fragmentMainBinding.getRoot();
    }

    private void setNearbyFragment() {
        NearbyFragment nearbyFragment = new NearbyFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.bottom_container,nearbyFragment,"NearbyFragment").commit();
    }

    @Override
    public void onModeHandle(int mode) {
        mapManipulationFragment.onModeHandle(mode);
        mapFragment.handleMode(mode);
    }

    @Override
    public void moveCamera(String address) {
        mapFragment.moveCameraByAddress(address);
        Log.d("OK", "moveCamera: "+address);
    }

    @Override
    public void onAddressBack(String text, LatLng latLng) {
        Log.d("OK", "onAddressBack: "+text);
        mapManipulationFragment.onAddressBack(text,latLng);
        if (mode.equals("view")){
            NearbyFragment nearbyFragment = (NearbyFragment) getChildFragmentManager().findFragmentByTag("NearbyFragment");
            nearbyFragment.onAddressBack(text,latLng);
        }
    }

    public ModelAddress onAddressBack(){
        ModelAddress modelAddress = mapManipulationFragment.getAddress();
        return modelAddress;
    }
}
