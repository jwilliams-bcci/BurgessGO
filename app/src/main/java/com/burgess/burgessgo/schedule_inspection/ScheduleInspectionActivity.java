package com.burgess.burgessgo.schedule_inspection;

import static com.burgess.burgessgo.Constants.PREF;
import static com.burgess.burgessgo.Constants.PREF_SECURITY_USER_ID;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.burgess.burgessgo.BaseActivity;
import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.ServerCallback;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

import data.models.ActiveLocation;
import data.models.BuilderPersonnel;
import data.models.InspectionType;

public class ScheduleInspectionActivity extends BaseActivity {
    private static final String TAG = "SCHEDULE_INSPECTION";
    public static final String INTENT_EXTRA = "LOCATION";

    // View components
    private ScheduleInspectionViewModel mViewModel;
    private ConstraintLayout mConstraintLayout;
    private TextView mTextViewAddress;
    private TextView mTextViewInspectionDate;
    private Spinner mSpinnerInspectionType;
    private TextView mTextViewPONumber;
    private TextView mTextViewNotes;
    private Button mButtonSchedule;

    // Class members
    private static GoLogger logger;
    private static GoAPIQueue apiQueue;
    private final Calendar mCalendar = Calendar.getInstance();
    private ActiveLocation mActiveLocation;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private ArrayAdapter<InspectionType> mSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_inspection);
        setSupportActionBar(findViewById(R.id.schedule_inspection_toolbar));
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ScheduleInspectionViewModel.class);

        // Prepare logger and API queue
        logger = GoLogger.getInstance(this);
        apiQueue = GoAPIQueue.getInstance(this);

        // Prepare shared preferences
        mSharedPreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        // Get intent
        Intent intent = getIntent();
        mActiveLocation = intent.getParcelableExtra(INTENT_EXTRA);

        initializeViews();
        initializeButtons();
        initializeDisplayContent();
    }

    public void initializeViews() {
        mConstraintLayout = findViewById(R.id.schedule_inspection_constraint_layout);
        mTextViewAddress = findViewById(R.id.schedule_inspection_textView_address);
        mTextViewInspectionDate = findViewById(R.id.schedule_inspection_textView_inspection_date);
        mSpinnerInspectionType = findViewById(R.id.schedule_inspection_spinner_inspection_type);
        mTextViewPONumber = findViewById(R.id.schedule_inspection_textView_po_number);
        mTextViewNotes = findViewById(R.id.schedule_inspection_textView_notes);
        mButtonSchedule = findViewById(R.id.schedule_inspection_button_schedule);
    }

    public void initializeButtons() {
        mButtonSchedule.setOnClickListener(v -> {
            Snackbar.make(mConstraintLayout, "Schedule button clicked", Snackbar.LENGTH_SHORT).show();
        });
    }

    public void initializeDisplayContent() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        mSpinnerAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, mViewModel.getInspectionTypeList());

        DatePickerDialog.OnDateSetListener date = (datePicker, year, month, day) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, day);
            mTextViewInspectionDate.setText(dateFormat.format(mCalendar.getTime()));
        };
        mTextViewInspectionDate.setOnClickListener(v -> {
            new DatePickerDialog(this, date, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        mTextViewAddress.setText(mActiveLocation.getAddress());

        loadSpinner();
    }

    public void loadSpinner() {
        apiQueue.getRequestQueue().add(apiQueue.getInspectionTypes(mViewModel, mActiveLocation.getLocationId(), mSharedPreferences.getInt(PREF_SECURITY_USER_ID, -1), new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                mSpinnerInspectionType.setAdapter(mSpinnerAdapter);
                mViewModel.insertHelperInspectionType();
                mSpinnerAdapter.notifyDataSetChanged();
                mSpinnerInspectionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != 0) {
                            InspectionType t = (InspectionType) adapterView.getSelectedItem();
                            Snackbar.make(mConstraintLayout, "Selected " + t.getTypeName() + ", ID: " + t.getInspectionTypeId(), Snackbar.LENGTH_SHORT).show();
                        }
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
}