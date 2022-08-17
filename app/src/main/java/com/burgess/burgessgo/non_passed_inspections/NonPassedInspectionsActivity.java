package com.burgess.burgessgo.non_passed_inspections;

import static com.burgess.burgessgo.Constants.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

import com.burgess.burgessgo.BaseActivity;
import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.ServerCallback;
import com.google.android.material.snackbar.Snackbar;

public class NonPassedInspectionsActivity extends BaseActivity {
    private static final String TAG = "NON_PASSED_INSPECTIONS";

    // View components
    private NonPassedInspectionsViewModel mViewModel;
    private ConstraintLayout mConstraintLayout;
    private RecyclerView mRecyclerInspectionList;

    // Class members
    private static GoAPIQueue apiQueue;
    private static GoLogger logger;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private NonPassedInspectionsListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_passed_inspections);
        setSupportActionBar(findViewById(R.id.non_passed_inspections_toolbar));
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(NonPassedInspectionsViewModel.class);

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
        mConstraintLayout = findViewById(R.id.non_passed_inspections_constraint_layout);
        mRecyclerInspectionList = findViewById(R.id.non_passed_inspections_recycler_inspections);
    }

    public void initializeDisplayContent() {
        mListAdapter = new NonPassedInspectionsListAdapter();
        mRecyclerInspectionList.setAdapter(mListAdapter);
        mRecyclerInspectionList.setLayoutManager(new LinearLayoutManager(this));
        updateInspectionList();
    }

    public void updateInspectionList() {
        mViewModel.clearInspectionList();
        apiQueue.getRequestQueue().add(apiQueue.getNonPassedInspections(mViewModel, mSharedPreferences.getInt(PREF_BUILDER_PERSONNEL_ID, -1), new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                mListAdapter.setNonPassedInspectionList(mViewModel.getInspectionList());
                mListAdapter.setCurrentSectionHeader("");
                mListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_LONG).show();
            }
        }));
    }
}