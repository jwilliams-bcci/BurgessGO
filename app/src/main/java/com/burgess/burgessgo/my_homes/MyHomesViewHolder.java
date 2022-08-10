package com.burgess.burgessgo.my_homes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;
import com.google.android.material.snackbar.Snackbar;

import data.models.ActiveLocation;

public class MyHomesViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTextViewCommunity;
    private final TextView mTextViewAddress;
    private ActiveLocation mActiveLocation;

    public MyHomesViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextViewCommunity = itemView.findViewById(R.id.item_my_home_textView_community);
        mTextViewAddress = itemView.findViewById(R.id.item_my_home_textView_address);

        itemView.setOnClickListener(v -> {
            Toast.makeText(itemView.getContext(), "Clicked location " + mActiveLocation.getLocationId(), Toast.LENGTH_SHORT).show();
        });
    }

    public void bind(String community, String address, ActiveLocation activeLocation) {
        mTextViewCommunity.setText(community);
        mTextViewAddress.setText(address);
        mActiveLocation = activeLocation;
    }

    public static MyHomesViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_home, parent, false);
        return new MyHomesViewHolder(view);
    }
}
