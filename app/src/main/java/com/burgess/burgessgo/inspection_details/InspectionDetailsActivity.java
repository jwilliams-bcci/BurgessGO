package com.burgess.burgessgo.inspection_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.burgess.burgessgo.BaseActivity;
import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.reschedule_inspection.RescheduleInspectionActivity;
import com.burgess.burgessgo.schedule_reinspection.ScheduleReinspectionActivity;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import data.models.Inspection;

public class InspectionDetailsActivity extends BaseActivity {
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
        mInspection = intent.getParcelableExtra(INTENT_EXTRA);

        initializeViews();
        initializeButtons();
        initializeDisplayContent();
    }

    public void initializeViews() {
        mTextViewAddress = findViewById(R.id.inspection_details_textView_address);
        mTextViewInspectionType = findViewById(R.id.inspection_details_textView_inspection_type);
        mTextViewStatus = findViewById(R.id.inspection_details_textView_status);
        mTextViewDate = findViewById(R.id.inspection_details_textView_date);
        mTextViewConsultant = findViewById(R.id.inspection_details_textView_consultant);
        mButtonReschedule = findViewById(R.id.inspection_details_button_reschedule);
    }

    public void initializeButtons() {
        mButtonReschedule.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), RescheduleInspectionActivity.class);
            i.putExtra(RescheduleInspectionActivity.INTENT_EXTRA, mInspection);
            v.getContext().startActivity(i);
        });
    }

    public void initializeDisplayContent() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        mTextViewAddress.setText(mInspection.getAddressToDisplay());
        mTextViewInspectionType.setText(mInspection.getTypeName());
        mTextViewStatus.setText(mInspection.getInspectionStatus());
        mTextViewDate.setText(mInspection.getInspectionDate().toLocalDate().format(formatter));
        mTextViewConsultant.setText(mInspection.getInspectorName());
    }
}