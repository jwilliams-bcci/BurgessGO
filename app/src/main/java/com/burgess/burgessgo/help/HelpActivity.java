package com.burgess.burgessgo.help;

import static com.burgess.burgessgo.Constants.PREF;
import static com.burgess.burgessgo.Constants.PREF_SECURITY_USER_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.burgess.burgessgo.BaseActivity;
import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;

public class HelpActivity extends BaseActivity {
    private static final String TAG = "HELP";

    // View components
    private ConstraintLayout mConstraintLayout;
    private Button mButtonSendLog;

    // Class members
    private static GoAPIQueue apiQueue;
    private static GoLogger logger;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setSupportActionBar(findViewById(R.id.help_toolbar));

        // Prepare logger and API queue
        logger = GoLogger.getInstance(this);
        apiQueue = GoAPIQueue.getInstance(this);

        // Prepare shared preferences
        mSharedPreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        initializeViews();
        initializeButtons();
    }

    public void initializeViews() {
        mConstraintLayout = findViewById(R.id.help_constraint_layout);
        mButtonSendLog = findViewById(R.id.help_button_send_log);
    }

    public void initializeButtons() {
        mButtonSendLog.setOnClickListener(v -> {
            Intent emailIntent = GoLogger.sendLogFile(mSharedPreferences.getInt(PREF_SECURITY_USER_ID, -1), "Test Version");
            startActivity(Intent.createChooser(emailIntent, "Send activity log..."));
        });
    }
}