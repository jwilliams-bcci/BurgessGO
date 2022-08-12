package com.burgess.burgessgo.deactivate_homes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;

import data.models.Home;

public class DeactivateHomesViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTextViewCommunity;
    private final TextView mTextViewAddress;
    private final CheckBox mCheckBoxDeactivate;
    private Home mActiveHome;

    public DeactivateHomesViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextViewCommunity = itemView.findViewById(R.id.item_home_textView_community);
        mTextViewAddress = itemView.findViewById(R.id.item_home_textView_address);
        mCheckBoxDeactivate = itemView.findViewById(R.id.item_home_checkBox_activated);
    }

    public void bind(String community, String address, Home activeHome) {
        mTextViewCommunity.setText(community);
        mTextViewAddress.setText(address);
        mActiveHome = activeHome;
        mCheckBoxDeactivate.setChecked(true);
    }

    public static DeactivateHomesViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new DeactivateHomesViewHolder(view);
    }
}
