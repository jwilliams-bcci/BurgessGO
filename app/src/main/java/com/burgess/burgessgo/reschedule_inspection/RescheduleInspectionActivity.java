package com.burgess.burgessgo.reschedule_inspection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.burgess.burgessgo.BaseActivity;
import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

import data.models.Inspection;

public class RescheduleInspectionActivity extends BaseActivity {
    private static final String TAG = "RESCHEDULE_INSPECTION";
    public static final String INTENT_EXTRA = "INSPECTION";

    // View Components
    private ConstraintLayout mConstraintLayout;
    private TextView mTextViewAddress;
    private TextView mTextViewDate;
    private TextView mTextViewInspectionType;
    private TextView mTextViewPONumber;
    private TextView mTextViewNotes;
    private Button mButtonReschedule;

    // Class members
    private static GoLogger logger;
    private Inspection mInspection;
    private final Calendar mCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reschedule_inspection);
        setSupportActionBar(findViewById(R.id.reschedule_inspection_toolbar));

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
        mConstraintLayout = findViewById(R.id.reschedule_inspection_constraint_layout);
        mTextViewAddress = findViewById(R.id.reschedule_inspection_textView_address);
        mTextViewDate = findViewById(R.id.reschedule_inspection_textView_date);
        mTextViewInspectionType = findViewById(R.id.reschedule_inspection_textView_inspection_type);
        mTextViewPONumber = findViewById(R.id.reschedule_inspection_textView_po_number);
        mTextViewNotes = findViewById(R.id.reschedule_inspection_textView_notes);
        mButtonReschedule = findViewById(R.id.reschedule_inspection_button_reschedule);
    }

    public void initializeButtons() {
        mButtonReschedule.setOnClickListener(v -> {
            Snackbar.make(mConstraintLayout, "Reschedule button clicked", Snackbar.LENGTH_SHORT).show();
        });
    }

    public void initializeDisplayContent() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        mTextViewAddress.setText(mInspection.getAddressToDisplay());
        mTextViewDate.setText(mInspection.getInspectionDate().toLocalDate().format(formatter));
        mTextViewInspectionType.setText(mInspection.getTypeName());

        DatePickerDialog.OnDateSetListener date = (datePicker, year, month, day) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, day);
            mTextViewDate.setText(dateFormat.format(mCalendar.getTime()));
        };
        mTextViewDate.setOnClickListener(v -> {
            new DatePickerDialog(this, date, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }
}