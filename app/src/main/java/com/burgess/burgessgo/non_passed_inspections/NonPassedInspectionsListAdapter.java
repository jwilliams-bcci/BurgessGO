package com.burgess.burgessgo.non_passed_inspections;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import data.models.NonPassedInspection;

public class NonPassedInspectionsListAdapter extends ListAdapter<NonPassedInspection, NonPassedInspectionsViewHolder> {
    protected NonPassedInspectionsListAdapter(@NonNull DiffUtil.ItemCallback<NonPassedInspection> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public NonPassedInspectionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return NonPassedInspectionsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull NonPassedInspectionsViewHolder holder, int position) {
        NonPassedInspection i = getItem(position);
        holder.bind(i.getCommunityName(), i.getInspectionAddress(), i.getTypeName(), i);
    }

    public static class InspectDiff extends DiffUtil.ItemCallback<NonPassedInspection> {

        @Override
        public boolean areItemsTheSame(@NonNull NonPassedInspection oldItem, @NonNull NonPassedInspection newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull NonPassedInspection oldItem, @NonNull NonPassedInspection newItem) {
            return oldItem.getInspectionId() == newItem.getInspectionId();
        }
    }
}
