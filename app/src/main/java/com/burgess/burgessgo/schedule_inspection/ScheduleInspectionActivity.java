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
import com.burgess.burgessgo.upcoming_inspections.UpcomingInspectionsActivity;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import data.models.Home;
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
    private Home mHome;
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
        mHome = intent.getParcelableExtra(INTENT_EXTRA);

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
            InspectionType t = (InspectionType) mSpinnerInspectionType.getSelectedItem();
            TimeZone tz = TimeZone.getDefault();
            Calendar cal = GregorianCalendar.getInstance(tz);
            int offsetInMillis = tz.getOffset(cal.getTimeInMillis());
            String timeAdjustHours = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
            timeAdjustHours = (offsetInMillis >= 0 ? "+" : "-") + timeAdjustHours;

            int locationId = mHome.getLocationId();
            String address = mHome.getAddress();
            String requestDate = mTextViewInspectionDate.getText().toString();
            int inspectionTypeId = t.getInspectionTypeId();
            String poNumber = mTextViewPONumber.getText().toString().isEmpty() ? mTextViewPONumber.getText().toString() : "";
            String notes = mTextViewNotes.getText().toString().isEmpty() ? mTextViewNotes.getText().toString() : "";
            int userId = mSharedPreferences.getInt(PREF_SECURITY_USER_ID, -1);

            String validityCheck = validityCheck(requestDate, t);

            if (validityCheck.equals("Success")) {
                String finalTimeAdjustHours = timeAdjustHours;
                apiQueue.getRequestQueue().add(apiQueue.getCheckHoliday(requestDate, new ServerCallback() {
                    @Override
                    public void onSuccess(String message) {
                        if (message.equals("Holiday")) {
                            Snackbar.make(mConstraintLayout, "Error! Cannot schedule on a holiday!", Snackbar.LENGTH_SHORT).show();
                        } else {
                            apiQueue.getRequestQueue().add(apiQueue.postScheduleInspection(locationId, address, requestDate, inspectionTypeId, poNumber, notes, userId, finalTimeAdjustHours, new ServerCallback() {
                                @Override
                                public void onSuccess(String message) {
                                    Snackbar.make(mConstraintLayout, "Inspection successfully scheduled", Snackbar.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getBaseContext(), UpcomingInspectionsActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(String message) {
                                    Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_SHORT).show();
                                }
                            }));
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_SHORT).show();
                    }
                }));
            } else {
                Snackbar.make(mConstraintLayout, "Error! " + validityCheck, Snackbar.LENGTH_SHORT).show();
            }
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

        mTextViewAddress.setText(mHome.getAddress());

        loadSpinner();
    }

    public void loadSpinner() {
        apiQueue.getRequestQueue().add(apiQueue.getInspectionTypes(mViewModel, mHome.getLocationId(), mSharedPreferences.getInt(PREF_SECURITY_USER_ID, -1), new ServerCallback() {
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

    public String validityCheck(String dateToCheck, InspectionType inspectionType) {
        if (inspectionType.getInspectionTypeId() == -1) {
            return "Please select an inspection type!";
        }

        if (dateToCheck.equals("")) {
            return "Please enter a date!";
        }

        LocalDate requestDate = LocalDate.parse(dateToCheck, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDate tomorrow = currentDate.toLocalDate().plusDays(1);
        LocalTime threeOClock = LocalTime.of(14, 59);

        if (requestDate.isBefore(ChronoLocalDate.from(currentDate))) {
            return "Cannot schedule before today!";
        }

        if (requestDate.equals(currentDate.toLocalDate())) {
            return "Cannot schedule for today!";
        }

        if (currentDate.toLocalTime().isAfter(threeOClock) && requestDate.equals(tomorrow)) {
            return "Cannot schedule for tomorrow if it's past 3:00!";
        }

        if (requestDate.getDayOfWeek() == DayOfWeek.SATURDAY || requestDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return "Cannot schedule for the weekend!";
        }

        if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            if (requestDate == LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY))) {
                return "Cannot schedule for Monday if it's the weekend!";
            }
        }
        return "Success";
    }
}