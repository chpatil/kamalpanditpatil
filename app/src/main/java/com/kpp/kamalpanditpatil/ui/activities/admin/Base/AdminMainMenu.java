package com.kpp.kamalpanditpatil.ui.activities.admin.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.kpp.kamalpanditpatil.R;
import com.kpp.kamalpanditpatil.ui.activities.admin.Attendance.AdminAttendanceBaseActivity;
import com.kpp.kamalpanditpatil.ui.activities.login_page;

public class AdminMainMenu extends AppCompatActivity {
//    @BindView(R.id.adminMainMenuToolbar)
//    Toolbar toolbar;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_admin_editing:
                    startActivity(new Intent(AdminMainMenu.this,worker_list.class));
                    finish();
                    mTextMessage.setText(R.string.EDITING);
                    return true;
                case R.id.navigation__admin_attendance:
                    startActivity(new Intent(AdminMainMenu.this, AdminAttendanceBaseActivity.class));
                    finish();
                    mTextMessage.setText(R.string.ATTENDANCE);
                    return true;
                case R.id.navigation_admin_production:
                    mTextMessage.setText(R.string.PRODUCTION);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_menu);

        Toolbar toolbar=findViewById(R.id.adminMainMenuToolbar);
        toolbar.setTitle("ADMIN");
        setSupportActionBar(toolbar);
        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, login_page.class));
        finish();
    }
}
