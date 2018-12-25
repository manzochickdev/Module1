package com.example.tuananh.module1.Map.MapManipulation;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.tuananh.module1.Map.IModule2;
import com.example.tuananh.module1.Map.MapManipulation.PeopleSearch.Adapter;
import com.example.tuananh.module1.Map.MapManipulation.PlaceSearch.PlaceSearchFragment;
import com.example.tuananh.module1.Model.ModelAddress;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.FragmentMapManipulationBinding;
import com.google.android.gms.maps.model.LatLng;


public class MapManipulationFragment extends Fragment {
    FragmentMapManipulationBinding fragmentMapManipulationBinding;
    String address="";
    TextView textView;
    ModelAddress modelAddress;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMapManipulationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map_manipulation, container, false);
        context = getContext();
        ViewHandle viewHandle = new ViewHandle(true,context,(IModule2)getParentFragment());
        onModeHandle(0);
        fragmentMapManipulationBinding.setViewHandle(viewHandle);
        return fragmentMapManipulationBinding.getRoot();


    }

    public void onModeHandle(int mode) {
        FrameLayout frameLayout = fragmentMapManipulationBinding.fragContainer;
        frameLayout.removeAllViewsInLayout();
        if (mode==0){
            PlaceSearchFragment placeSearchFragment = new PlaceSearchFragment();
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frag_container,placeSearchFragment,"PlaceSearchFragment");
            fragmentTransaction.commit();
        }
        else if(mode==1){
            EditText editText = new EditText(context);
            editText.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            editText.setPadding(4,4,4,4);
            frameLayout.addView(editText);

            final String query="";
            FrameLayout frameLayout1 = fragmentMapManipulationBinding.fragContainer1;
            RecyclerView recyclerView = new RecyclerView(context);
            final Adapter adapter = new Adapter(query,context);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            recyclerView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            frameLayout1.addView(recyclerView);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //todo ok , remove with real data
                    adapter.onQuery(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) { }
            });
        }
        else {
            Log.d("OK", "onModeHandle: "+mode);
            textView = new TextView(context);
            textView.setText(address);
            textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(4,4,4,4);
            frameLayout.addView(textView);
        }
    }

    public void onAddressBack(String text, LatLng latLng) {
        modelAddress = new ModelAddress(text,latLng);

        this.address = text;
        if(textView!=null){
            textView.setText(text);
        }
    }

    public ModelAddress getAddress(){
        return modelAddress;
    }
}
