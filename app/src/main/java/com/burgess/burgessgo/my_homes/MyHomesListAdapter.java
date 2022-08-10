package com.burgess.burgessgo.my_homes;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import data.models.ActiveLocation;

public class MyHomesListAdapter extends ListAdapter<ActiveLocation, MyHomesViewHolder> {
    protected MyHomesListAdapter(@NonNull DiffUtil.ItemCallback<ActiveLocation> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MyHomesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MyHomesViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHomesViewHolder holder, int position) {
        ActiveLocation i = getItem(position);
        holder.bind(i.getCommunity(), i.getAddress(), i);
    }

    public static class InspectDiff extends DiffUtil.ItemCallback<ActiveLocation> {
        @Override
        public boolean areItemsTheSame(@NonNull ActiveLocation oldItem, @NonNull ActiveLocation newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ActiveLocation oldItem, @NonNull ActiveLocation newItem) {
            return oldItem.getLocationId() == newItem.getLocationId();
        }
    }
}
