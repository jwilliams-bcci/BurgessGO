package com.burgess.burgessgo.upcoming_inspections;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;
import com.burgess.burgessgo.inspection_details.InspectionDetailsActivity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import data.models.Inspection;

public class UpcomingInspectionsListAdapter extends RecyclerView.Adapter<UpcomingInspectionsViewHolder> {
    private List<Inspection> mInspectionList;
    private String mCurrentSectionHeader;

    public UpcomingInspectionsListAdapter(){
        mInspectionList = new ArrayList<>();
        mCurrentSectionHeader = "";
    }

    public UpcomingInspectionsListAdapter(List<Inspection> inspectionList) {
        mInspectionList = inspectionList;
        mCurrentSectionHeader = "";
    }

    @NonNull
    @Override
    public UpcomingInspectionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inspection, parent, false);
        return new UpcomingInspectionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingInspectionsViewHolder holder, int position) {
        Inspection i = mInspectionList.get(position);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, LLLL dd");
        String dateDisplay = i.getInspectionDate().toLocalDate().format(formatter);

        if (mCurrentSectionHeader.compareTo(dateDisplay) == 0) {
            holder.getConstraintLayoutUpper().setVisibility(View.GONE);
        } else {
            holder.getConstraintLayoutUpper().setVisibility(View.VISIBLE);
            mCurrentSectionHeader = dateDisplay;
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), InspectionDetailsActivity.class);
            intent.putExtra(InspectionDetailsActivity.INTENT_EXTRA, i);
            v.getContext().startActivity(intent);
        });

        holder.getTextViewDate().setText(dateDisplay);
        holder.getTextViewCommunity().setText(i.getCommunityName());
        holder.getTextViewAddress().setText(i.getAddressToDisplay());
        holder.getTextViewInspectionType().setText(i.getTypeName());
    }

    @Override
    public int getItemCount() {
        return mInspectionList.size();
    }

    //region GETTERS AND SETTERS
    public List<Inspection> getInspectionList() {
        return mInspectionList;
    }

    public void setInspectionList(List<Inspection> inspectionList) {
        mInspectionList = inspectionList;
    }

    public String getCurrentSectionHeader() {
        return mCurrentSectionHeader;
    }

    public void setCurrentSectionHeader(String currentSectionHeader) {
        this.mCurrentSectionHeader = currentSectionHeader;
    }
//endregion
}
