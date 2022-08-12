package com.burgess.burgessgo.deactivate_homes;

import static com.burgess.burgessgo.Constants.PREF;
import static com.burgess.burgessgo.Constants.PREF_BUILDER_PERSONNEL_ID;

import androidx.appcompat.app.AppCompatActivity;
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

public class DeactivateHomesActivity extends BaseActivity {
    private static final String TAG = "DEACTIVATE_HOMES";

    // View components
    private DeactivateHomesViewModel mViewModel;
    private ConstraintLayout mConstraintLayout;
    private RecyclerView mRecyclerActiveHomesList;
    private Button mButtonSubmit;

    // Class members
    private static GoAPIQueue apiQueue;
    private static GoLogger logger;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private DeactivateHomesListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deactivate_homes);
        setSupportActionBar(findViewById(R.id.deactivate_homes_toolbar));
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(DeactivateHomesViewModel.class);

        // Prepare logger and API queue
        logger = GoLogger.getInstance(this);
        apiQueue = GoAPIQueue.getInstance(this);

        // Prepare shared preferences
        mSharedPreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        initializeViews();
        initializeButtons();
        initializeDisplayContent();
    }

    private void initializeViews() {
        mConstraintLayout = findViewById(R.id.deactivate_homes_constraint_layout);
        mRecyclerActiveHomesList = findViewById(R.id.deactivate_homes_recycler_active_home_list);
        mButtonSubmit = findViewById(R.id.deactivate_homes_button_submit);
    }

    private void initializeButtons() {
        mButtonSubmit.setOnClickListener(v -> {
            Snackbar.make(mConstraintLayout, "Submit button clicked", Snackbar.LENGTH_SHORT).show();
        });
    }

    private void initializeDisplayContent() {
        mListAdapter = new DeactivateHomesListAdapter(new DeactivateHomesListAdapter.InspectDiff());
        mRecyclerActiveHomesList.setAdapter(mListAdapter);
        mRecyclerActiveHomesList.setLayoutManager(new LinearLayoutManager(this));
        updateActiveHomeList();
    }

    private void updateActiveHomeList() {
        mViewModel.clearActiveHomeList();
        apiQueue.getRequestQueue().add(apiQueue.getActiveHomes(mViewModel, mSharedPreferences.getInt(PREF_BUILDER_PERSONNEL_ID, -1), new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                mListAdapter.notifyDataSetChanged();
                mListAdapter.submitList(mViewModel.getActiveHomeList());
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_SHORT).show();
            }
        }));
    }
}