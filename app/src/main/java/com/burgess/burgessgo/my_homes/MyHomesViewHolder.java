package com.burgess.burgessgo.my_homes;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;

public class MyHomesViewHolder extends RecyclerView.ViewHolder {
    private final ConstraintLayout mConstraintLayoutUpper;
    private final ConstraintLayout mConstraintLayoutLower;
    private final TextView mTextViewCommunity;
    private final TextView mTextViewAddress;
    private final ImageView mImageViewArrow;
    private final Button mButtonScheduleNewInspection;
    private final Button mButtonOpenDefects;
    private final TableLayout mTableLayout;

    public MyHomesViewHolder(@NonNull View itemView) {
        super(itemView);
        mConstraintLayoutUpper = itemView.findViewById(R.id.item_my_home_constraint_layout_upper);
        mConstraintLayoutLower = itemView.findViewById(R.id.item_my_home_constraint_layout_lower);
        mTextViewCommunity = itemView.findViewById(R.id.item_my_home_textView_community);
        mTextViewAddress = itemView.findViewById(R.id.item_my_home_textView_address);
        mImageViewArrow = itemView.findViewById(R.id.item_my_home_imageView_arrow);
        mButtonScheduleNewInspection = itemView.findViewById(R.id.item_my_home_button_schedule_new_inspection);
        mButtonOpenDefects = itemView.findViewById(R.id.item_my_home_button_open_defects);
        mTableLayout = itemView.findViewById(R.id.item_my_home_table_layout);
    }

    //region GETTERS

    public ConstraintLayout getConstraintLayoutUpper() {
        return mConstraintLayoutUpper;
    }

    public ConstraintLayout getConstraintLayoutLower() {
        return mConstraintLayoutLower;
    }

    public TextView getTextViewCommunity() {
        return mTextViewCommunity;
    }

    public TextView getTextViewAddress() {
        return mTextViewAddress;
    }

    public ImageView getImageViewArrow() {
        return mImageViewArrow;
    }

    public Button getButtonScheduleNewInspection() {
        return mButtonScheduleNewInspection;
    }

    public Button getButtonOpenDefects() {
        return mButtonOpenDefects;
    }

    public TableLayout getTableLayout() {
        return mTableLayout;
    }

    //endregion
}
