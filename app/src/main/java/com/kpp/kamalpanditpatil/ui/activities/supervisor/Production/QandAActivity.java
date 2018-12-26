package com.kpp.kamalpanditpatil.ui.activities.supervisor.Production;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.kpp.kamalpanditpatil.R;

public class QandAActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qand_a);
        Toolbar toolbar=findViewById(R.id.adminProductionMainMenuToolbar);
        toolbar.setTitle("ADMIN PRODUCTION");
        setSupportActionBar(toolbar);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ProductionMainMenu.class));
        finish();
    }
}
