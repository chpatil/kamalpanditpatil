package com.kpp.kamalpanditpatil.ui.activities.supervisor.Attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.kpp.kamalpanditpatil.R;


public class AttendanceBaseActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.attendance_navigation_casing:
                    startActivity(new Intent(AttendanceBaseActivity.this, com.kpp.kamalpanditpatil.ui.activities.supervisor.Production.CasingActivity.class));
                    finish();
                    return true;
                case R.id.attendance_navigation_cnc:
                    startActivity(new Intent(AttendanceBaseActivity.this, com.kpp.kamalpanditpatil.ui.activities.supervisor.Production.CNCActivity.class));
                    finish();
                    return true;
                case R.id.attendance_navigation_grinding:
                    startActivity(new Intent(AttendanceBaseActivity.this, com.kpp.kamalpanditpatil.ui.activities.supervisor.Production.GrindingActivity.class));
                    finish();
                    return true;
                case R.id.attendance_navigation_qanda:
                    startActivity(new Intent(AttendanceBaseActivity.this, com.kpp.kamalpanditpatil.ui.activities.supervisor.Production.QandAActivity.class));
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_base);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
