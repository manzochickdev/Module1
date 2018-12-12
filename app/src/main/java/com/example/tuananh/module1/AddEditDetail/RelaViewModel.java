package com.example.tuananh.module1.AddEditDetail;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.tuananh.module1.BR;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.Model.Relationship;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.LayoutPeopleSelectBinding;
import com.example.tuananh.module1.databinding.LayoutRelationshipSelectBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class RelaViewModel extends BaseObservable {
    int click=0;
    int mode=-1;
    Boolean isVisible=false;
    int position;
    AddFragment.OnDataHandle onDataHandle;
    ModelRela modelRela;

    public RelaViewModel(ModelRela modelRela,AddFragment.OnDataHandle onDataHandle,int position) {
        this.modelRela = modelRela;
        this.onDataHandle = onDataHandle;
        this.position = position;
        handleMode();
    }

    void handleMode(){
        if (this.modelRela.model !=null && this.modelRela.relationship !=null){
            this.mode=2;
            notifyPropertyChanged(BR.mode);
        }
    }

    @Bindable
    public int getMode() {
        return mode;
    }

    @Bindable
    public Boolean getVisible() {
        return isVisible;
    }

    public void onModeChange(int mode){
        if (this.mode==mode){
            click++;
            if (click==1){
                this.mode=-1;
                notifyPropertyChanged(BR.mode);
                click=0;
            }
        }
        else{
            this.mode = mode;
            notifyPropertyChanged(BR.mode);
        }
    }
    public void addModelRela(){
        isVisible = !isVisible;
        notifyPropertyChanged(BR.visible);
    }

    public void handleFinish(int mode){
        this.mode=2;
        notifyPropertyChanged(BR.mode);
        if (mode==0){
            onDataHandle.cancelAddRelationship(position);
        }
        else if (mode==1){
            //todo handle ok click -> check model!=null || relationship != null
            onDataHandle.addNewRelationship();
        }
    }

    public interface OnDataHandle{
        void onDataBack(String relationship);
    }

    @BindingAdapter("setLayout")
    public static void setLayout(FrameLayout view, int mode){
        Context context = view.getContext();
        view.removeAllViewsInLayout();
        if (mode==0){
            view.setVisibility(View.VISIBLE);
            View relationshipSelect = LayoutInflater.from(context).inflate(R.layout.layout_relationship_select,null,false);
            relationshipSelect.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            LayoutRelationshipSelectBinding layoutRelationshipSelectBinding = DataBindingUtil.bind(relationshipSelect);
            layoutRelationshipSelectBinding.setOnDataHandle(new OnDataHandle() {
                @Override
                public void onDataBack(String relationship) {
                    Log.d("OK", "onDataBack: "+relationship);
                }
            });
            layoutRelationshipSelectBinding.setRelationship(new ArrayList<>(Arrays.asList(Relationship.getRelationship())));
            view.addView(relationshipSelect);
        }
        else if (mode==1){
            view.setVisibility(View.VISIBLE);
            View peopleSelect = LayoutInflater.from(context).inflate(R.layout.layout_people_select,null,false);
            peopleSelect.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            LayoutPeopleSelectBinding layoutPeopleSelectBinding = DataBindingUtil.bind(peopleSelect);
            //todo remove with real data
            //todo handle et search
            PeopleSearchAdapter peopleSearchAdapter = new PeopleSearchAdapter(new ArrayList<Model>(),context);
            layoutPeopleSelectBinding.rvSearch.setAdapter(peopleSearchAdapter);
            layoutPeopleSelectBinding.rvSearch.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            view.addView(peopleSelect);
        }
        else view.setVisibility(View.GONE);
    }
}
