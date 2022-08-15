package com.burgess.burgessgo.location_defects;

import static com.burgess.burgessgo.Constants.PREF;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.burgess.burgessgo.BaseActivity;
import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.ServerCallback;
import com.google.android.material.snackbar.Snackbar;

public class LocationDefectsActivity extends BaseActivity {
    private static final String TAG = "LOCATION_DEFECTS";
    public static final String INTENT_EXTRA = "LOCATION_ID";

    // View components
    private LocationDefectsViewModel mViewModel;
    private ConstraintLayout mConstraintLayout;
    private RecyclerView mRecyclerDefectList;

    // Class members
    private static GoAPIQueue apiQueue;
    private static GoLogger logger;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private LocationDefectsListAdapter mListAdapter;
    private int mLocationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_defects);
        setSupportActionBar(findViewById(R.id.location_defects_toolbar));
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(LocationDefectsViewModel.class);

        // Prepare logger and API queue
        logger = GoLogger.getInstance(this);
        apiQueue = GoAPIQueue.getInstance(this);

        // Prepare shared preferences
        mSharedPreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        // Get intent
        Intent intent = getIntent();
        mLocationId = intent.getIntExtra(INTENT_EXTRA, -1);

        initializeViews();
        initializeDisplayContent();
    }

    public void initializeViews() {
        mConstraintLayout = findViewById(R.id.location_defects_constraint_layout);
        mRecyclerDefectList = findViewById(R.id.location_defects_recycler_defect_list);
    }

    public void initializeDisplayContent() {
        mRecyclerDefectList.setLayoutManager(new LinearLayoutManager(this));
        updateDefectList();
    }

    public void updateDefectList() {
        mViewModel.clearInspectionDefectList();
        apiQueue.getRequestQueue().add(apiQueue.getDefectsAtLocation(mViewModel, mLocationId, new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                mListAdapter = new LocationDefectsListAdapter(mViewModel.getInspectionDefectList());
                mRecyclerDefectList.setAdapter(mListAdapter);
                mListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_SHORT).show();
            }
        }));
    }
}