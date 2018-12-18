package com.example.tuananh.module1.AddEditDetail;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import com.example.tuananh.module1.BR;
import com.example.tuananh.module1.DatabaseHandle;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.Model.PeopleSearch;
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
    Boolean isEdit;
    OnDataHandle onHandler;
    com.example.tuananh.module1.AddEditDetail.OnDataHandle onDataHandle;
    ModelRela modelRela;

    public RelaViewModel(ModelRela modelRela, com.example.tuananh.module1.AddEditDetail.OnDataHandle onDataHandle, int position,Boolean isEdit) {
        this.modelRela = modelRela;
        this.onDataHandle = onDataHandle;
        this.position = position;
        this.isEdit = isEdit;
        setInterface();
        handleMode();
    }

    private void setInterface() {
        onHandler = new OnDataHandle() {
            @Override
            public void onDataBack(String relationship) {
                modelRela.relationship = relationship;
                notifyPropertyChanged(BR.modelRela);
            }

            @Override
            public void onDataBack(Model model) {
                modelRela.model = model;
                notifyPropertyChanged(BR.modelRela);
            }
        };
    }

    void handleMode(){
        if (this.modelRela.model !=null){
            if (this.modelRela.relationship!=null){
                this.mode=2;
                notifyPropertyChanged(BR.mode);
            }
            this.isVisible = true;
            notifyPropertyChanged(BR.visible);
        }
    }

    public OnDataHandle getOnHandler() {
        return onHandler;
    }
    @Bindable
    public Boolean getEdit() {
        if (this.modelRela.model !=null && this.modelRela.relationship!=null){
            return isEdit;
        }
        else return false;
    }

    @Bindable
    public ModelRela getModelRela() {
        return modelRela;
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
            onDataHandle.onRelationshipManipulation(mode,onHandler);
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
            if (modelRela.relationship!=null && modelRela.model!=null){
                isEdit = true;
                notifyPropertyChanged(BR.edit);
                onDataHandle.addNewRelationship();
            }
            else onDataHandle.cancelAddRelationship(position);
        }
    }

    public void handleRemove(){
        onDataHandle.onRemove(modelRela.model.getId());
    }

    public interface OnDataHandle{
        void onDataBack(String relationship);
        void onDataBack(Model model);
    }

    @BindingAdapter({"mode","onDataHandle"})
    public static void setLayout(FrameLayout view, final int mode, OnDataHandle onDataHandle){
        if (mode!=-1 && onDataHandle!=null){
            final Context context = view.getContext();
            view.removeAllViewsInLayout();
            if (mode==0){
                view.setVisibility(View.VISIBLE);
                View relationshipSelect = LayoutInflater.from(context).inflate(R.layout.layout_relationship_select,null,false);
                relationshipSelect.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                LayoutRelationshipSelectBinding layoutRelationshipSelectBinding = DataBindingUtil.bind(relationshipSelect);
                layoutRelationshipSelectBinding.setOnDataHandle(onDataHandle);
                layoutRelationshipSelectBinding.setRelationship(new ArrayList<>(Arrays.asList(Relationship.getRelationship())));
                view.addView(relationshipSelect);
            }
            else if (mode==1){
                final PeopleSearch peopleSearch = new PeopleSearch(context);
                view.setVisibility(View.VISIBLE);
                View peopleSelect = LayoutInflater.from(context).inflate(R.layout.layout_people_select,null,false);
                peopleSelect.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                LayoutPeopleSelectBinding layoutPeopleSelectBinding = DataBindingUtil.bind(peopleSelect);

                final ArrayList<Model> models = new ArrayList<>();
                final ArrayList<Model> fromDb = DatabaseHandle.getInstance(context).showPeople();
                final PeopleSearchAdapter peopleSearchAdapter = new PeopleSearchAdapter(models,context,onDataHandle);
                layoutPeopleSelectBinding.etSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        models.clear();
                        models.addAll(peopleSearch.onSearchListener(charSequence.toString(),5));
                        peopleSearchAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable editable) { }
                });
                layoutPeopleSelectBinding.rvSearch.setAdapter(peopleSearchAdapter);
                layoutPeopleSelectBinding.rvSearch.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
                view.addView(peopleSelect);
            }
            else view.setVisibility(View.GONE);
        }
    }
}
