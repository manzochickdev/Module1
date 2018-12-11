package com.example.tuananh.module1.AddEditDetail;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.tuananh.module1.BR;
import com.example.tuananh.module1.Model.Relationship;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.LayoutRelationshipSelectBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class RelaViewModel extends BaseObservable {
    int click=0;
    int mode=-1;
    Boolean isVisible=false;

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

    public interface OnDataHandle{
        void onDataBack(String relationship);
    }

    @BindingAdapter("setLayout")
    public static void setLayout(FrameLayout view, int mode){
        view.removeAllViewsInLayout();
        if (mode==0){

            View relationshipSelect = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_relationship_select,null,false);
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
    }
}
