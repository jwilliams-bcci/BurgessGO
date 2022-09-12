package com.burgess.burgessgo.reschedule_inspection;

import static com.burgess.burgessgo.Constants.PREF;
import static com.burgess.burgessgo.Constants.PREF_SECURITY_USER_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
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
import java.time.OffsetDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import data.models.Inspection;
import data.models.InspectionType;

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
    private static GoAPIQueue apiQueue;
    private static GoLogger logger;
    private SharedPreferences mSharedPreferences;
    private Inspection mInspection;
    private final Calendar mCalendar = Calendar.getInstance();
    private DateTimeFormatter mFormatter;
    private SimpleDateFormat mDateFormat;
    private LocalDate mNextAvailableDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reschedule_inspection);
        setSupportActionBar(findViewById(R.id.reschedule_inspection_toolbar));

        // Prepare API queue and logger
        apiQueue = GoAPIQueue.getInstance(this);
        logger = GoLogger.getInstance(this);

        // Prepare shared preferences
        mSharedPreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);

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
            TimeZone tz = TimeZone.getDefault();
            Calendar cal = GregorianCalendar.getInstance(tz);
            int offsetInMillis = tz.getOffset(cal.getTimeInMillis());
            String timeAdjustHours = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
            timeAdjustHours = (offsetInMillis >= 0 ? "+" : "-") + timeAdjustHours;

            String requestDate = mTextViewDate.getText().toString();
            String poNumber = mTextViewPONumber.getText().toString().isEmpty() ? mTextViewPONumber.getText().toString() : "";
            String notes = mTextViewNotes.getText().toString().isEmpty() ? mTextViewNotes.getText().toString() : "";
            int userId = mSharedPreferences.getInt(PREF_SECURITY_USER_ID, -1);

            String validityCheck = validityCheck(requestDate);

            if (validityCheck.equals("Success")) {
                String finalTimeAdjustHours = timeAdjustHours;
                apiQueue.getRequestQueue().add(apiQueue.getCheckHoliday(requestDate, new ServerCallback() {
                    @Override
                    public void onSuccess(String message) {
                        if (message.equals("Holiday")) {
                            Snackbar.make(mConstraintLayout, "Error! Cannot schedule on a holiday!", Snackbar.LENGTH_SHORT).show();
                        } else {
                            apiQueue.getRequestQueue().add(apiQueue.postRescheduleInspection(mInspection.getInspectionId(), requestDate, poNumber, notes, userId, new ServerCallback() {
                                @Override
                                public void onSuccess(String message) {
                                    Snackbar.make(mConstraintLayout, "Reschedule request sent successfully", Snackbar.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getBaseContext(), UpcomingInspectionsActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(String message) {
                                    Snackbar.make(mConstraintLayout, "Reschedule request failed yo, fix that shiz: " + message, Snackbar.LENGTH_SHORT).show();
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
        mDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        mFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        mTextViewAddress.setText(mInspection.getAddressToDisplay());
        mTextViewDate.setText(mInspection.getInspectionDate().toLocalDate().format(mFormatter));
        mTextViewInspectionType.setText(mInspection.getTypeName());

        DatePickerDialog.OnDateSetListener date = (datePicker, year, month, day) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, day);
            mTextViewDate.setText(mDateFormat.format(mCalendar.getTime()));
        };
        mTextViewDate.setOnClickListener(v -> {
            new DatePickerDialog(this, date, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        getNextAvailableDate(LocalDateTime.now().toLocalDate());
    }

    public void getNextAvailableDate(LocalDate current) {
        LocalDate nextAvailableDate = current.plusDays(1);
        if (nextAvailableDate.getDayOfWeek() == DayOfWeek.SATURDAY || nextAvailableDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            getNextAvailableDate(nextAvailableDate);
        } else {
            apiQueue.getRequestQueue().add(apiQueue.getCheckHoliday(nextAvailableDate.toString(), new ServerCallback() {
                @Override
                public void onSuccess(String message) {
                    if (message.equals("Holiday")) {
                        getNextAvailableDate(nextAvailableDate);
                    } else {
                        mNextAvailableDate = nextAvailableDate;
                        Snackbar.make(mConstraintLayout, "Next available date is: " + mNextAvailableDate.toString(), Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(String message) {
                    Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_SHORT).show();
                }
            }));
        }
    }

    public String validityCheck(String dateToCheck) {
        if (dateToCheck.equals("")) {
            return "Please enter a date!";
        }

        LocalDate requestDate = LocalDate.parse(dateToCheck, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDateTime currentDate = LocalDateTime.now();
        LocalTime threeOClock = LocalTime.of(14, 59);

        if (requestDate.isBefore(ChronoLocalDate.from(currentDate))) {
            return "Cannot schedule before today!";
        }

        if (requestDate.equals(currentDate.toLocalDate())) {
            return "Cannot schedule for today!";
        }

        if (currentDate.toLocalTime().isAfter(threeOClock) && requestDate.equals(mNextAvailableDate)) {
            return "Cannot schedule for the next available day if it's past 3:00!";
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