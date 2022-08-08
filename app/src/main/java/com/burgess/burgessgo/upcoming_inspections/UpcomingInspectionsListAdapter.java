package com.burgess.burgessgo.upcoming_inspections;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import data.models.Inspection;

public class UpcomingInspectionsListAdapter extends ListAdapter<Inspection, UpcomingInspectionsViewHolder> {
    protected UpcomingInspectionsListAdapter(@NonNull DiffUtil.ItemCallback<Inspection> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public UpcomingInspectionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return UpcomingInspectionsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingInspectionsViewHolder holder, int position) {
        Inspection i = getItem(position);
        holder.bind(i.getCommunityName(), i.getAddressToDisplay(), i.getTypeName(), i);
    }

    public static class InspectDiff extends DiffUtil.ItemCallback<Inspection> {

        @Override
        public boolean areItemsTheSame(@NonNull Inspection oldItem, @NonNull Inspection newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Inspection oldItem, @NonNull Inspection newItem) {
            return oldItem.getInspectionId() == newItem.getInspectionId();
        }
    }
}
