package com.burgess.burgessgo.inspection_defects;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import data.models.InspectionDefect;

public class InspectionDefectsListAdapter extends ListAdapter<InspectionDefect, InspectionDefectsViewHolder> {
    protected InspectionDefectsListAdapter(@NonNull DiffUtil.ItemCallback<InspectionDefect> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public InspectionDefectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return InspectionDefectsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull InspectionDefectsViewHolder holder, int position) {
        InspectionDefect i = getItem(position);
        holder.bind(i.getDefectCategoryDisplayName(), i.getColumnHeader1(), i.getColumnHeader2(), i.getDefectItemDescription(), i.getDeviationText(), i);
    }

    public static class InspectDiff extends DiffUtil.ItemCallback<InspectionDefect> {
        @Override
        public boolean areItemsTheSame(@NonNull InspectionDefect oldItem, @NonNull InspectionDefect newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull InspectionDefect oldItem, @NonNull InspectionDefect newItem) {
            return oldItem.getRowId() == newItem.getRowId();
        }
    }
}
