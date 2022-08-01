package com.burgess.burgessgo.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.burgess.burgessgo.R;
import com.burgess.burgessgo.upcoming_inspections.UpcomingInspections;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN";

    // View components
    private ConstraintLayout mConstraintLayout;
    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private Button mButtonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeButtonListeners();
        initializeDisplayContent();
    }

    private void initializeViews() {
        mConstraintLayout = findViewById(R.id.login_constraint_layout);
        mEditTextUserName = findViewById(R.id.login_editText_userName);
        mEditTextPassword = findViewById(R.id.login_editText_password);
        mButtonLogin = findViewById(R.id.login_button_login);
    }

    private void initializeButtonListeners() {
        mButtonLogin.setOnClickListener(v -> {
            String userName = mEditTextUserName.getText().toString();
            String password = mEditTextPassword.getText().toString();

            Intent upcomingInspectionsIntent = new Intent(LoginActivity.this, UpcomingInspections.class);
            startActivity(upcomingInspectionsIntent);
        });
    }

    private void initializeDisplayContent() {
        mEditTextUserName.onEditorAction(EditorInfo.IME_ACTION_DONE);
        mEditTextPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
    }
}