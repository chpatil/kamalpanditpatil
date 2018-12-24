package com.kpp.kamalpanditpatil.ui.activities.supervisor.Production;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.kpp.kamalpanditpatil.R;

public class CNCActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cnc);
        Toolbar toolbar=findViewById(R.id.CNCtoolbar);
        toolbar.setTitle("C.N.C");
        setSupportActionBar(toolbar);

    }
}
