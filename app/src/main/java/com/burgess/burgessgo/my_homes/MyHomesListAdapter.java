package com.burgess.burgessgo.my_homes;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.ServerCallback;
import com.burgess.burgessgo.location_defects.LocationDefectsActivity;
import com.burgess.burgessgo.schedule_inspection.ScheduleInspectionActivity;
import com.google.android.material.snackbar.Snackbar;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import data.models.Home;
import data.models.Inspection;

public class MyHomesListAdapter extends RecyclerView.Adapter<MyHomesViewHolder> {
    private List<Home> mHomeList;
    private List<Inspection> inspectionList;
    private int[] selectedItems;
    private GoAPIQueue queue;
    private MyHomesViewModel vm;

    public MyHomesListAdapter() {}

    public MyHomesListAdapter(List<Home> homeList, GoAPIQueue queue, MyHomesViewModel vm) {
        this.mHomeList = homeList;
        inspectionList = new ArrayList<>();
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
        Home i = mHomeList.get(position);

        holder.itemView.setOnClickListener(v -> {
            setSelectedItem(position);
            notifyDataSetChanged();
        });

        if (position == getSelectedItem()) {
            holder.getConstraintLayoutLower().setVisibility(View.VISIBLE);
            holder.getImageViewArrow().setRotation(180);
            inspectionList.clear();
            queue.getRequestQueue().add(queue.getInspectionsAtLocation(vm, i.getLocationId(), new ServerCallback() {
                @Override
                public void onSuccess(String message) {
                    inspectionList = vm.getInspectionList();
                    setUpInspectionTable(holder);
                }

                @Override
                public void onFailure(String message) {
                    Snackbar.make(holder.getConstraintLayoutLower(), message, Snackbar.LENGTH_SHORT).show();
                }
            }));
        } else {
            holder.getConstraintLayoutLower().setVisibility(View.GONE);
            holder.getImageViewArrow().setRotation(90);
        }
        holder.getTextViewCommunity().setText(i.getCommunity());
        holder.getTextViewAddress().setText(i.getAddress());
        holder.getButtonScheduleNewInspection().setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ScheduleInspectionActivity.class);
            intent.putExtra(ScheduleInspectionActivity.INTENT_EXTRA, i);
            holder.itemView.getContext().startActivity(intent);
        });
        holder.getButtonOpenDefects().setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), LocationDefectsActivity.class);
            intent.putExtra(LocationDefectsActivity.INTENT_EXTRA, i.getLocationId());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mHomeList.size();
    }

    public void setUpInspectionTable(MyHomesViewHolder holder) {
        if (holder.getTableLayout().getChildCount() > 1) {
            holder.getTableLayout().removeViews(1, holder.getTableLayout().getChildCount()-1);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        for (int lcv = 0; lcv < inspectionList.size(); lcv++) {
            Inspection i = inspectionList.get(lcv);
            TableRow tr = new TableRow(holder.itemView.getContext());
            View view = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.item_inspection_table_row, tr, false);

            if (lcv % 2 == 1) {
                view.setBackgroundColor(Color.LTGRAY);
            }

            TextView txtDate = view.findViewById(R.id.item_inspection_table_row_date);
            TextView txtType = view.findViewById(R.id.item_inspection_table_row_type);
            TextView txtStatus = view.findViewById(R.id.item_inspection_table_row_status);

            txtDate.setText(i.getInspectionDate().toLocalDate().format(formatter));
            txtType.setText(i.getTypeName());
            txtStatus.setText(i.getResolution());

            holder.getTableLayout().addView(view);
        }
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
