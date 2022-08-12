package com.burgess.burgessgo.deactivate_homes;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.Objects;

import data.models.Home;

public class DeactivateHomesListAdapter extends ListAdapter<Home, DeactivateHomesViewHolder> {
    protected DeactivateHomesListAdapter(@NonNull DiffUtil.ItemCallback<Home> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public DeactivateHomesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return DeactivateHomesViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DeactivateHomesViewHolder holder, int position) {
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
