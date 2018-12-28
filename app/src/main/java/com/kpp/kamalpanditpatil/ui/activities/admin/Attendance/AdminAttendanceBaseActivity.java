package com.kpp.kamalpanditpatil.ui.activities.admin.Attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.kpp.kamalpanditpatil.R;
import com.kpp.kamalpanditpatil.ui.activities.admin.Base.AdminMainMenu;

public class AdminAttendanceBaseActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.attendance_navigation_casing:
                    startActivity(new Intent(AdminAttendanceBaseActivity.this, AdminAttendanceCasingActivity.class));
                    finish();
                    return true;
                case R.id.attendance_navigation_cnc:
                    startActivity(new Intent(AdminAttendanceBaseActivity.this, AdminAttendanceCNCActivity.class));
                    finish();
                    return true;
                case R.id.attendance_navigation_grinding:
                    startActivity(new Intent(AdminAttendanceBaseActivity.this, AdminAttendanceGrindingActivity.class));
                    finish();
                    return true;
                case R.id.attendance_navigation_qanda:
                    startActivity(new Intent(AdminAttendanceBaseActivity.this, AdminAttendanceQandAActivity.class));
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_attendance_base);
        Toolbar toolbar = findViewById(R.id.adminattendancebaseToolbar);
        toolbar.setTitle("ADMIN ATTENDANCE");

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.attendancemenu);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, AdminMainMenu.class));
        finish();
    }
}
