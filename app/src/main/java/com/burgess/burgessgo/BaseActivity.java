package com.burgess.burgessgo;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.burgess.burgessgo.non_passed_inspections.NonPassedInspectionsActivity;
import com.burgess.burgessgo.upcoming_inspections.UpcomingInspectionsActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuItem_upcomingInspections:
                intent = new Intent(this, UpcomingInspectionsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuItem_nonPassedInspections:
                intent = new Intent(this, NonPassedInspectionsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuItem_myHomes:
                Toast.makeText(this, "Clicked My Homes", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItem_activateHomes:
                Toast.makeText(this, "Clicked Activate Homes", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItem_deactivateHomes:
                Toast.makeText(this, "Clicked Deactivate Homes", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItem_shareHomes:
                Toast.makeText(this, "Clicked Share/Transfer Homes", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItem_requestHome:
                Toast.makeText(this, "Clicked Request Homes", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItem_help:
                Toast.makeText(this, "Clicked Help", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItem_changePassword:
                Toast.makeText(this, "Clicked Change Password", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItem_logout:
                Toast.makeText(this, "Clicked Logout", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
