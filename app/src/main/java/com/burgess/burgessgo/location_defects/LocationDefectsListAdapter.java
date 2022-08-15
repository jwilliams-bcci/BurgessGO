package com.burgess.burgessgo.location_defects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burgess.burgessgo.R;

import java.util.List;

import data.models.InspectionDefect;

public class LocationDefectsListAdapter extends RecyclerView.Adapter<LocationDefectsViewHolder> {
    private List<InspectionDefect> defectList;

    public LocationDefectsListAdapter(){}

    public LocationDefectsListAdapter(List<InspectionDefect> list) {
        defectList = list;
    }

    @NonNull
    @Override
    public LocationDefectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inspection_defect, parent, false);
        return new LocationDefectsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationDefectsViewHolder holder, int position) {
        InspectionDefect i = defectList.get(position);

        holder.getTextViewCategory().setText(i.getDefectCategoryDisplayName());
        holder.getTextViewColumnHeader1().setText(i.getColumnHeader1());
        holder.getTextViewColumnHeader2().setText(i.getColumnHeader2());
        holder.getTextViewItemDescription().setText(i.getDefectItemDescription());
        holder.getTextViewItemText().setText(i.getDeviationText());
    }

    @Override
    public int getItemCount() {
        return defectList.size();
    }
}
