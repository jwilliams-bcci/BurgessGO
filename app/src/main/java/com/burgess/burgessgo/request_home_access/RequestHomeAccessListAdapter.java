package com.burgess.burgessgo.request_home_access;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import data.models.Home;

public class RequestHomeAccessListAdapter extends RecyclerView.Adapter<RequestHomeAccessViewHolder> {
    private List<Home> homeList;
    private int[] selectedItems;

    public RequestHomeAccessListAdapter(){}

    public RequestHomeAccessListAdapter(List<Home> list) {
        homeList = list;
        selectedItems = new int[homeList.size()];
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
        Home i = (Home) homeList.get(position);

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
            Snackbar.make(holder.getTextViewAddressLower(), "Button YES clicked for " + i.getLocationId(), Snackbar.LENGTH_SHORT).show();
        });
        holder.getButtonNo().setOnClickListener(v -> {
            Snackbar.make(holder.getTextViewAddressLower(), "Button NO clicked for " + i.getLocationId(), Snackbar.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return homeList.size();
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
}
