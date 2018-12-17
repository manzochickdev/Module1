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

import java.lang.reflect.Array;
import java.util.ArrayList;

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<ArrayList<Model>> list;
    Context context;
    IMainActivity iMainActivity;

    public Adapter(ArrayList<Model> models, Context context) {
        this.context = context;
        list = handleList(models);
        iMainActivity = (IMainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_people_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.layoutPeopleItemBinding.setModels(list.get(i));
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.container :
                        iMainActivity.onEditListener(list.get(i).get(0).getId());
                        break;
                    case R.id.container1:
                        iMainActivity.onEditListener(list.get(i).get(1).getId());
                        break;
                    case R.id.container2:
                        iMainActivity.onEditListener(list.get(i).get(2).getId());
                        break;
                }
            }
        };
        viewHolder.layoutPeopleItemBinding.container.setOnClickListener(onClickListener);
        viewHolder.layoutPeopleItemBinding.container1.setOnClickListener(onClickListener);
        viewHolder.layoutPeopleItemBinding.container2.setOnClickListener(onClickListener);
//        viewHolder.layoutPeopleItemBinding.setModel(models.get(i));
//        viewHolder.layoutPeopleItemBinding.peopleContainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                IMainActivity iMainActivity = (IMainActivity) context;
//                iMainActivity.onEditListener(models.get(i).getId());
//            }
//        });
        viewHolder.layoutPeopleItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    ArrayList<ArrayList<Model>> handleList(ArrayList<Model> models){
        ArrayList<ArrayList<Model>> lists = new ArrayList<>();
        while(models.size()>=3){
            ArrayList<Model> temp = new ArrayList<>(models.subList(0,3));
            lists.add(temp);
            models.removeAll(temp);
        }
        lists.add(models);
        return lists;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutPeopleItemBinding layoutPeopleItemBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutPeopleItemBinding = DataBindingUtil.bind(itemView);
        }
    }
}
