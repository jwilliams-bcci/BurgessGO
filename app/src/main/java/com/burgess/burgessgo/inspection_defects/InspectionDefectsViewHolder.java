package com.burgess.burgessgo.inspection_defects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;

import data.models.InspectionDefect;

public class InspectionDefectsViewHolder extends RecyclerView.ViewHolder {
    private final ConstraintLayout mConstraintLayoutHeader;
    private final TextView mTextViewCategory;
    private final TextView mTextViewColumnHeader1;
    private final TextView mTextViewColumnHeader2;
    private final TextView mTextViewItemDescription;
    private final TextView mTextViewItemText;
    private InspectionDefect mInspectionDefect;

    public InspectionDefectsViewHolder(@NonNull View itemView) {
        super(itemView);
        mConstraintLayoutHeader = itemView.findViewById(R.id.item_inspection_defect_constraint_layout_header);
        mTextViewCategory = itemView.findViewById(R.id.item_inspection_defect_textView_category);
        mTextViewColumnHeader1 = itemView.findViewById(R.id.item_inspection_defect_textView_column_header_1);
        mTextViewColumnHeader2 = itemView.findViewById(R.id.item_inspection_defect_textView_column_header_2);
        mTextViewItemDescription = itemView.findViewById(R.id.item_inspection_defect_textView_item_description);
        mTextViewItemText = itemView.findViewById(R.id.item_inspection_defect_textView_item_text);
    }

    public void bind(String category, String columnHeader1, String columnHeader2, String description, String text, InspectionDefect inspectionDefect) {
        mTextViewCategory.setText(category);
        mTextViewColumnHeader1.setText(columnHeader1);
        mTextViewColumnHeader2.setText(columnHeader2);
        mTextViewItemDescription.setText(description);
        mTextViewItemText.setText(text);
        mInspectionDefect = inspectionDefect;
    }

    public static InspectionDefectsViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inspection_defect, parent, false);
        return new InspectionDefectsViewHolder(view);
    }
}
