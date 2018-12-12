package com.example.tuananh.module1.AddEditDetail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.LayoutRelationshipItemBinding;

import java.util.ArrayList;

class RelationshipAdapter extends RecyclerView.Adapter<RelationshipAdapter.ViewHolder> {
    ArrayList<ModelRela> modelRelas;
    Context context;
    OnDataHandle onDataHandle;

    public RelationshipAdapter(ArrayList<ModelRela> modelRelas, Context context, OnDataHandle onDataHandle) {
        this.modelRelas = modelRelas;
        this.context = context;
        this.onDataHandle = onDataHandle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_relationship_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.layoutRelationshipItemBinding.setViewModel(new RelaViewModel(modelRelas.get(i),onDataHandle,i));
        viewHolder.layoutRelationshipItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return modelRelas.size();
    }

    ArrayList<ModelRela> getItemList(){
        return this.modelRelas;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutRelationshipItemBinding layoutRelationshipItemBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutRelationshipItemBinding = DataBindingUtil.bind(itemView);
        }
    }
}
