package com.burgess.burgessgo.schedule_reinspection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.burgess.burgessgo.BaseActivity;
import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import data.models.NonPassedInspection;

public class ScheduleReinspectionActivity extends BaseActivity {
    private static final String TAG = "SCHEDULE_REINSPECTION";
    public static final String INTENT_EXTRA = "INSPECTION";

    // View Components
    private ConstraintLayout mConstraintLayout;
    private TextView mTextViewAddress;
    private TextView mTextViewInspectionType;
    private TextView mTextViewPONumber;
    private TextView mTextViewDate;
    private TextView mTextViewNotes;
    private Button mButtonSchedule;

    // Class members
    private static GoLogger logger;
    private NonPassedInspection mInspection;
    private final Calendar mCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_reinspection);
        setSupportActionBar(findViewById(R.id.schedule_reinspection_toolbar));

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
        mConstraintLayout = findViewById(R.id.schedule_reinspection_constraint_layout);
        mTextViewAddress = findViewById(R.id.schedule_reinspection_textView_address);
        mTextViewInspectionType = findViewById(R.id.schedule_reinspection_textView_inspection_type);
        mTextViewPONumber = findViewById(R.id.schedule_reinspection_textView_po_number);
        mTextViewDate = findViewById(R.id.schedule_reinspection_textView_date);
        mTextViewNotes = findViewById(R.id.schedule_reinspection_textView_notes);
        mButtonSchedule = findViewById(R.id.schedule_reinspection_button_schedule);
    }

    public void initializeButtons() {
        mButtonSchedule.setOnClickListener(v -> {
            Snackbar.make(mConstraintLayout, "Schedule button clicked", Snackbar.LENGTH_SHORT).show();
        });
    }

    public void initializeDisplayContent() {
        mTextViewAddress.setText(mInspection.getInspectionAddress());
        mTextViewInspectionType.setText(mInspection.getTypeName());

        DatePickerDialog.OnDateSetListener date = (datePicker, year, month, day) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, day);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            mTextViewDate.setText(dateFormat.format(mCalendar.getTime()));
        };
        mTextViewDate.setOnClickListener(v -> {
            new DatePickerDialog(this, date, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }
}