package com.example.tuananh.module1.AddEditDetail;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.DatabaseHandle;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.FragmentEditBinding;
import com.example.tuananh.module1.databinding.LayoutInfoBinding;
import com.example.tuananh.module1.databinding.LayoutRelationshipBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class EditFragment extends Fragment {
    FragmentEditBinding fragmentEditBinding;
    int id;
    boolean isEdit;
    CustomPagerAdapter customPagerAdapter;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = this.getArguments();
        id = bundle.getInt("id");
        isEdit = bundle.getBoolean("isEdit",false);
        context = getContext();
        customPagerAdapter = new CustomPagerAdapter(context,id,isEdit);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentEditBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit, container, false);
        fragmentEditBinding.viewpager.setAdapter(customPagerAdapter);
        fragmentEditBinding.detailTabs.setupWithViewPager(fragmentEditBinding.viewpager);

        if (isEdit){
            fragmentEditBinding.detailTabs.getTabAt(1).select();
            fragmentEditBinding.setVisible(false);
        }
        else fragmentEditBinding.setVisible(true);


        fragmentEditBinding.toolbar.inflateMenu(R.menu.menu);
        fragmentEditBinding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.miEdit:
                        handleMode(true);
                        return true;
                    case R.id.miDelete:
                        //todo handle delete
                        DatabaseHandle.getInstance(context).removePerson(id);
                        IMain2Activity iMain2Activity = (IMain2Activity) context;
                        iMain2Activity.onBackListener();
                        return true;

                    default: return EditFragment.super.onOptionsItemSelected(menuItem);

                }
            }
        });
        fragmentEditBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleMode(false);
            }
        });
        fragmentEditBinding.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo handle OK
                Integer[] a1 = new Integer[]{1,2,3,4,5};
                ArrayList<Integer> a = new ArrayList<>(Arrays.asList(a1));
                Integer[]b1=new Integer[]{1,3,5,6,7};
                ArrayList<Integer> b = new ArrayList<>(Arrays.asList(b1));
                int m=0;
                int n=0;
                while (m<a.size()){
                    n=0;
                    while (n<b.size()){
                        int a2 = a.get(m);
                        int b2 = b.get(n);
                        if (a.get(m) == b.get(n)){
                            a.remove(m);

                            b.remove(n);
                        }
                        else n++;
                    }
                    m++;
                }
                Log.d("OK", "onClick: ");
            }
        });
        return fragmentEditBinding.getRoot();
    }


    private void handleMode(boolean b) {
        View view = (View) customPagerAdapter.onViewBack(1);
        LayoutRelationshipBinding layoutRelationship = DataBindingUtil.bind(view);
        RelationshipAdapter relationshipAdapter = (RelationshipAdapter) layoutRelationship.rvRelationship.getAdapter();
        relationshipAdapter.setIsEdit(b);

        View view1 = customPagerAdapter.onViewBack(0);
        LayoutInfoBinding layoutInfoBinding = DataBindingUtil.bind(view1);
        layoutInfoBinding.setIsEdit(b);
        fragmentEditBinding.setVisible(!b);
    }


    protected class CustomPagerAdapter extends PagerAdapter{
        Context context;
        int id;
        View layoutInfo;
        View layoutRelationship;
        Boolean isEdit;


        public CustomPagerAdapter(Context context,int id,Boolean isEdit) {
            this.context = context;
            this.id = id;
            this.isEdit = isEdit;
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
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            switch (position){
                case 0:
                    layoutInfo = layoutInflater.inflate(R.layout.layout_info,container,false);
                    handleLayoutInfo(layoutInfo);
                    container.addView(layoutInfo);
                    return layoutInfo;
                case 1:
                    layoutRelationship = layoutInflater.inflate(R.layout.layout_relationship,container,false);
                    handleLayoutRelationship(layoutRelationship);
                    container.addView(layoutRelationship);
                    return layoutRelationship;
            }
            return null;
        }

        private void handleLayoutInfo(View view) {
            LayoutInfoBinding layoutInfoBinding = DataBindingUtil.bind(view);
            layoutInfoBinding.setIsEdit(isEdit);
            String name = DatabaseHandle.getInstance(context).getName(id);
            layoutInfoBinding.etName.setText(name);
        }

        RelationshipAdapter relationshipAdapter;
        ArrayList<ModelRela> modelRelas;
        private void handleLayoutRelationship(View view) {
            LayoutRelationshipBinding layoutRelationship = DataBindingUtil.bind(view);
            modelRelas = DatabaseHandle.getInstance(context).getAllRelative(id);
            if (modelRelas==null){
                modelRelas = new ArrayList<>();
            }
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

                @Override
                public void onRemove(int position) {
                    int pos =-1;
                    for (ModelRela m : modelRelas){
                        if (m.model!=null&&m.relationship!=null){
                            if (m.model.getId()==position){
                                pos = modelRelas.indexOf(m);
                            }
                        }
                    }
                    modelRelas.remove(pos);
                    relationshipAdapter.notifyItemRemoved(pos);
                    DatabaseHandle.getInstance(context).removeRelative(id,position);
                }
            };
            relationshipAdapter = new RelationshipAdapter(modelRelas,context,onDataHandle);
            layoutRelationship.rvRelationship.setAdapter(relationshipAdapter);
            layoutRelationship.rvRelationship.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
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

        public View onViewBack(int pos){
            switch (pos){
                case 0:
                    return layoutInfo;
                case 1:
                    return layoutRelationship;
                    default:return null;
            }

        }
    }

}
