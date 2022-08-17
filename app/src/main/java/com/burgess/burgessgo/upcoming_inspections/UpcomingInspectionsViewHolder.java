package com.burgess.burgessgo.upcoming_inspections;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;

public class UpcomingInspectionsViewHolder extends RecyclerView.ViewHolder {
    private final ConstraintLayout mConstraintLayoutUpper;
    private final ConstraintLayout mConstraintLayoutLower;
    private final TextView mTextViewDate;
    private final TextView mTextViewCommunity;
    private final TextView mTextViewAddress;
    private final TextView mTextViewInspectionType;

    public UpcomingInspectionsViewHolder(@NonNull View itemView) {
        super(itemView);
        mConstraintLayoutUpper = itemView.findViewById(R.id.item_inspection_constraint_layout_upper);
        mConstraintLayoutLower = itemView.findViewById(R.id.item_inspection_constraint_layout_lower);
        mTextViewDate = itemView.findViewById(R.id.item_inspection_textView_inspection_date);
        mTextViewCommunity = itemView.findViewById(R.id.item_inspection_textView_community);
        mTextViewAddress = itemView.findViewById(R.id.item_inspection_textView_address);
        mTextViewInspectionType = itemView.findViewById(R.id.item_inspection_textView_inspection_type);
    }

    //region GETTERS
    public TextView getTextViewCommunity() {
        return mTextViewCommunity;
    }

    public TextView getTextViewAddress() {
        return mTextViewAddress;
    }

    public TextView getTextViewInspectionType() {
        return mTextViewInspectionType;
    }

    public ConstraintLayout getConstraintLayoutUpper() {
        return mConstraintLayoutUpper;
    }

    public ConstraintLayout getConstraintLayoutLower() {
        return mConstraintLayoutLower;
    }

    public TextView getTextViewDate() {
        return mTextViewDate;
    }
//endregion
}
