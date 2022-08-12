package com.burgess.burgessgo.my_homes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import data.models.ActiveLocation;
import data.models.Home;
import data.models.Inspection;

public class MyHomesListAdapter extends RecyclerView.Adapter<MyHomesViewHolder> {
    private List<ActiveLocation> activeLocationList;
    private List<Inspection> inspectionList;
    private int[] selectedItems;
    private GoAPIQueue queue;
    private MyHomesViewModel vm;

    public MyHomesListAdapter() {}

    public MyHomesListAdapter(List<ActiveLocation> homeList, GoAPIQueue queue, MyHomesViewModel vm) {
        this.activeLocationList = homeList;
        this.queue = queue;
        this.vm = vm;
        selectedItems = new int[homeList.size()];
        for (int lcv = 0; lcv < selectedItems.length; lcv++) {
            selectedItems[lcv] = 0;
        }
    }

    @NonNull
    @Override
    public MyHomesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_home, parent, false);
        return new MyHomesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHomesViewHolder holder, int position) {
        ActiveLocation i = activeLocationList.get(position);

        holder.itemView.setOnClickListener(v -> {
            setSelectedItem(position);
            // TODO: load inspectionlist tableview
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
        holder.getTextViewAddress().setText(i.getAddress());
        holder.getButtonScheduleNewInspection().setOnClickListener(v -> {
            Snackbar.make(holder.getConstraintLayoutLower(), "Clicked Schedule New Inspection for " + i.getLocationId(), Snackbar.LENGTH_SHORT).show();
        });
        holder.getButtonOpenDefects().setOnClickListener(v -> {
            Snackbar.make(holder.getConstraintLayoutLower(), "Clicked Open Defects for " + i.getLocationId(), Snackbar.LENGTH_SHORT).show();
        });

        //TODO: down here
        TableRow tr = new TableRow(holder.itemView.getContext());
        View view = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.item_inspection_table_row, tr, false);
        TextView txt = (TextView) view.findViewById(R.id.item_inspection_table_row_date);
        txt.setText("1/1/2050");

        TableRow tr2 = new TableRow(holder.itemView.getContext());
        View view2 = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.item_inspection_table_row, tr2, false);
        TextView txt2 = (TextView) view2.findViewById(R.id.item_inspection_table_row_date);
        txt2.setText("1/2/2050");

        TableRow tr3 = new TableRow(holder.itemView.getContext());
        View view3 = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.item_inspection_table_row, tr3, false);
        TextView txt3 = (TextView) view3.findViewById(R.id.item_inspection_table_row_date);
        txt3.setText("1/3/2050");

        holder.getTableLayout().addView(view);
        holder.getTableLayout().addView(view2);
        holder.getTableLayout().addView(view3);
    }

    @Override
    public int getItemCount() {
        return activeLocationList.size();
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
