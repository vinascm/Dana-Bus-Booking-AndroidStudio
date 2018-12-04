package com.example.tweak.danabusbooking;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tweak.danabusbooking.alarm_util.AlarmReceiver;
import com.example.tweak.danabusbooking.home.FunctionViewPageFragment1;
import com.example.tweak.danabusbooking.home.FunctionViewPageFragment2;
import com.example.tweak.danabusbooking.home.FunctionViewPageFragment3;
import com.example.tweak.danabusbooking.home.FunctionViewPageFragment4;
import com.example.tweak.danabusbooking.utils.SharedVariables;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ViewPager functionViewPager;
    private BottomNavigationView bottomNavigationView;

    private boolean m_DoubleBackToExitOnePress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Auto reset on server data every day
        settingAutomaticallyUpdateData();

        //getting bottom navigation view and attaching the listener
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        //loading the default fragment and after booking ticket
        if (SharedVariables.lastIndexFunctionPress == 2) {
            loadFragment(new FunctionViewPageFragment3());
            bottomNavigationView.setSelectedItemId(R.id.historyMenuAction);
        } else loadFragment(new FunctionViewPageFragment1());

    }

    // Override this to handle exit behaviour
    @Override
    public void onBackPressed() {
        if (m_DoubleBackToExitOnePress) {
            super.onBackPressed();

            finish();
            return;
        }

        m_DoubleBackToExitOnePress = true;

        Toast.makeText(getBaseContext(), "Click BACK again to exit!", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                m_DoubleBackToExitOnePress = false;
            }
        }, 2000);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.mapMenuAction:
                fragment = new FunctionViewPageFragment1();
                break;

            case R.id.searchMenuAction:
                fragment = new FunctionViewPageFragment2();
                break;

            case R.id.historyMenuAction:
                fragment = new FunctionViewPageFragment3();
                break;

            case R.id.moreMenuAction:
                fragment = new FunctionViewPageFragment4();
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.functionFrameLayout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void settingAutomaticallyUpdateData() {
        // Define our intention of executing AlarmReceiver
        Intent alertIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, alertIntent, 0);

        // Allows us to schedule for application to do something at a later date
        // even if it is in the background or isn't active
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Set the alarm to start at 23:59 PM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);

        // setRepeating() lets us specify a precise custom interval--in this case
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);
    }

}
