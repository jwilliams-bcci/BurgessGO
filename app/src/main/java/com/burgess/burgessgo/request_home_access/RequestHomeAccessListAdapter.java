package com.burgess.burgessgo.request_home_access;

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

import data.models.Home;

public class RequestHomeAccessListAdapter extends RecyclerView.Adapter<RequestHomeAccessViewHolder> {
    private List<Home> mHomeList;
    private int[] selectedItems;
    private GoAPIQueue apiQueue;
    private int builderPersonnelId;

    public RequestHomeAccessListAdapter(GoAPIQueue queue, int bpid) {
        mHomeList = new ArrayList<>();
        apiQueue = queue;
        builderPersonnelId = bpid;
    }

    public RequestHomeAccessListAdapter(List<Home> list) {
        mHomeList = list;
        selectedItems = new int[mHomeList.size()];
        for (int lcv = 0; lcv < selectedItems.length; lcv++) {
            selectedItems[lcv] = 0;
        }
    }

    @NonNull
    @Override
    public RequestHomeAccessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request_home_access, parent, false);
        return new RequestHomeAccessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestHomeAccessViewHolder holder, int position) {
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
        holder.getTextViewCommunity().setText(i.getCommunity());
        holder.getTextViewAddressUpper().setText(i.getAddress());
        holder.getTextViewAddressLower().setText(i.getAddress());
        holder.getButtonYes().setOnClickListener(v -> {
            apiQueue.getRequestQueue().add(apiQueue.postRequestHomeAccess(builderPersonnelId, i.getLocationId(), new ServerCallback() {
                @Override
                public void onSuccess(String message) {
                    Snackbar.make(holder.getConstraintLayoutUpper(), "Successfully requested home access!", Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String message) {
                    Snackbar.make(holder.getConstraintLayoutUpper(), message, Snackbar.LENGTH_SHORT).show();
                }
            }));
        });
        holder.getButtonNo().setOnClickListener(v -> {
            holder.getConstraintLayoutLower().setVisibility(View.GONE);
            holder.getImageViewArrow().setRotation(90);
            setSelectedItem(-1);
        });
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

    public void setHomeList(List<Home> homeList) {
        this.mHomeList = homeList;
        selectedItems = new int[homeList.size()];
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

    public GoAPIQueue getApiQueue() {
        return apiQueue;
    }

    public void setApiQueue(GoAPIQueue apiQueue) {
        this.apiQueue = apiQueue;
    }

    //endregion
}
