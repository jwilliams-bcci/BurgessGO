package com.burgess.burgessgo.inspection_defects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;

import java.util.ArrayList;
import java.util.List;

import data.models.InspectionDefect;

public class InspectionDefectsListAdapter extends RecyclerView.Adapter<InspectionDefectsViewHolder> {
    private List<InspectionDefect> mInspectionDefectList;
    private String mCurrentSectionHeader;

    public InspectionDefectsListAdapter() {
        mInspectionDefectList = new ArrayList<>();
        mCurrentSectionHeader = "";
    }

    public InspectionDefectsListAdapter(List<InspectionDefect> inspectionDefectList) {
        mInspectionDefectList = inspectionDefectList;
        mCurrentSectionHeader = "";
    }

    @NonNull
    @Override
    public InspectionDefectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inspection_defect, parent, false);
        return new InspectionDefectsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InspectionDefectsViewHolder holder, int position) {
        InspectionDefect i = mInspectionDefectList.get(position);

        if (mCurrentSectionHeader.compareTo(i.getDefectCategoryDisplayName()) == 0) {
            holder.getConstraintLayoutUpper().setVisibility(View.GONE);
        } else {
            holder.getConstraintLayoutUpper().setVisibility(View.VISIBLE);
            mCurrentSectionHeader = i.getDefectCategoryDisplayName();
        }

        holder.getTextViewCategory().setText(i.getDefectCategoryDisplayName());
        holder.getTextViewColumnHeader1().setText(i.getColumnHeader1());
        holder.getTextViewColumnHeader2().setText(i.getColumnHeader2());
        holder.getTextViewItemDescription().setText(i.getDefectItemDescription());
        holder.getTextViewItemText().setText(i.getDeviationText());
    }

    @Override
    public int getItemCount() {
        return mInspectionDefectList.size();
    }

    //region GETTERS AND SETTERS
    public List<InspectionDefect> getInspectionDefectList() {
        return mInspectionDefectList;
    }

    public void setInspectionDefectList(List<InspectionDefect> inspectionDefectList) {
        mInspectionDefectList = inspectionDefectList;
    }

    public String getCurrentSectionHeader() {
        return mCurrentSectionHeader;
    }

    public void setCurrentSectionHeader(String currentSectionHeader) {
        mCurrentSectionHeader = currentSectionHeader;
    }
//endregion
}
