package com.example.tuananh.module1;

import android.content.Context;
import android.content.res.AssetManager;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.widget.TextView;

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

    @BindingAdapter("setFont")
    public static void setFont(TextView view, Context context){
        AssetManager assetManager = context.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager,"Font/icon.ttf");
        view.setTypeface(typeface);

    }
}
