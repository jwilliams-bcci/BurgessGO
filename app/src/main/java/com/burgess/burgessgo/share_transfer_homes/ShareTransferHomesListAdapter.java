package com.burgess.burgessgo.share_transfer_homes;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.ServerCallback;
import com.burgess.burgessgo.upcoming_inspections.UpcomingInspectionsActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import data.models.BuilderPersonnel;
import data.models.Home;

public class ShareTransferHomesListAdapter extends RecyclerView.Adapter<ShareTransferHomesViewHolder> {
    private final String TAG = "SHARE_TRANSFER_HOMES_LIST_ADAPTER";

    private List<Home> homeList;
    private List<BuilderPersonnel> personnelList;
    private int[] selectedItems;
    private GoAPIQueue queue;
    private ShareTransferHomesViewModel vm;
    private int builderId;
    private int builderPersonnelId;

    public ShareTransferHomesListAdapter(GoAPIQueue queue, ShareTransferHomesViewModel vm, int builderId, int builderPersonnelId) {
        homeList = new ArrayList<>();
        personnelList = new ArrayList<>();
        this.queue = queue;
        this.vm = vm;
        this.builderId = builderId;
        this.builderPersonnelId = builderPersonnelId;
    }

    public ShareTransferHomesListAdapter(List<Home> list, GoAPIQueue queue, ShareTransferHomesViewModel vm, int builderId, int builderPersonnelId) {
        homeList = list;
        this.queue = queue;
        this.vm = vm;
        this.builderId = builderId;
        this.builderPersonnelId = builderPersonnelId;
        selectedItems = new int[homeList.size()];
        for (int lcv = 0; lcv < selectedItems.length; lcv++) {
            selectedItems[lcv] = 0;
        }
    }

    @NonNull
    @Override
    public ShareTransferHomesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share_transfer_home, parent, false);
        return new ShareTransferHomesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShareTransferHomesViewHolder holder, int position) {
        Home i = homeList.get(position);

        if (i.getMAXBuilderPersonnelIDRequestingAccess() > 0) {
            holder.getConstraintLayoutUpper().setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorLightRed));
        }

        ArrayAdapter<BuilderPersonnel> spinnerAdapter = new ArrayAdapter<>(holder.itemView.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, i.getBuilderPersonnelList());
        holder.getSpinnerBuilderPersonnel().setAdapter(spinnerAdapter);

        holder.itemView.setOnClickListener(v -> {
            setSelectedItem(position);
            notifyDataSetChanged();
        });

        if (position == getSelectedItem()) {
            holder.getConstraintLayoutLower().setVisibility(View.VISIBLE);
            holder.getImageViewArrow().setRotation(180);
            if (i.getMAXBuilderPersonnelIDRequestingAccess() > 0) {
                holder.getButtonRejectShareRequest().setVisibility(View.VISIBLE);
            } else {
                holder.getButtonRejectShareRequest().setVisibility(View.GONE);
            }
        } else {
            holder.getConstraintLayoutLower().setVisibility(View.GONE);
            holder.getImageViewArrow().setRotation(90);
        }
        holder.getTextViewCommunity().setText(i.getCommunity());
        holder.getTextViewAddress().setText(i.getAddress());

        holder.getButtonShare().setOnClickListener(v -> {
            sendRequest("BurgessGoAssignmentAdd", ((BuilderPersonnel) holder.getSpinnerBuilderPersonnel().getSelectedItem()).getBuilderPersonnelId(), i.getLocationId(), holder.itemView.getContext());
        });
        holder.getButtonTransfer().setOnClickListener(v -> {
            sendRequest("BurgessGoAssignmentMove", ((BuilderPersonnel) holder.getSpinnerBuilderPersonnel().getSelectedItem()).getBuilderPersonnelId(), i.getLocationId(), holder.itemView.getContext());
        });
        holder.getButtonRejectShareRequest().setOnClickListener(v -> {
            sendRequest("BurgessGoAssignmentDeny", ((BuilderPersonnel) holder.getSpinnerBuilderPersonnel().getSelectedItem()).getBuilderPersonnelId(), i.getLocationId(), holder.itemView.getContext());
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

    private void sendRequest(String action, int builderPersonnelIdNew, int locationId, Context ctx) {
        queue.getRequestQueue().add(queue.postShareTransferHomes(action, builderPersonnelIdNew, locationId, builderPersonnelId, new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                Intent intent = new Intent(ctx, UpcomingInspectionsActivity.class);
                ctx.startActivity(intent);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(ctx, "Failed! " + message, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    //region GETTERS AND SETTERS
    public List<Home> getHomeList() {
        return homeList;
    }

    public void setHomeList(List<Home> homeList) {
        this.homeList = homeList;
        selectedItems = new int[homeList.size()];
        for (int lcv = 0; lcv < selectedItems.length; lcv++) {
            selectedItems[lcv] = 0;
        }
    }

    public List<BuilderPersonnel> getPersonnelList() {
        return personnelList;
    }

    public void setPersonnelList(List<BuilderPersonnel> personnelList) {
        this.personnelList = personnelList;
    }
    //endregion
}
