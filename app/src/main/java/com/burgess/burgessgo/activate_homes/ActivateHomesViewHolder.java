package com.burgess.burgessgo.activate_homes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;

import data.models.Home;

public class ActivateHomesViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTextViewCommunity;
    private final TextView mTextViewAddress;
    private final CheckBox mCheckBoxActivate;
    private Home mInactiveHome;

    public ActivateHomesViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextViewCommunity = itemView.findViewById(R.id.item_home_textView_community);
        mTextViewAddress = itemView.findViewById(R.id.item_home_textView_address);
        mCheckBoxActivate = itemView.findViewById(R.id.item_home_checkBox_activated);
    }

    public void bind(String community, String address, Home inactiveHome) {
        mTextViewCommunity.setText(community);
        mTextViewAddress.setText(address);
        mInactiveHome = inactiveHome;
    }

    public static ActivateHomesViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new ActivateHomesViewHolder(view);
    }
}
