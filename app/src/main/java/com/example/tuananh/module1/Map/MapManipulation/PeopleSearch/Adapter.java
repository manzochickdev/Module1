package com.example.tuananh.module1.Map.MapManipulation.PeopleSearch;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.LayoutProfileImageCircleBinding;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    String query;
    Context context;

    public Adapter(String query, Context context) {
        this.query = query;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_profile_image_circle,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d("OK", "onBindViewHolder: "+query);
        viewHolder.layoutProfileImageCircleBinding.executePendingBindings();
    }

    @Override

    public int getItemCount() {
        return 2;
    }

    public void onQuery(String query){
        this.query = query;
        notifyDataSetChanged();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutProfileImageCircleBinding layoutProfileImageCircleBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutProfileImageCircleBinding = DataBindingUtil.bind(itemView);
        }
    }
}
