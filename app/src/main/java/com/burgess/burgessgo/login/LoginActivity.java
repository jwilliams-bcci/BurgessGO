package com.burgess.burgessgo.login;

import static com.burgess.burgessgo.Constants.PREF;
import static com.burgess.burgessgo.Constants.PREF_LOGIN_NAME;
import static com.burgess.burgessgo.Constants.PREF_LOGIN_PASSWORD;
import static com.burgess.burgessgo.Constants.PREF_REMEMBER_CREDENTIALS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.ServerCallback;
import com.burgess.burgessgo.upcoming_inspections.UpcomingInspectionsActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN";

    // View components
    private ConstraintLayout mConstraintLayout;
    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private CheckBox mCheckBoxRememberCredentials;
    private TextView mTextViewVersion;
    private ProgressBar mProgressBar;
    private LinearLayout mLockScreen;

    // Class members
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Prepare shared preferences
        mSharedPreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        initializeViews();
        initializeButtonListeners();
        initializeDisplayContent();
        checkPermissions();
    }

    private void initializeViews() {
        mConstraintLayout = findViewById(R.id.login_constraint_layout);
        mEditTextUserName = findViewById(R.id.login_editText_userName);
        mEditTextPassword = findViewById(R.id.login_editText_password);
        mButtonLogin = findViewById(R.id.login_button_login);
        mCheckBoxRememberCredentials = findViewById(R.id.login_checkBox_remember_credentials);
        mTextViewVersion = findViewById(R.id.login_textView_version);
        mProgressBar = findViewById(R.id.login_progress_bar);
        mLockScreen = findViewById(R.id.login_lock_screen);
    }

    private void initializeButtonListeners() {
        mButtonLogin.setOnClickListener(v -> {
            String userName = mEditTextUserName.getText().toString();
            String password = mEditTextPassword.getText().toString();

            if (mCheckBoxRememberCredentials.isChecked()) {
                mEditor.putBoolean(PREF_REMEMBER_CREDENTIALS, true);
                mEditor.putString(PREF_LOGIN_NAME, userName);
                mEditor.putString(PREF_LOGIN_PASSWORD, password);
            } else {
                mEditor.putBoolean(PREF_REMEMBER_CREDENTIALS, false);
            }
            mEditor.apply();

            // Hide keyboard on button click
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mButtonLogin.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

            loginUser(userName, password);
        });
    }

    private void initializeDisplayContent() {
        // Set the version label
        PackageInfo pInfo = null;
        try {
            pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionName = "Version: " + pInfo.versionName;
        mTextViewVersion.setText(versionName);

        mEditTextUserName.onEditorAction(EditorInfo.IME_ACTION_DONE);
        mEditTextPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);

        if (mSharedPreferences.getBoolean(PREF_REMEMBER_CREDENTIALS, false)) {
            mCheckBoxRememberCredentials.setChecked(true);
            mEditTextUserName.setText(mSharedPreferences.getString(PREF_LOGIN_NAME, "NOT FOUND"));
            mEditTextPassword.setText(mSharedPreferences.getString(PREF_LOGIN_PASSWORD, ""));
        }
    }

    private void loginUser(String userName, String password) {
        showSpinner();

        JsonObjectRequest loginRequest = GoAPIQueue.getInstance(LoginActivity.this).loginUser(userName, password, new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                Intent upcomingInspectionsIntent = new Intent(LoginActivity.this, UpcomingInspectionsActivity.class);
                startActivity(upcomingInspectionsIntent);
                hideSpinner();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_LONG).show();
                hideSpinner();
            }
        });

        GoAPIQueue.getInstance().getRequestQueue().add(loginRequest);
    }

    private void checkPermissions() {
        ArrayList<String> permissionRequests = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionRequests.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionRequests.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionRequests.size() > 0) {
            requestPermissions(permissionRequests.toArray(new String[permissionRequests.size()]), 100);
        }
    }

    private void showSpinner() {
        mProgressBar.setVisibility(View.VISIBLE);
        mLockScreen.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    private void hideSpinner() {
        mProgressBar.setVisibility(View.GONE);
        mLockScreen.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}