package com.burgess.burgessgo.inspection_defects;

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
import android.widget.TextView;

import com.burgess.burgessgo.BaseActivity;
import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.ServerCallback;
import com.google.android.material.snackbar.Snackbar;

import java.time.format.DateTimeFormatter;

import data.models.Inspection;
import data.models.NonPassedInspection;

public class InspectionDefectsActivity extends BaseActivity {
    private static final String TAG = "INSPECTION_DEFECTS";
    public static final String INTENT_EXTRA = "INSPECTION";

    // View components
    private InspectionDefectsViewModel mViewModel;
    private ConstraintLayout mConstraintLayout;
    private TextView mTextViewAddress;
    private TextView mTextViewInspectionType;
    private TextView mTextViewInspectionDate;
    private TextView mTextViewConsultant;
    private RecyclerView mRecyclerInspectionDefectList;

    // Class members
    private static GoAPIQueue apiQueue;
    private static GoLogger logger;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private InspectionDefectsListAdapter mListAdapter;
    private NonPassedInspection mInspection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_defects);
        setSupportActionBar(findViewById(R.id.inspection_defects_toolbar));
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(InspectionDefectsViewModel.class);

        // Prepare logger and API queue
        logger = GoLogger.getInstance(this);
        apiQueue = GoAPIQueue.getInstance(this);

        // Prepare shared preferences
        mSharedPreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        // Get intent
        Intent intent = getIntent();
        mInspection = intent.getParcelableExtra(INTENT_EXTRA);

        initializeViews();
        initializeDisplayContent();
    }

    public void initializeViews() {
        mConstraintLayout = findViewById(R.id.inspection_defects_constraint_layout);
        mTextViewAddress = findViewById(R.id.inspection_defects_textView_address);
        mTextViewInspectionType = findViewById(R.id.inspection_defects_textView_inspection_type);
        mTextViewInspectionDate = findViewById(R.id.inspection_defects_textView_date);
        mTextViewConsultant = findViewById(R.id.inspection_defects_textView_consultant);
        mRecyclerInspectionDefectList = findViewById(R.id.inspection_defects_recycler_inspection_defect_list);
    }

    public void initializeDisplayContent() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        mTextViewAddress.setText(mInspection.getInspectionAddress());
        mTextViewInspectionType.setText(mInspection.getTypeName());
        mTextViewInspectionDate.setText(mInspection.getInspectionDate().toLocalDate().format(formatter));
        mTextViewConsultant.setText(mInspection.getInspectedBy());

        mListAdapter = new InspectionDefectsListAdapter(new InspectionDefectsListAdapter.InspectDiff());
        mRecyclerInspectionDefectList.setAdapter(mListAdapter);
        mRecyclerInspectionDefectList.setLayoutManager(new LinearLayoutManager(this));
        updateInspectionDefectList();
    }

    public void updateInspectionDefectList() {
        mViewModel.clearInspectionDefectList();
        apiQueue.getRequestQueue().add(apiQueue.getInspectionDefects(mViewModel, mInspection.getInspectionId(), new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                mListAdapter.notifyDataSetChanged();
                mListAdapter.submitList(mViewModel.getInspectionDefectList());
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_LONG).show();
            }
        }));
    }
}