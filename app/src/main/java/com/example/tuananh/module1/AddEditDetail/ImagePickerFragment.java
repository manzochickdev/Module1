package com.example.tuananh.module1.AddEditDetail;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.FragmentImagePickerBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


public class ImagePickerFragment extends BottomSheetDialogFragment {
    FragmentImagePickerBinding fragmentImagePickerBinding;
    IMain2Activity iMain2Activity;
    int mode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        mode = bundle.getInt("mode");
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentImagePickerBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_image_picker,container,false);
        iMain2Activity = (IMain2Activity) getContext();
        fragmentImagePickerBinding.btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,9191);


            }
        });

        fragmentImagePickerBinding.btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent.createChooser(intent,"Select"),9292);


            }
        });

        fragmentImagePickerBinding.btnStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent.createChooser(intent,"Select"),9292);


            }
        });
        fragmentImagePickerBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return fragmentImagePickerBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            switch (requestCode){
                case 9191:
                    try{
                        Bundle extras = data.getExtras();
                        Bitmap bitma1p = (Bitmap) extras.get("data");
                        iMain2Activity.onImageBack(bitma1p,mode);
                        dismiss();
                    }
                    catch (Exception e){

                    }
                    finally {

                    }
                    break;
                case 9292:
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                        iMain2Activity.onImageBack(bitmap,mode);
                        dismiss();
                    }
                    catch (IOException e) { }
                    finally { }
                    break;

            }
        }
    }

    private void testFileSave() {
        File file = new File(getContext().getFilesDir(),"aaaa");
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = getContext().openFileOutput("aaaa",Context.MODE_PRIVATE);
            String s = "test";
            fileOutputStream.write(s.getBytes());
            Log.d("OK", "testFileSave: ");
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileInputStream fileInputStream;
        try {
            fileInputStream = getContext().openFileInput("aaaa");
            int temp =-1;
            String result="";
            while ((temp = fileInputStream.read())!=-1){
                result+=(char) temp;
            }
            Log.d("OK", "testFileSave: "+result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
