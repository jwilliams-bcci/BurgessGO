package com.burgess.burgessgo.my_homes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.burgess.burgessgo.BaseActivity;
import com.burgess.burgessgo.R;

public class MyHomesActivity extends BaseActivity {
    public static final String TAG = "MY_HOMES";

    // View components

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_homes);
    }
}