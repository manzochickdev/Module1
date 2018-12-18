package com.example.tuananh.module1.AddEditDetail;

public interface OnDataHandle {
    void addNewRelationship();
    void cancelAddRelationship(int position);
    void onRelationshipManipulation(int mode, RelaViewModel.OnDataHandle onDataHandle);

    void onRemove(int position);
}
