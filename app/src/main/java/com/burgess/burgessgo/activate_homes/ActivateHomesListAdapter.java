package com.burgess.burgessgo.activate_homes;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.Objects;

import data.models.Home;

public class ActivateHomesListAdapter extends ListAdapter<Home, ActivateHomesViewHolder> {
    protected ActivateHomesListAdapter(@NonNull DiffUtil.ItemCallback<Home> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ActivateHomesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ActivateHomesViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivateHomesViewHolder holder, int position) {
        Home i = getItem(position);
        holder.bind(i.getCommunity(), i.getAddress(), i);
    }

    public static class InspectDiff extends DiffUtil.ItemCallback<Home> {
        @Override
        public boolean areItemsTheSame(@NonNull Home oldItem, @NonNull Home newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Home oldItem, @NonNull Home newItem) {
            return oldItem.getLocationId() == newItem.getLocationId() && Objects.equals(oldItem.getAddress(), newItem.getAddress()) && Objects.equals(oldItem.getCommunity(), newItem.getCommunity());
        }
    }
}
