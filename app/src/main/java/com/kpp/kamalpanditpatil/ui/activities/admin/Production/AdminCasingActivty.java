package com.kpp.kamalpanditpatil.ui.activities.admin.Production;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.kpp.kamalpanditpatil.R;


public class AdminCasingActivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_casing_activty);
        Toolbar toolbar=findViewById(R.id.adminCasingToolbar);
        toolbar.setTitle("CASING");
        setSupportActionBar(toolbar);
    }
}
