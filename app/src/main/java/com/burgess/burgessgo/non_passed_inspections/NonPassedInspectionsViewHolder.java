package com.burgess.burgessgo.non_passed_inspections;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;
import com.burgess.burgessgo.non_passed_inspection_details.NonPassedInspectionDetailsActivity;

import data.models.NonPassedInspection;

public class NonPassedInspectionsViewHolder extends RecyclerView.ViewHolder {
    private final ConstraintLayout mConstraintLayoutUpper;
    private final ConstraintLayout mConstraintLayoutLower;
    private final TextView mTextViewDate;
    private final TextView mTextViewCommunity;
    private final TextView mTextViewAddress;
    private final TextView mTextViewInspectionType;

    public NonPassedInspectionsViewHolder(@NonNull View itemView) {
        super(itemView);
        mConstraintLayoutUpper = itemView.findViewById(R.id.item_inspection_constraint_layout_upper);
        mConstraintLayoutLower = itemView.findViewById(R.id.item_inspection_constraint_layout_lower);
        mTextViewDate = itemView.findViewById(R.id.item_inspection_textView_inspection_date);
        mTextViewCommunity = itemView.findViewById(R.id.item_inspection_textView_community);
        mTextViewAddress = itemView.findViewById(R.id.item_inspection_textView_address);
        mTextViewInspectionType = itemView.findViewById(R.id.item_inspection_textView_inspection_type);
    }

    //region GETTERS AND SETTERS
    public ConstraintLayout getConstraintLayoutUpper() {
        return mConstraintLayoutUpper;
    }

    public ConstraintLayout getConstraintLayoutLower() {
        return mConstraintLayoutLower;
    }

    public TextView getTextViewDate() {
        return mTextViewDate;
    }

    public TextView getTextViewCommunity() {
        return mTextViewCommunity;
    }

    public TextView getTextViewAddress() {
        return mTextViewAddress;
    }

    public TextView getTextViewInspectionType() {
        return mTextViewInspectionType;
    }
    //endregion
}
