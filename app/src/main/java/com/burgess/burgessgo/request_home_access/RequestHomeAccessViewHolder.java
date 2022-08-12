package com.burgess.burgessgo.request_home_access;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;

public class RequestHomeAccessViewHolder extends RecyclerView.ViewHolder {
    private final ConstraintLayout mConstraintLayoutUpper;
    private final ConstraintLayout mConstraintLayoutLower;
    private final TextView mTextViewCommunity;
    private final TextView mTextViewAddressUpper;
    private final TextView mTextViewAddressLower;
    private final Button mButtonYes;
    private final Button mButtonNo;
    private final ImageView mImageViewArrow;

    public RequestHomeAccessViewHolder(@NonNull View itemView) {
        super(itemView);
        mConstraintLayoutUpper = itemView.findViewById(R.id.item_request_home_access_constraint_layout_upper);
        mConstraintLayoutLower = itemView.findViewById(R.id.item_request_home_access_constraint_layout_lower);
        mTextViewCommunity = itemView.findViewById(R.id.item_request_home_access_textView_community);
        mTextViewAddressUpper = itemView.findViewById(R.id.item_request_home_access_textView_address_upper);
        mTextViewAddressLower = itemView.findViewById(R.id.item_request_home_access_textView_address_lower);
        mButtonYes = itemView.findViewById(R.id.item_request_home_access_button_yes);
        mButtonNo = itemView.findViewById(R.id.item_request_home_access_button_no);
        mImageViewArrow = itemView.findViewById(R.id.item_request_home_access_imageView_arrow);
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

    public TextView getTextViewAddressUpper() {
        return mTextViewAddressUpper;
    }

    public TextView getTextViewAddressLower() {
        return mTextViewAddressLower;
    }

    public Button getButtonYes() {
        return mButtonYes;
    }

    public Button getButtonNo() {
        return mButtonNo;
    }

    public ImageView getImageViewArrow() {
        return mImageViewArrow;
    }
    //endregion
}
