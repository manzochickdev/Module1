package com.example.tuananh.module1.AddEditDetail;

public interface OnDataHandle {
    void addNewRelationship();
    void cancelAddRelationship();
    void onRelationshipManipulation(RelaViewModel.OnDataHandle onDataHandle);

    void onRemove(int position);
}
