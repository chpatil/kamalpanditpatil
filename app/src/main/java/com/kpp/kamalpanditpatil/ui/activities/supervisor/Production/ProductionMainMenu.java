package com.kpp.kamalpanditpatil.ui.activities.supervisor.Production;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.kpp.kamalpanditpatil.R;

public class ProductionMainMenu extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_casing:
                    startActivity(new Intent(ProductionMainMenu.this,com.kpp.kamalpanditpatil.ui.activities.supervisor.Production.CasingActivity.class));
                    return true;
                case R.id.navigation_cnc:
                    startActivity(new Intent(ProductionMainMenu.this,com.kpp.kamalpanditpatil.ui.activities.supervisor.Production.CNCActivity.class));
                    return true;
                case R.id.navigation_grinding:
                    startActivity(new Intent(ProductionMainMenu.this,com.kpp.kamalpanditpatil.ui.activities.supervisor.Production.GrindingActivity.class));
                    return true;
                case R.id.navigation_qanda:
                    startActivity(new Intent(ProductionMainMenu.this,com.kpp.kamalpanditpatil.ui.activities.supervisor.Production.QandAActivity.class));
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_main_menu);
        Toolbar toolbar=findViewById(R.id.ProductionMainMenuToolbar);
        toolbar.setTitle("PRODUCTION MENU");
        setSupportActionBar(toolbar);


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
