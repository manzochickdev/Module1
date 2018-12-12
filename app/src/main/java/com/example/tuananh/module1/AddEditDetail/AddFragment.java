package com.example.tuananh.module1.AddEditDetail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.FragmentAddBinding;

import java.util.ArrayList;


public class AddFragment extends Fragment {
    FragmentAddBinding fragmentAddBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAddBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add, container, false);
        handleInfoLayout();
        handleRelationshipLayout();
        return fragmentAddBinding.getRoot();
    }

    RelationshipAdapter relationshipAdapter;
    private void handleRelationshipLayout() {
        final ArrayList<ModelRela> modelRelas = new ArrayList<>();
        modelRelas.add(new ModelRela());
        OnDataHandle onDataHandle = new OnDataHandle() {
            @Override
            public void addNewRelationship() {
                modelRelas.add(new ModelRela());
                relationshipAdapter.notifyItemInserted(modelRelas.size()-1);
            }

            @Override
            public void cancelAddRelationship(int position) {
                modelRelas.set(position,new ModelRela());
                relationshipAdapter.notifyItemChanged(position);
            }
        };
        relationshipAdapter = new RelationshipAdapter(modelRelas,getContext(),onDataHandle);
        fragmentAddBinding.layoutRelationship.rvRelationship.setAdapter(relationshipAdapter);
        fragmentAddBinding.layoutRelationship.rvRelationship.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

    private void handleInfoLayout() {
    }

    public interface OnDataHandle{
        void addNewRelationship();
        void cancelAddRelationship(int position);
    }
}
