package com.example.tuananh.module1.People;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.Model.Model;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.LayoutPeopleItemBinding;

import java.util.ArrayList;

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<Model> models;
    Context context;

    public Adapter(ArrayList<Model> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_people_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.layoutPeopleItemBinding.setModel(models.get(i));
        viewHolder.layoutPeopleItemBinding.peopleContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IMainActivity iMainActivity = (IMainActivity) context;
                iMainActivity.onEditListener(models.get(i).getId());
            }
        });
        viewHolder.layoutPeopleItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutPeopleItemBinding layoutPeopleItemBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutPeopleItemBinding = DataBindingUtil.bind(itemView);
        }
    }
}
