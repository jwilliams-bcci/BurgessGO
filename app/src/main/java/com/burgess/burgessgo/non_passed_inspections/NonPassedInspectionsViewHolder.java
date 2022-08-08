package com.burgess.burgessgo.non_passed_inspections;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;
import com.burgess.burgessgo.non_passed_inspection_details.NonPassedInspectionDetailsActivity;

import data.models.NonPassedInspection;

public class NonPassedInspectionsViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTextViewCommunity;
    private final TextView mTextViewAddress;
    private final TextView mTextViewInspectionType;
    private NonPassedInspection mInspection;

    public NonPassedInspectionsViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextViewCommunity = itemView.findViewById(R.id.item_inspection_textView_community);
        mTextViewAddress = itemView.findViewById(R.id.item_inspection_textView_address);
        mTextViewInspectionType = itemView.findViewById(R.id.item_inspection_textView_inspection_type);

        itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NonPassedInspectionDetailsActivity.class);
            intent.putExtra(NonPassedInspectionDetailsActivity.INTENT_EXTRA, mInspection);
            v.getContext().startActivity(intent);
        });
    }

    public void bind(String community, String address, String inspectionType, NonPassedInspection inspection) {
        mTextViewCommunity.setText(community);
        mTextViewAddress.setText(address);
        mTextViewInspectionType.setText(inspectionType);
        mInspection = inspection;
    }

    public static NonPassedInspectionsViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inspection, parent, false);
        return new NonPassedInspectionsViewHolder(view);
    }
}
