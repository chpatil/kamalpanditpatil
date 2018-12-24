package com.kpp.kamalpanditpatil.ui.activities.admin.Production;

import android.content.Intent;
import android.os.Bundle;
import com.kpp.kamalpanditpatil.R;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

public class productionAdminMenuActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_admin_casing:
                    startActivity(new Intent(productionAdminMenuActivity.this,com.kpp.kamalpanditpatil.ui.activities.admin.Production.AdminCasingActivty.class));
                    return true;
                case R.id.navigation_admin_cnc:
                    startActivity(new Intent(productionAdminMenuActivity.this,com.kpp.kamalpanditpatil.ui.activities.admin.Production.AdminCNCActivity.class));
                    return true;
                case R.id.navigation_admin_grinding:
                    startActivity(new Intent(productionAdminMenuActivity.this,com.kpp.kamalpanditpatil.ui.activities.admin.Production.AdminGrindingActivity.class));
                    return true;
                case R.id.navigation_admin_qanda:
                    startActivity(new Intent(productionAdminMenuActivity.this,com.kpp.kamalpanditpatil.ui.activities.admin.Production.AdminQandAActivity.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_admin_menu);
        Toolbar toolbar=findViewById(R.id.adminProductionMainMenuToolbar);
        toolbar.setTitle("ADMIN PRODUCTION");
        setSupportActionBar(toolbar);
        

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
