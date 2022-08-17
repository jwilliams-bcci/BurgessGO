package com.burgess.burgessgo.non_passed_inspection_details;

import static com.burgess.burgessgo.Constants.PREF;
import static com.burgess.burgessgo.Constants.PREF_SECURITY_USER_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.burgess.burgessgo.BaseActivity;
import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.ReportViewerActivity;
import com.burgess.burgessgo.ServerCallback;
import com.burgess.burgessgo.inspection_defects.InspectionDefectsActivity;
import com.burgess.burgessgo.schedule_reinspection.ScheduleReinspectionActivity;
import com.google.android.material.snackbar.Snackbar;

import java.time.format.DateTimeFormatter;

import data.models.NonPassedInspection;

public class NonPassedInspectionDetailsActivity extends BaseActivity {
    private static final String TAG = "NON_PASSED_INSPECTION_DETAILS";
    public static final String INTENT_EXTRA = "INSPECTION";

    // View components
    private NonPassedInspectionDetailsViewModel mViewModel;
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
    private static GoAPIQueue apiQueue;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private NonPassedInspection mInspection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_passed_inspection_details);
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(NonPassedInspectionDetailsViewModel.class);
        setSupportActionBar(findViewById(R.id.non_passed_inspection_details_toolbar));

        // Prepare logger and API queue
        apiQueue = GoAPIQueue.getInstance(this);
        logger = GoLogger.getInstance(this);

        // Prepare shared preferences
        mSharedPreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

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
            apiQueue.getRequestQueue().add(apiQueue.getReportData(mViewModel, mInspection.getInspectionId(), mSharedPreferences.getInt(PREF_SECURITY_USER_ID, -1), new ServerCallback() {
                @Override
                public void onSuccess(String message) {
                    Intent i = new Intent(v.getContext(), ReportViewerActivity.class);
                    i.putExtra(ReportViewerActivity.INTENT_EXTRA, mViewModel.getReportUrl());
                    v.getContext().startActivity(i);
                }

                @Override
                public void onFailure(String message) {
                    Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_SHORT).show();
                }
            }));
        });

        mButtonReinspect.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), ScheduleReinspectionActivity.class);
            i.putExtra(ScheduleReinspectionActivity.INTENT_EXTRA, mInspection);
            v.getContext().startActivity(i);
        });

        mButtonViewDefects.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), InspectionDefectsActivity.class);
            i.putExtra(InspectionDefectsActivity.INTENT_EXTRA, mInspection);
            v.getContext().startActivity(i);
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