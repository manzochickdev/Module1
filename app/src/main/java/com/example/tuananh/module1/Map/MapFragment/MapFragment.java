package com.example.tuananh.module1.Map.MapFragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.Map.IModule2;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.FragmentMapBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    FragmentMapBinding fragmentMapBinding;
    GoogleMap googleMap;
    IModule2 iModule2;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMapBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        iModule2 = (IModule2) getParentFragment();
        context = getContext();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return fragmentMapBinding.getRoot();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //todo load view when map ready
        this.googleMap = googleMap;
        Log.d("OK", "onMapReady: ");
    }

    void moveCamera(LatLng latLng){
        Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.setInfoWindowAdapter(new CustomInfoWindow(context,null));
        marker.showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    public void getCurrentLocation(){
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
                String data="";
                moveCamera(currentLocation);
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    if (addresses.size()!=0){
                        data = addresses.get(0).getAddressLine(0);
                    }
                    else data = currentLocation.latitude+","+currentLocation.longitude;
                } catch (IOException e) {
                    data = currentLocation.latitude+","+currentLocation.longitude;
                    e.printStackTrace();
                }
                finally {
                    iModule2.onAddressBack(data,currentLocation);
                }
            }
        });
    }

    public void getPinnedLocation(int mode) {
        if(mode==3){
            final Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    int x = fragmentMapBinding.getRoot().getWidth() / 2;
                    int y = fragmentMapBinding.getRoot().getHeight() / 2;
                    LatLng position = googleMap.getProjection().fromScreenLocation(new Point(x, y));
                    String s = "";
                    try {
                        List<Address> addresses = geocoder.getFromLocation(position.latitude,position.longitude,1);
                        if (addresses.size()!=0){
                            s = addresses.get(0).getAddressLine(0);
                        }
                        else {
                            s = position.latitude+","+position.longitude;
                        }


                    } catch (IOException e) {
                        s = position.latitude+","+position.longitude;
                        e.printStackTrace();
                    }
                    finally {
                        iModule2.onAddressBack(s,position);
                    }
                }
            });
        }
        else googleMap.setOnCameraIdleListener(null);
    }

    public void moveCameraByAddress(String address){
        FetchAddress fetchAddress = new FetchAddress(context);
        try {
            LatLng latLng = fetchAddress.execute(address).get();
            iModule2.onAddressBack(address,latLng);
            googleMap.addMarker(new MarkerOptions().position(latLng).title(address));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void handleMode(int mode){
        getPinnedLocation(mode);
        if (mode==2){
            getCurrentLocation();
        }
    }

    private class FetchAddress extends AsyncTask<String,Void,LatLng> {
        Context mContext;

        public FetchAddress(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected LatLng doInBackground(String... strings) {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            LatLng latLng = null;
            try {
                List<Address> addresses = geocoder.getFromLocationName(strings[0],1);
                double lat = addresses.get(0).getLatitude();
                double lng = addresses.get(0).getLongitude();
                latLng = new LatLng(lat,lng);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return latLng;
        }

        @Override
        protected void onPostExecute(LatLng latLng) {
            super.onPostExecute(latLng);
        }
    }
}
