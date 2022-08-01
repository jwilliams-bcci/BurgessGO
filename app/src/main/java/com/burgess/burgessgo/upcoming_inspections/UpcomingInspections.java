package com.burgess.burgessgo.upcoming_inspections;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.burgess.burgessgo.R;

public class UpcomingInspections extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_inspections);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.upcoming_inspections_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.showOverflowMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onPrepareOptionsMenu(menu);
    }
}