package com.burgess.burgessgo.my_homes;

import static com.burgess.burgessgo.Constants.PREF;
import static com.burgess.burgessgo.Constants.PREF_BUILDER_PERSONNEL_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
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

public class MyHomesActivity extends BaseActivity {
    public static final String TAG = "MY_HOMES";

    // View components
    private MyHomesViewModel mViewModel;
    private ConstraintLayout mConstraintLayout;
    private Button mButtonAddNewAddress;
    private RecyclerView mRecyclerMyHomesList;

    // Class members
    private static GoAPIQueue apiQueue;
    private static GoLogger logger;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private MyHomesListAdapter mListAdapter;
    private DividerItemDecoration mDividerItemDecoration;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_homes);
        setSupportActionBar(findViewById(R.id.my_homes_toolbar));
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MyHomesViewModel.class);

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

    public void initializeViews() {
        mConstraintLayout = findViewById(R.id.my_homes_constraint_layout);
        mButtonAddNewAddress = findViewById(R.id.my_homes_button_add_new_address);
        mRecyclerMyHomesList = findViewById(R.id.my_homes_recycler_home_list);
    }

    public void initializeButtons() {
        mButtonAddNewAddress.setOnClickListener(v -> {
            Snackbar.make(mConstraintLayout, "Clicked Add New Address", Snackbar.LENGTH_SHORT).show();
        });
    }

    public void initializeDisplayContent() {
        mLinearLayoutManager = new LinearLayoutManager(this);
        mDividerItemDecoration = new DividerItemDecoration(mRecyclerMyHomesList.getContext(), mLinearLayoutManager.getOrientation());
        mListAdapter = new MyHomesListAdapter(new MyHomesListAdapter.InspectDiff());
        mRecyclerMyHomesList.setAdapter(mListAdapter);
        mRecyclerMyHomesList.setLayoutManager(mLinearLayoutManager);
        mRecyclerMyHomesList.addItemDecoration(mDividerItemDecoration);
        updateMyHomesList();
    }

    public void updateMyHomesList() {
        mViewModel.clearActiveLocationList();
        apiQueue.getRequestQueue().add(apiQueue.getActiveLocationsBySuper(mViewModel, mSharedPreferences.getInt(PREF_BUILDER_PERSONNEL_ID, -1), 0, new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                mListAdapter.notifyDataSetChanged();
                mListAdapter.submitList(mViewModel.getActiveLocationList());
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_LONG).show();
            }
        }));
    }
}