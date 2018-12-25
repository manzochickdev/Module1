package com.example.tuananh.module1.AddEditDetail;

import android.graphics.Bitmap;

import com.example.tuananh.module1.Model.ModelAddress;

import java.util.ArrayList;

public interface IMain2Activity {
    void onDataBack(String name, ArrayList<ModelRela> modelRela, Bitmap bitmap, ModelAddress modelAddress);
    void onPickAddress();
    void onBackListener();

    void onImageBack(Bitmap bitma1p, int mode);
    void saveBitmap(int id,Bitmap bitmap);

    void reload(int id,Boolean isEdit);

    void handleDelete(int id);
}
