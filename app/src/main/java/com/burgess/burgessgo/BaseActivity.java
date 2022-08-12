package com.burgess.burgessgo;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.burgess.burgessgo.activate_homes.ActivateHomesActivity;
import com.burgess.burgessgo.deactivate_homes.DeactivateHomesActivity;
import com.burgess.burgessgo.my_homes.MyHomesActivity;
import com.burgess.burgessgo.non_passed_inspections.NonPassedInspectionsActivity;
import com.burgess.burgessgo.request_home_access.RequestHomeAccessActivity;
import com.burgess.burgessgo.share_transfer_homes.ShareTransferHomesActivity;
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
                intent = new Intent(this, MyHomesActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuItem_activateHomes:
                intent = new Intent(this, ActivateHomesActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuItem_deactivateHomes:
                intent = new Intent(this, DeactivateHomesActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuItem_shareHomes:
                intent = new Intent(this, ShareTransferHomesActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuItem_requestHome:
                intent = new Intent(this, RequestHomeAccessActivity.class);
                startActivity(intent);
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
