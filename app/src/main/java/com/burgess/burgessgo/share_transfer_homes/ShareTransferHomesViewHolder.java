package com.burgess.burgessgo.share_transfer_homes;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;

import java.util.List;

import data.models.BuilderPersonnel;
import data.models.Home;

public class ShareTransferHomesViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTextViewCommunity;
    private final TextView mTextViewAddress;
    private final ImageView mImageViewArrow;
    private final Spinner mSpinnerBuilderPersonnel;
    private final Button mButtonShare;
    private final Button mButtonTransfer;
    private ArrayAdapter<BuilderPersonnel> mSpinnerBuilderPersonnelAdapter;

    public ShareTransferHomesViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextViewCommunity = itemView.findViewById(R.id.item_share_transfer_home_textView_community);
        mTextViewAddress = itemView.findViewById(R.id.item_share_transfer_home_textView_address);
        mImageViewArrow = itemView.findViewById(R.id.item_share_transfer_home_imageView_arrow);
        mSpinnerBuilderPersonnel = itemView.findViewById(R.id.item_share_transfer_home_spinner_builder_personnel);
        mButtonShare = itemView.findViewById(R.id.item_share_transfer_home_button_share);
        mButtonTransfer = itemView.findViewById(R.id.item_share_transfer_home_button_transfer);
    }

    public void bind(Home home) {
        mTextViewCommunity.setText(home.getCommunity());
        mTextViewAddress.setText(home.getAddress());

        mSpinnerBuilderPersonnelAdapter = new ArrayAdapter<>(mTextViewAddress.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, home.getBuilderPersonnelList());
        mSpinnerBuilderPersonnel.setAdapter(mSpinnerBuilderPersonnelAdapter);
        mSpinnerBuilderPersonnel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BuilderPersonnel builderPersonnel = (BuilderPersonnel) adapterView.getSelectedItem();
                Toast.makeText(mTextViewAddress.getContext(), "Clicked " + builderPersonnel.getPersonnelName() + ", ID: " + builderPersonnel.getBuilderPersonnelId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mButtonShare.setOnClickListener(v -> {
            Toast.makeText(mTextViewAddress.getContext(), "Clicked Share for " + home.getLocationId(), Toast.LENGTH_SHORT).show();
        });
        mButtonTransfer.setOnClickListener(v -> {
            Toast.makeText(mTextViewAddress.getContext(), "Clicked Transfer for " + home.getLocationId(), Toast.LENGTH_SHORT).show();
        });
    }

    public static ShareTransferHomesViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share_transfer_home, parent, false);
        return new ShareTransferHomesViewHolder(view);
    }
}
