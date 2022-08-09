package com.burgess.burgessgo.non_passed_inspection_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.burgess.burgessgo.BaseActivity;
import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.schedule_reinspection.ScheduleReinspectionActivity;
import com.google.android.material.snackbar.Snackbar;

import java.time.format.DateTimeFormatter;

import data.models.NonPassedInspection;

public class NonPassedInspectionDetailsActivity extends BaseActivity {
    private static final String TAG = "NON_PASSED_INSPECTION_DETAILS";
    public static final String INTENT_EXTRA = "INSPECTION";

    // View components
    private ConstraintLayout mConstraintLayout;
    private TextView mTextViewAddress;
    private TextView mTextViewInspectionType;
    private TextView mTextViewStatus;
    private TextView mTextViewDate;
    private TextView mTextViewConsultant;
    private TextView mTextViewEmailTo;
    private TextView mTextViewEmailMessage;
    private Button mButtonViewReport;
    private Button mButtonReinspect;
    private Button mButtonViewDefects;
    private Button mButtonEmailSend;
    private CheckBox mCheckBoxEmailCopyMe;

    // Class members
    private static GoLogger logger;
    private NonPassedInspection mInspection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_passed_inspection_details);
        setSupportActionBar(findViewById(R.id.non_passed_inspection_details_toolbar));

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
        mConstraintLayout = findViewById(R.id.non_passed_inspection_details_constraint_layout);
        mTextViewAddress = findViewById(R.id.non_passed_inspection_details_textView_address);
        mTextViewInspectionType = findViewById(R.id.non_passed_inspection_details_textView_inspection_type);
        mTextViewStatus = findViewById(R.id.non_passed_inspection_details_textView_status);
        mTextViewDate = findViewById(R.id.non_passed_inspection_details_textView_date);
        mTextViewConsultant = findViewById(R.id.non_passed_inspection_details_textView_consultant);
        mTextViewEmailTo = findViewById(R.id.non_passed_inspection_details_textView_email_to);
        mTextViewEmailMessage = findViewById(R.id.non_passed_inspection_details_textView_email_message);
        mButtonViewReport = findViewById(R.id.non_passed_inspection_details_button_view_report);
        mButtonReinspect = findViewById(R.id.non_passed_inspection_details_button_reinspect);
        mButtonViewDefects = findViewById(R.id.non_passed_inspection_details_button_view_defects);
        mButtonEmailSend = findViewById(R.id.non_passed_inspection_details_button_email_send);
        mCheckBoxEmailCopyMe = findViewById(R.id.non_passed_inspection_details_checkBox_email_copy_me);
    }

    public void initializeButtons() {
        mButtonViewReport.setOnClickListener(v -> {
            Snackbar.make(mConstraintLayout, "View Report clicked", Snackbar.LENGTH_SHORT).show();
        });

        mButtonReinspect.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), ScheduleReinspectionActivity.class);
            i.putExtra(ScheduleReinspectionActivity.INTENT_EXTRA, mInspection);
            v.getContext().startActivity(i);
        });

        mButtonViewDefects.setOnClickListener(v -> {
            Snackbar.make(mConstraintLayout, "View Defects clicked", Snackbar.LENGTH_SHORT).show();
        });

        mButtonEmailSend.setOnClickListener(v -> {
            Snackbar.make(mConstraintLayout, "Send Email clicked", Snackbar.LENGTH_SHORT).show();
        });
    }

    public void initializeDisplayContent() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        mTextViewAddress.setText(mInspection.getInspectionAddress());
        mTextViewInspectionType.setText(mInspection.getTypeName());
        mTextViewStatus.setText(mInspection.getInspectionStatus());
        mTextViewDate.setText(mInspection.getInspectionDate().toLocalDate().format(formatter));
        mTextViewConsultant.setText(mInspection.getInspectedBy());
    }
}