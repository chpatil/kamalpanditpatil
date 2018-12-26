package com.kpp.kamalpanditpatil.ui.activities.supervisor.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.kpp.kamalpanditpatil.R;

public class MenuActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_registration:
                    startActivity(new Intent(MenuActivity.this,RegisterActivity.class));
                    finish();
                    return true;
                case R.id.navigation_attendance:
                    startActivity(new Intent(MenuActivity.this, com.kpp.kamalpanditpatil.ui.activities.supervisor.Attendance.AttendanceBaseActivity.class));
                    finish();
                    return true;
                case R.id.navigation_production:
                    startActivity(new Intent(MenuActivity.this,com.kpp.kamalpanditpatil.ui.activities.supervisor.Production.ProductionMainMenu.class));
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar=findViewById(R.id.mainMenuToolbar);
        toolbar.setTitle("SUPERVISOR");
        setSupportActionBar(toolbar);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, com.kpp.kamalpanditpatil.ui.activities.login_page.class));
        finish();
    }
}
