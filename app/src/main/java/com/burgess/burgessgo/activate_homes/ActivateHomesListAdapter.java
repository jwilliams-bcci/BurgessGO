package com.burgess.burgessgo.activate_homes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;

import java.util.ArrayList;
import java.util.List;

import data.models.Home;

public class ActivateHomesListAdapter extends RecyclerView.Adapter<ActivateHomesViewHolder> {
    private List<Home> mHomeList;

    public ActivateHomesListAdapter() {
        mHomeList = new ArrayList<>();
    }

    public ActivateHomesListAdapter(List<Home> list) {
        mHomeList = list;
    }

    @NonNull
    @Override
    public ActivateHomesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activate_home, parent, false);
        return new ActivateHomesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivateHomesViewHolder holder, int position) {
        Home i = mHomeList.get(position);

        holder.getCheckBoxActivate().setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                i.setSelected(true);
            } else {
                i.setSelected(false);
            }
        });

        holder.getTextViewCommunity().setText(i.getCommunity());
        holder.getTextViewAddress().setText(i.getAddress());
    }

    @Override
    public int getItemCount() {
        return mHomeList.size();
    }

    //region GETTERS AND SETTERS
    public List<Home> getHomeList() {
        return mHomeList;
    }

    public void setHomeList(List<Home> mHomeList) {
        this.mHomeList = mHomeList;
    }
    //endregion
}
