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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;

import java.util.List;

import data.models.BuilderPersonnel;
import data.models.Home;

public class ShareTransferHomesViewHolder extends RecyclerView.ViewHolder {
    private final ConstraintLayout mConstraintLayoutUpper;
    private final ConstraintLayout mConstraintLayoutLower;
    private final TextView mTextViewCommunity;
    private final TextView mTextViewAddress;
    private final ImageView mImageViewArrow;
    private final Spinner mSpinnerBuilderPersonnel;
    private final Button mButtonShare;
    private final Button mButtonTransfer;
    private final Button mButtonRejectShareRequest;

    public ShareTransferHomesViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextViewCommunity = itemView.findViewById(R.id.item_share_transfer_home_textView_community);
        mTextViewAddress = itemView.findViewById(R.id.item_share_transfer_home_textView_address);
        mImageViewArrow = itemView.findViewById(R.id.item_share_transfer_home_imageView_arrow);
        mSpinnerBuilderPersonnel = itemView.findViewById(R.id.item_share_transfer_home_spinner_builder_personnel);
        mButtonShare = itemView.findViewById(R.id.item_share_transfer_home_button_share);
        mButtonTransfer = itemView.findViewById(R.id.item_share_transfer_home_button_transfer);
        mButtonRejectShareRequest = itemView.findViewById(R.id.item_share_transfer_home_button_reject_share_request);
        mConstraintLayoutUpper = itemView.findViewById(R.id.item_share_transfer_home_constraint_layout_upper);
        mConstraintLayoutLower = itemView.findViewById(R.id.item_share_transfer_home_constraint_layout_lower);
    }

    //region GETTERS
    public TextView getTextViewCommunity() {
        return mTextViewCommunity;
    }

    public TextView getTextViewAddress() {
        return mTextViewAddress;
    }

    public ConstraintLayout getConstraintLayoutUpper() {
        return mConstraintLayoutUpper;
    }

    public ConstraintLayout getConstraintLayoutLower() {
        return mConstraintLayoutLower;
    }

    public Button getButtonShare() {
        return mButtonShare;
    }

    public Button getButtonTransfer() {
        return mButtonTransfer;
    }

    public Spinner getSpinnerBuilderPersonnel() {
        return mSpinnerBuilderPersonnel;
    }

    public ImageView getImageViewArrow() {
        return mImageViewArrow;
    }

    public Button getButtonRejectShareRequest() {
        return mButtonRejectShareRequest;
    }
//endregion
}
