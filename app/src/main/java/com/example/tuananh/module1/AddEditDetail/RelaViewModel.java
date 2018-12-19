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
import android.widget.LinearLayout;

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
    Boolean isVisible=false;
    Boolean isEdit;
    Boolean isFinish=false;
    OnDataHandle onHandler;
    com.example.tuananh.module1.AddEditDetail.OnDataHandle onDataHandle;
    ModelRela modelRela;
    String manipulation;

    public RelaViewModel(ModelRela modelRela, com.example.tuananh.module1.AddEditDetail.OnDataHandle onDataHandle,String manipulation,Boolean isEdit) {
        this.modelRela = modelRela;
        this.onDataHandle = onDataHandle;
        this.manipulation = manipulation;
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
                checkFinish();
            }

            @Override
            public void onDataBack(Model model) {
                modelRela.model = model;
                notifyPropertyChanged(BR.modelRela);
                checkFinish();
            }
        };
    }

    void handleMode(){
        if (this.modelRela.model !=null){
            if (this.modelRela.relationship!=null){
                isFinish = true;
            }
            this.isVisible = true;
            notifyPropertyChanged(BR.visible);
        }
    }

    @Bindable
    public Boolean getEdit() {
        if (this.manipulation.equals("edit")){
            return isEdit;
        }
        else return true;
    }

    @Bindable
    public ModelRela getModelRela() {
        return modelRela;
    }


    @Bindable
    public Boolean getVisible() {
        return isVisible;
    }

    public void addModelRela(){
        isVisible = !isVisible;
        onDataHandle.onRelationshipManipulation(onHandler);
        notifyPropertyChanged(BR.visible);
    }

    void checkFinish(){
        if (modelRela.relationship!=null && modelRela.model!=null){
            isFinish = true;
            onDataHandle.onRelationshipManipulation(null);
            onDataHandle.addNewRelationship();
        }
    }

    public void handleRemove(){
        if (isFinish){
            onDataHandle.onRemove(modelRela.model.getId());
        }
        else {
            onDataHandle.cancelAddRelationship();
            onDataHandle.onRelationshipManipulation(null);
        }
    }

    public interface OnDataHandle{
        void onDataBack(String relationship);
        void onDataBack(Model model);
    }

    @BindingAdapter("setLayout")
    public static void setLayout(LinearLayout view, OnDataHandle onDataHandle){
        Context context = view.getContext();
        view.removeAllViewsInLayout();
        if (onDataHandle!=null){
            view.setVisibility(View.VISIBLE);
            View relationshipSelect = LayoutInflater.from(context).inflate(R.layout.layout_relationship_select,null,false);
            relationshipSelect.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            LayoutRelationshipSelectBinding layoutRelationshipSelectBinding = DataBindingUtil.bind(relationshipSelect);
            layoutRelationshipSelectBinding.setOnDataHandle(onDataHandle);
            layoutRelationshipSelectBinding.setRelationship(new ArrayList<>(Arrays.asList(Relationship.getRelationship())));
            view.addView(relationshipSelect);

            final PeopleSearch peopleSearch = new PeopleSearch(context);
            view.setVisibility(View.VISIBLE);
            View peopleSelect = LayoutInflater.from(context).inflate(R.layout.layout_people_select,null,false);
            peopleSelect.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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
