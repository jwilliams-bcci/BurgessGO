package com.burgess.burgessgo.deactivate_homes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.ServerCallback;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import data.models.Home;

public class DeactivateHomesListAdapter extends RecyclerView.Adapter<DeactivateHomesViewHolder> {
    private List<Home> mHomeList;
    private int[] selectedItems;
    private GoAPIQueue apiQueue;
    private int mBuilderPersonnelId;

    public DeactivateHomesListAdapter(GoAPIQueue queue, int bpid) {
        mHomeList = new ArrayList<>();
        selectedItems = new int[0];
        apiQueue = queue;
        mBuilderPersonnelId = bpid;
    }

    public DeactivateHomesListAdapter(List<Home> list) {
        mHomeList = list;
        selectedItems = new int[mHomeList.size()];
        for (int lcv = 0; lcv < selectedItems.length; lcv++) {
            selectedItems[lcv] = 0;
        }
    }

    @NonNull
    @Override
    public DeactivateHomesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deactivate_home, parent, false);
        return new DeactivateHomesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeactivateHomesViewHolder holder, int position) {
        Home i = mHomeList.get(position);

        holder.itemView.setOnClickListener(v -> {
            setSelectedItem(position);
            notifyDataSetChanged();
        });

        if (position == getSelectedItem()) {
            holder.getConstraintLayoutLower().setVisibility(View.VISIBLE);
            holder.getImageViewArrow().setRotation(180);
        } else {
            holder.getConstraintLayoutLower().setVisibility(View.GONE);
            holder.getImageViewArrow().setRotation(90);
        }

        holder.getButtonYes().setOnClickListener(v -> {
            apiQueue.getRequestQueue().add(apiQueue.postDeactivateHomes(i.getLocationId(), mBuilderPersonnelId, new ServerCallback() {
                @Override
                public void onSuccess(String message) {
                    setSelectedItem(-1);
                    mHomeList.remove(i);
                    notifyDataSetChanged();
                    Snackbar.make(holder.getConstraintLayout(), "Deactivated home successfully", Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String message) {
                    Snackbar.make(holder.getConstraintLayout(), message, Snackbar.LENGTH_SHORT).show();
                }
            }));
        });

        holder.getButtonNo().setOnClickListener(v -> {
            holder.getConstraintLayoutLower().setVisibility(View.GONE);
            holder.getImageViewArrow().setRotation(90);
            setSelectedItem(-1);
        });

        holder.getTextViewCommunity().setText(i.getCommunity());
        holder.getTextViewAddressUpper().setText(i.getAddress());
        holder.getTextViewAddressLower().setText(i.getAddress());
    }

    @Override
    public int getItemCount() {
        return mHomeList.size();
    }

    private void setSelectedItem(int position) {
        for (int lcv = 0; lcv < selectedItems.length; lcv++) {
            if (lcv == position && selectedItems[lcv] != 1) {
                selectedItems[lcv] = 1;
            } else {
                selectedItems[lcv] = 0;
            }
        }
    }

    private int getSelectedItem() {
        for (int lcv = 0; lcv < selectedItems.length; lcv++) {
            if (selectedItems[lcv] == 1) {
                return lcv;
            }
        }
        return -1;
    }

    //region GETTERS AND SETTERS
    public List<Home> getHomeList() {
        return mHomeList;
    }

    public void setHomeList(List<Home> mHomeList) {
        this.mHomeList = mHomeList;
        selectedItems = new int[mHomeList.size()];
        for (int lcv = 0; lcv < selectedItems.length; lcv++) {
            selectedItems[lcv] = 0;
        }
    }

    public int[] getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(int[] selectedItems) {
        this.selectedItems = selectedItems;
    }
    //endregion
}
