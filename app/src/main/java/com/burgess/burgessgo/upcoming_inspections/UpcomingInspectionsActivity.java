package com.burgess.burgessgo.upcoming_inspections;

import static com.burgess.burgessgo.Constants.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.burgess.burgessgo.Constants;
import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.ServerCallback;
import com.google.android.material.snackbar.Snackbar;

import data.models.Inspection;

public class UpcomingInspectionsActivity extends AppCompatActivity {
    private static final String TAG = "UPCOMING_INSPECTIONS";

    // View components
    private UpcomingInspectionsViewModel mViewModel;
    private ConstraintLayout mConstraintLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerInspectionList;

    // Class members
    private static GoAPIQueue apiQueue;
    private static GoLogger logger;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private UpcomingInspectionsListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_inspections);
        setSupportActionBar(findViewById(R.id.upcoming_inspections_toolbar));
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(UpcomingInspectionsViewModel.class);

        // Prepare logger and API queue
        logger = GoLogger.getInstance(this);
        apiQueue = GoAPIQueue.getInstance(this);

        // Prepare shared preferences...
        mSharedPreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        initializeViews();
        initializeDisplayContent();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    public void initializeViews() {
        mConstraintLayout = findViewById(R.id.upcoming_inspections_contraint_layout);
        mSwipeRefreshLayout = findViewById(R.id.upcoming_inspections_swipe_refresh);
        mRecyclerInspectionList = findViewById(R.id.upcoming_inspections_recycler_inspections);
    }

    public void initializeDisplayContent() {
        mSwipeRefreshLayout.setOnRefreshListener(this::updateInspectionList);
        mListAdapter = new UpcomingInspectionsListAdapter(new UpcomingInspectionsListAdapter.InspectDiff());
        mRecyclerInspectionList.setAdapter(mListAdapter);
        mRecyclerInspectionList.setLayoutManager(new LinearLayoutManager(this));
        updateInspectionList();
    }

    public void updateInspectionList() {
        mViewModel.clearInspectionList();
        apiQueue.getRequestQueue().add(apiQueue.getUpcomingInspections(mViewModel, mSharedPreferences.getInt(PREF_BUILDER_PERSONNEL_ID, -1), new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                Snackbar.make(mConstraintLayout, "Inspections updated", Snackbar.LENGTH_SHORT).show();
                mListAdapter.notifyDataSetChanged();
                mListAdapter.submitList(mViewModel.getInspectionList());
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(mConstraintLayout, "Error! " + message, Snackbar.LENGTH_LONG).show();
                apiQueue.getRequestQueue().stop();
            }
        }));
    }
}