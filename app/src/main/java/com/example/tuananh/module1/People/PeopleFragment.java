package com.example.tuananh.module1.People;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.DatabaseHandle;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.People.Adapter;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.FragmentPeopleBinding;

import java.util.ArrayList;


public class PeopleFragment extends Fragment {
    FragmentPeopleBinding fragmentPeopleBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentPeopleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_people, container, false);

        fragmentPeopleBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IMainActivity iMainActivity = (IMainActivity) getContext();
                iMainActivity.onAddListener();
            }
        });
        return fragmentPeopleBinding.getRoot();
    }

    private ArrayList<Model> getData() {
        return DatabaseHandle.getInstance(getContext()).showPeople();
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<Model> models = getData();
        Adapter adapter = new Adapter(models,getContext());
        fragmentPeopleBinding.rvPeople.setAdapter(adapter);
        fragmentPeopleBinding.rvPeople.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }
}
