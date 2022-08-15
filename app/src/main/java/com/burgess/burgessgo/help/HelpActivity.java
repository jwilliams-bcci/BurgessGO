package com.burgess.burgessgo.help;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.burgess.burgessgo.BaseActivity;
import com.burgess.burgessgo.R;

public class HelpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setSupportActionBar(findViewById(R.id.help_toolbar));
    }
}