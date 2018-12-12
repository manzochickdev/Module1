package com.example.tuananh.module1.AddEditDetail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.FragmentEditBinding;
import com.example.tuananh.module1.databinding.LayoutRelationshipBinding;

import java.util.ArrayList;

public class EditFragment extends Fragment {
    FragmentEditBinding fragmentEditBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentEditBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit, container, false);
        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getContext());
        fragmentEditBinding.viewpager.setAdapter(customPagerAdapter);
        fragmentEditBinding.detailTabs.setupWithViewPager(fragmentEditBinding.viewpager);
        return fragmentEditBinding.getRoot();
    }

    protected class CustomPagerAdapter extends PagerAdapter{
        Context context;

        public CustomPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view==o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(context);
            switch (position){
                case 0:
                    View layoutInfo = inflater.inflate(R.layout.layout_info,container,false);
                    handleLayoutInfo(layoutInfo);
                    container.addView(layoutInfo);
                    return layoutInfo;
                case 1:
                    View layoutRelationship = inflater.inflate(R.layout.layout_relationship,container,false);
                    handleLayoutRelationship(layoutRelationship);
                    container.addView(layoutRelationship);
                    return layoutRelationship;
            }
            return null;
        }

        private void handleLayoutInfo(View view) {
        }

        RelationshipAdapter relationshipAdapter;
        private void handleLayoutRelationship(View view) {
            LayoutRelationshipBinding layoutRelationship = DataBindingUtil.bind(view);
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
            layoutRelationship.rvRelationship.setAdapter(relationshipAdapter);
            layoutRelationship.rvRelationship.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0){
                return "Info";
            }
            else return "Relationship";
        }
    }

}
