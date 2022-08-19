package com.burgess.burgessgo.deactivate_homes;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;

public class DeactivateHomesViewHolder extends RecyclerView.ViewHolder {
    private final ConstraintLayout mConstraintLayout;
    private final ConstraintLayout mConstraintLayoutUpper;
    private final ConstraintLayout mConstraintLayoutLower;
    private final TextView mTextViewCommunity;
    private final TextView mTextViewAddressUpper;
    private final ImageView mImageViewArrow;
    private final TextView mTextViewAddressLower;
    private final Button mButtonYes;
    private final Button mButtonNo;

    public DeactivateHomesViewHolder(@NonNull View itemView) {
        super(itemView);
        mConstraintLayout = itemView.findViewById(R.id.item_deactivate_home_constraint_layout);
        mConstraintLayoutUpper = itemView.findViewById(R.id.item_deactivate_home_constraint_layout_upper);
        mConstraintLayoutLower = itemView.findViewById(R.id.item_deactivate_home_constraint_layout_lower);
        mTextViewCommunity = itemView.findViewById(R.id.item_deactivate_home_textView_community);
        mTextViewAddressUpper = itemView.findViewById(R.id.item_deactivate_home_textView_address_upper);
        mImageViewArrow = itemView.findViewById(R.id.item_deactivate_home_imageView_arrow);
        mTextViewAddressLower = itemView.findViewById(R.id.item_deactivate_home_textView_address_lower);
        mButtonYes = itemView.findViewById(R.id.item_deactivate_home_button_yes);
        mButtonNo = itemView.findViewById(R.id.item_deactivate_home_button_no);
    }

    //region GETTERS
    public ConstraintLayout getConstraintLayout() {
        return mConstraintLayout;
    }

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

    public ImageView getImageViewArrow() {
        return mImageViewArrow;
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
    //endregion
}
