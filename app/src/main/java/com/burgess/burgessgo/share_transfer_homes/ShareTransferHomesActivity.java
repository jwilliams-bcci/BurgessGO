package com.burgess.burgessgo.share_transfer_homes;

import static com.burgess.burgessgo.Constants.PREF;
import static com.burgess.burgessgo.Constants.PREF_BUILDER_ID;
import static com.burgess.burgessgo.Constants.PREF_BUILDER_PERSONNEL_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.burgess.burgessgo.BaseActivity;
import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.ServerCallback;
import com.burgess.burgessgo.deactivate_homes.DeactivateHomesListAdapter;
import com.google.android.material.snackbar.Snackbar;

public class ShareTransferHomesActivity extends BaseActivity {
    private static final String TAG = "SHARE_TRANSFER_HOMES";

    // View components
    private ShareTransferHomesViewModel mViewModel;
    private ConstraintLayout mConstraintLayout;
    private RecyclerView mRecyclerHomeList;

    // Class members
    private static GoAPIQueue apiQueue;
    private static GoLogger logger;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private ShareTransferHomesListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_transfer_homes);
        setSupportActionBar(findViewById(R.id.share_transfer_homes_toolbar));
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ShareTransferHomesViewModel.class);

        // Prepare logger and API queue
        logger = GoLogger.getInstance(this);
        apiQueue = GoAPIQueue.getInstance(this);

        // Prepare shared preferences
        mSharedPreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        initializeViews();
        initializeDisplayContent();
    }

    public void initializeViews() {
        mConstraintLayout = findViewById(R.id.share_transfer_homes_constraint_layout);
        mRecyclerHomeList = findViewById(R.id.share_transfer_homes_recycler_home_list);
    }

    public void initializeDisplayContent() {
        mRecyclerHomeList.setLayoutManager(new LinearLayoutManager(this));
        updateHomeList();
    }

    public void updateHomeList() {
        mViewModel.clearActiveHomeList();
        apiQueue.getRequestQueue().add(apiQueue.getActiveHomes(mViewModel, mSharedPreferences.getInt(PREF_BUILDER_PERSONNEL_ID, -1), new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                mListAdapter = new ShareTransferHomesListAdapter(mViewModel.getActiveHomeList(), apiQueue, mViewModel, mSharedPreferences.getInt(PREF_BUILDER_ID, -1));
                mRecyclerHomeList.setAdapter(mListAdapter);
                mListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_SHORT).show();
            }
        }));
    }
}