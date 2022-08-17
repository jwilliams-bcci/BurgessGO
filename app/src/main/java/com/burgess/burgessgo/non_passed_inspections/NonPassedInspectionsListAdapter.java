package com.burgess.burgessgo.non_passed_inspections;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;
import com.burgess.burgessgo.non_passed_inspection_details.NonPassedInspectionDetailsActivity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import data.models.NonPassedInspection;

public class NonPassedInspectionsListAdapter extends RecyclerView.Adapter<NonPassedInspectionsViewHolder> {
    private List<NonPassedInspection> mNonPassedInspectionList;
    private String mCurrentSectionHeader;

    public NonPassedInspectionsListAdapter() {
        mNonPassedInspectionList = new ArrayList<>();
        mCurrentSectionHeader = "";
    }

    public NonPassedInspectionsListAdapter(List<NonPassedInspection> nonPassedInspectionList) {
        mNonPassedInspectionList = nonPassedInspectionList;
        mCurrentSectionHeader = "";
    }

    @NonNull
    @Override
    public NonPassedInspectionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inspection, parent, false);
        return new NonPassedInspectionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NonPassedInspectionsViewHolder holder, int position) {
        NonPassedInspection i = mNonPassedInspectionList.get(position);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, LLLL dd");
        String dateDisplay = i.getInspectionDate().toLocalDate().format(formatter);

        if (mCurrentSectionHeader.compareTo(dateDisplay) == 0) {
            holder.getConstraintLayoutUpper().setVisibility(View.GONE);
        } else {
            holder.getConstraintLayoutUpper().setVisibility(View.VISIBLE);
            mCurrentSectionHeader = dateDisplay;
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NonPassedInspectionDetailsActivity.class);
            intent.putExtra(NonPassedInspectionDetailsActivity.INTENT_EXTRA, i);
            v.getContext().startActivity(intent);
        });

        holder.getTextViewDate().setText(dateDisplay);
        holder.getTextViewCommunity().setText(i.getCommunityName());
        holder.getTextViewAddress().setText(i.getInspectionAddress());
        holder.getTextViewInspectionType().setText(i.getTypeName());
    }

    @Override
    public int getItemCount() {
        return mNonPassedInspectionList.size();
    }

    //region GETTERS AND SETTERS
    public List<NonPassedInspection> getNonPassedInspectionList() {
        return mNonPassedInspectionList;
    }

    public void setNonPassedInspectionList(List<NonPassedInspection> nonPassedInspectionList) {
        mNonPassedInspectionList = nonPassedInspectionList;
    }

    public String getCurrentSectionHeader() {
        return mCurrentSectionHeader;
    }

    public void setCurrentSectionHeader(String currentSectionHeader) {
        mCurrentSectionHeader = currentSectionHeader;
    }
    //endregion
}
