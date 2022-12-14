package com.burgess.burgessgo.location_defects;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;

public class LocationDefectsViewHolder extends RecyclerView.ViewHolder {
    private final ConstraintLayout mConstraintLayout;
    private final ConstraintLayout mConstraintLayoutUpper;
    private final ConstraintLayout mConstraintLayoutHeaderInner;
    private final ConstraintLayout mConstraintLayoutLower;
    private final TextView mTextViewCategory;
    private final TextView mTextViewColumnHeader1;
    private final TextView mTextViewColumnHeader2;
    private final TextView mTextViewItemDescription;
    private final TextView mTextViewItemText;

    public LocationDefectsViewHolder(@NonNull View itemView) {
        super(itemView);
        mConstraintLayout = itemView.findViewById(R.id.item_inspection_defect_constraint_layout);
        mConstraintLayoutUpper = itemView.findViewById(R.id.item_inspection_defect_constraint_layout_upper);
        mConstraintLayoutHeaderInner = itemView.findViewById(R.id.item_inspection_defect_constraint_layout_header_inner);
        mConstraintLayoutLower = itemView.findViewById(R.id.item_inspection_defect_constraint_layout_lower);
        mTextViewCategory = itemView.findViewById(R.id.item_inspection_defect_textView_category);
        mTextViewColumnHeader1 = itemView.findViewById(R.id.item_inspection_defect_textView_column_header_1);
        mTextViewColumnHeader2 = itemView.findViewById(R.id.item_inspection_defect_textView_column_header_2);
        mTextViewItemDescription = itemView.findViewById(R.id.item_inspection_defect_textView_item_description);
        mTextViewItemText = itemView.findViewById(R.id.item_inspection_defect_textView_item_text);
    }

    //region GETTERS
    public ConstraintLayout getConstraintLayoutHeaderInner() {
        return mConstraintLayoutHeaderInner;
    }

    public TextView getTextViewCategory() {
        return mTextViewCategory;
    }

    public TextView getTextViewColumnHeader1() {
        return mTextViewColumnHeader1;
    }

    public TextView getTextViewColumnHeader2() {
        return mTextViewColumnHeader2;
    }

    public TextView getTextViewItemDescription() {
        return mTextViewItemDescription;
    }

    public TextView getTextViewItemText() {
        return mTextViewItemText;
    }

    public ConstraintLayout getConstraintLayout() {
        return mConstraintLayout;
    }

    public ConstraintLayout getConstraintLayoutUpper() {
        return mConstraintLayoutUpper;
    }

    public ConstraintLayout getConstraintLayoutLower() {
        return mConstraintLayoutLower;
    }
//endregion
}
