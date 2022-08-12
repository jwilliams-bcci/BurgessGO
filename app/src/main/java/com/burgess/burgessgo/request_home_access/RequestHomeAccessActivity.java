package com.burgess.burgessgo.request_home_access;

import static com.burgess.burgessgo.Constants.PREF;
import static com.burgess.burgessgo.Constants.PREF_BUILDER_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.burgess.burgessgo.BaseActivity;
import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.ServerCallback;
import com.google.android.material.snackbar.Snackbar;

import data.models.BuilderPersonnel;

public class RequestHomeAccessActivity extends BaseActivity {
    private static final String TAG = "REQUEST_HOME_ACCESS";

    // View components
    private RequestHomeAccessViewModel mViewModel;
    private ConstraintLayout mConstraintLayout;
    private Spinner mSpinnerBuilderPersonnel;
    private RecyclerView mRecyclerHomeList;

    // Class members
    private static GoAPIQueue apiQueue;
    private static GoLogger logger;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private RequestHomeAccessListAdapter mListAdapter;
    private ArrayAdapter<BuilderPersonnel> mSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_home_access);
        setSupportActionBar(findViewById(R.id.request_home_access_toolbar));
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(RequestHomeAccessViewModel.class);

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
        mConstraintLayout = findViewById(R.id.request_home_access_constraint_layout);
        mSpinnerBuilderPersonnel = findViewById(R.id.request_home_access_spinner_builder_personnel);
        mRecyclerHomeList = findViewById(R.id.request_home_access_recycler_home_list);
    }

    public void initializeDisplayContent() {
        mSpinnerAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, mViewModel.getBuilderPersonnelList());
        mRecyclerHomeList.setLayoutManager(new LinearLayoutManager(this));
        updateBuilderPersonnelList();
    }

    public void updateBuilderPersonnelList() {
        apiQueue.getRequestQueue().add(apiQueue.getBuilderPersonnel(mViewModel, mSharedPreferences.getInt(PREF_BUILDER_ID, -1), 0, new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                mSpinnerBuilderPersonnel.setAdapter(mSpinnerAdapter);
                mSpinnerAdapter.notifyDataSetChanged();
                mSpinnerBuilderPersonnel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        BuilderPersonnel p = (BuilderPersonnel) adapterView.getSelectedItem();
                        Snackbar.make(mConstraintLayout, "Selected " + p.getPersonnelName() + ", ID: " + p.getBuilderPersonnelId(), Snackbar.LENGTH_SHORT).show();
                        loadHomes(p.getBuilderPersonnelId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_SHORT).show();
            }
        }));
    }

    public void loadHomes(int builderPersonnelId) {
        mViewModel.clearActiveHomeList();
        apiQueue.getRequestQueue().add(apiQueue.getActiveHomes(mViewModel, builderPersonnelId, new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                mListAdapter = new RequestHomeAccessListAdapter(mViewModel.getActiveHomeList());
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