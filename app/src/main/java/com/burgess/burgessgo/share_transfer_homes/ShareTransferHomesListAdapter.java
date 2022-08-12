package com.burgess.burgessgo.share_transfer_homes;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import data.models.Home;

public class ShareTransferHomesListAdapter extends ListAdapter<Home, ShareTransferHomesViewHolder> {
    protected ShareTransferHomesListAdapter(@NonNull DiffUtil.ItemCallback<Home> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ShareTransferHomesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ShareTransferHomesViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ShareTransferHomesViewHolder holder, int position) {
        Home i = getItem(position);
        holder.bind(i);
    }

    public static class InspectDiff extends DiffUtil.ItemCallback<Home> {
        @Override
        public boolean areItemsTheSame(@NonNull Home oldItem, @NonNull Home newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Home oldItem, @NonNull Home newItem) {
            return oldItem.getLocationId() == newItem.getLocationId();
        }
    }
}
