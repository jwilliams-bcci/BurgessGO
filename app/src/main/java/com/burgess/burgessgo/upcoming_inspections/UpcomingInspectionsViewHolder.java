package com.burgess.burgessgo.upcoming_inspections;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;
import com.burgess.burgessgo.inspection_details.InspectionDetailsActivity;

import data.models.Inspection;

public class UpcomingInspectionsViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTextViewCommunity;
    private final TextView mTextViewAddress;
    private final TextView mTextViewInspectionType;
    private Inspection mInspection;

    public UpcomingInspectionsViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextViewCommunity = itemView.findViewById(R.id.item_inspection_textView_community);
        mTextViewAddress = itemView.findViewById(R.id.item_inspection_textView_address);
        mTextViewInspectionType = itemView.findViewById(R.id.item_inspection_textView_inspection_type);

        itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), InspectionDetailsActivity.class);
            intent.putExtra(InspectionDetailsActivity.INTENT_EXTRA, (Inspection) mInspection);
            v.getContext().startActivity(intent);
        });
    }

    public void bind(String community, String address, String inspectionType, Inspection inspection) {
        mTextViewCommunity.setText(community);
        mTextViewAddress.setText(address);
        mTextViewInspectionType.setText(inspectionType);
        mInspection = inspection;
    }

    public static UpcomingInspectionsViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inspection, parent, false);
        return new UpcomingInspectionsViewHolder(view);
    }
}
