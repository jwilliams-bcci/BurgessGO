package com.burgess.burgessgo.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.toolbox.JsonObjectRequest;
import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.ServerCallback;
import com.burgess.burgessgo.upcoming_inspections.UpcomingInspectionsActivity;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN";

    // View components
    private ConstraintLayout mConstraintLayout;
    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private ProgressBar mProgressBar;
    private LinearLayout mLockScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        initializeButtonListeners();
        initializeDisplayContent();
    }

    private void initializeViews() {
        mConstraintLayout = findViewById(R.id.login_constraint_layout);
        mEditTextUserName = findViewById(R.id.login_editText_userName);
        mEditTextPassword = findViewById(R.id.login_editText_password);
        mButtonLogin = findViewById(R.id.login_button_login);
        mProgressBar = findViewById(R.id.login_progress_bar);
        mLockScreen = findViewById(R.id.login_lock_screen);
    }

    private void initializeButtonListeners() {
        mButtonLogin.setOnClickListener(v -> {
            String userName = mEditTextUserName.getText().toString();
            String password = mEditTextPassword.getText().toString();

            // Hide keyboard on button click
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mButtonLogin.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

            loginUser(userName, password);
        });
    }

    private void initializeDisplayContent() {
        mEditTextUserName.onEditorAction(EditorInfo.IME_ACTION_DONE);
        mEditTextPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
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