package com.example.tuananh.module1.AddEditDetail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.LayoutPeopleCircleItemBinding;

import java.util.ArrayList;

public class PeopleSearchAdapter extends RecyclerView.Adapter<PeopleSearchAdapter.ViewHolder> {
    ArrayList<Model> models;
    Context context;
    RelaViewModel.OnDataHandle onDataHandle;

    public PeopleSearchAdapter(ArrayList<Model> models, Context context,RelaViewModel.OnDataHandle onDataHandle) {
        this.models = models;
        this.context = context;
        this.onDataHandle = onDataHandle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_people_circle_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.layoutPeopleCircleItemBinding.setModel(models.get(i));
        viewHolder.layoutPeopleCircleItemBinding.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDataHandle.onDataBack(models.get(i));
            }
        });
        viewHolder.layoutPeopleCircleItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutPeopleCircleItemBinding layoutPeopleCircleItemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutPeopleCircleItemBinding = DataBindingUtil.bind(itemView);
        }
    }
}
