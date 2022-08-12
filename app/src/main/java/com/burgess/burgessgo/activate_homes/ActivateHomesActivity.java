package com.burgess.burgessgo.activate_homes;

import static com.burgess.burgessgo.Constants.PREF;
import static com.burgess.burgessgo.Constants.PREF_BUILDER_PERSONNEL_ID;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.burgess.burgessgo.BaseActivity;
import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.ServerCallback;
import com.google.android.material.snackbar.Snackbar;

public class ActivateHomesActivity extends BaseActivity {
    private static final String TAG = "ACTIVATE_HOMES";

    // View components
    private ActivateHomesViewModel mViewModel;
    private ConstraintLayout mConstraintLayout;
    private RecyclerView mRecyclerInactiveHomeList;
    private Button mButtonSubmit;

    // Class members
    private static GoAPIQueue apiQueue;
    private static GoLogger logger;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private ActivateHomesListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_homes);
        setSupportActionBar(findViewById(R.id.activate_homes_toolbar));
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ActivateHomesViewModel.class);

        // Prepare logger and API queue
        logger = GoLogger.getInstance(this);
        apiQueue = GoAPIQueue.getInstance(this);

        // Prepare shared preferences
        mSharedPreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        initializeView();
        initializeButtons();
        initializeDisplayContent();
    }

    private void initializeView() {
        mConstraintLayout = findViewById(R.id.activate_homes_constraint_layout);
        mRecyclerInactiveHomeList = findViewById(R.id.deactivate_homes_recycler_active_home_list);
        mButtonSubmit = findViewById(R.id.activate_homes_button_submit);
    }

    private void initializeButtons() {
        mButtonSubmit.setOnClickListener(v -> {
            Snackbar.make(mConstraintLayout, "Submit button clicked", Snackbar.LENGTH_SHORT).show();
        });
    }

    private void initializeDisplayContent() {
        mListAdapter = new ActivateHomesListAdapter(new ActivateHomesListAdapter.InspectDiff());
        mRecyclerInactiveHomeList.setAdapter(mListAdapter);
        mRecyclerInactiveHomeList.setLayoutManager(new LinearLayoutManager(this));
        updateInactiveHomeList();
    }

    private void updateInactiveHomeList() {
        mViewModel.clearInactiveHomeList();
        apiQueue.getRequestQueue().add(apiQueue.getInactiveHomes(mViewModel, mSharedPreferences.getInt(PREF_BUILDER_PERSONNEL_ID, -1), new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                mListAdapter.notifyDataSetChanged();
                mListAdapter.submitList(mViewModel.getInactiveHomeList());
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_SHORT).show();
            }
        }));
    }
}