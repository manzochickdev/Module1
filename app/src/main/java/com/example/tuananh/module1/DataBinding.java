package com.example.tuananh.module1;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

public class DataBinding {
    @BindingAdapter("setProfileImage")
    public static void setProfileImage(CircleImageView view,int id){
        if(id!=-1){
            File file = new File(view.getContext().getFilesDir(),Integer.toString(id));
            try {
                Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(file));
                view.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                view.setImageResource(R.mipmap.ic_launcher);
                e.printStackTrace();
            }
        }
    }
}
