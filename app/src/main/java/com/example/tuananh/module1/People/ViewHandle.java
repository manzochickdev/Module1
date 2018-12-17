package com.example.tuananh.module1.People;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.tuananh.module1.BR;
import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.Model.PeopleSearch;

import java.util.ArrayList;

public class ViewHandle extends BaseObservable{
    int mode=0;
    Boolean isSearch=false;
    String query="";
    IMainActivity iMainActivity;
    PeopleFragment.OnDataHandle onDataHandle;
    PeopleSearch peopleSearch;

    public ViewHandle(IMainActivity iMainActivity, PeopleFragment.OnDataHandle onDataHandle, PeopleSearch peopleSearch) {
        this.iMainActivity = iMainActivity;
        this.onDataHandle = onDataHandle;
        this.peopleSearch = peopleSearch;
    }

    @Bindable
    public int getMode() {
        return mode;
    }

    @Bindable
    public Boolean getSearch() {
        return isSearch;
    }

    public void onModeChange(){
        this.isSearch = !this.isSearch;
        if (!this.isSearch){
            onDataHandle.updateList(peopleSearch.getModels());
        }
        notifyPropertyChanged(BR.search);
    }

    public void onAddListener(){
        iMainActivity.onAddListener();
    }

    @Bindable
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
        ArrayList<Model> result = peopleSearch.onSearchListener(query,9999);
        onDataHandle.updateList(result);
        notifyPropertyChanged(BR.query);
    }
}
