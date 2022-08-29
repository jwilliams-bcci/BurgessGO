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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.burgess.burgessgo.BaseActivity;
import com.burgess.burgessgo.Constants;
import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.ServerCallback;
import com.google.android.material.snackbar.Snackbar;

import data.models.Inspection;

public class UpcomingInspectionsActivity extends BaseActivity {
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
    private LinearLayoutManager mLayoutManager;
    private DividerItemDecoration mDividerItemDecoration;

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

    public void initializeViews() {
        mConstraintLayout = findViewById(R.id.upcoming_inspections_contraint_layout);
        mSwipeRefreshLayout = findViewById(R.id.upcoming_inspections_swipe_refresh);
        mRecyclerInspectionList = findViewById(R.id.upcoming_inspections_recycler_inspections);
    }

    public void initializeDisplayContent() {
        mSwipeRefreshLayout.setOnRefreshListener(this::updateInspectionList);
        mListAdapter = new UpcomingInspectionsListAdapter();
        mLayoutManager = new LinearLayoutManager(this);
        mDividerItemDecoration = new DividerItemDecoration(this, mLayoutManager.getOrientation());
        mRecyclerInspectionList.addItemDecoration(mDividerItemDecoration);
        mRecyclerInspectionList.setLayoutManager(mLayoutManager);
        mRecyclerInspectionList.setAdapter(mListAdapter);
        updateInspectionList();
    }

    public void updateInspectionList() {
        mViewModel.clearInspectionList();
        apiQueue.getRequestQueue().add(apiQueue.getUpcomingInspections(mViewModel, mSharedPreferences.getInt(PREF_BUILDER_PERSONNEL_ID, -1), new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                Snackbar.make(mConstraintLayout, "Inspections updated", Snackbar.LENGTH_SHORT).show();
                mListAdapter.setInspectionList(mViewModel.getInspectionList());
                mListAdapter.setCurrentSectionHeader("");
                mListAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_LONG).show();
            }
        }));
    }
}