package com.burgess.burgessgo.inspection_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;

import data.models.Inspection;

public class InspectionDetailsActivity extends AppCompatActivity {
    private static final String TAG = "INSPECTION_DETAILS";
    public static final String INTENT_EXTRA = "INSPECTION";

    // View components
    private ConstraintLayout mConstraintLayout;
    private TextView mTextViewAddress;
    private TextView mTextViewInspectionType;
    private TextView mTextViewStatus;
    private TextView mTextViewDate;
    private TextView mTextViewConsultant;
    private Button mButtonReschedule;

    // Class members
    private static GoLogger logger;
    private Inspection mInspection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_details);
        setSupportActionBar(findViewById(R.id.inspection_details_toolbar));

        // Prepare logger
        logger = GoLogger.getInstance(this);

        // Get intent
        Intent intent = getIntent();
        mInspection = (Inspection) intent.getParcelableExtra(INTENT_EXTRA);

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
        mTextViewAddress = findViewById(R.id.inspection_details_textView_address);
        mTextViewInspectionType = findViewById(R.id.inspection_details_textView_inspection_type);
        mTextViewStatus = findViewById(R.id.inspection_details_textView_status);
        mTextViewDate = findViewById(R.id.inspection_details_textView_date);
        mTextViewConsultant = findViewById(R.id.inspection_details_textView_consultant);
    }

    public void initializeDisplayContent() {
        mTextViewAddress.setText(mInspection.getAddressToDisplay());
        mTextViewInspectionType.setText(mInspection.getTypeName());
        mTextViewStatus.setText(mInspection.getInspectionStatus());
        mTextViewDate.setText(mInspection.getInspectionDate().toString());
        mTextViewConsultant.setText(mInspection.getInspectorName());
    }
}