package com.kpp.kamalpanditpatil.ui.activities.admin.Production;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kpp.kamalpanditpatil.R;

import java.util.ArrayList;

public class AdminGrindingActivity extends AppCompatActivity {
    TextView dateTextview;
    ListView listView;
    Button Graphintent, dateButton;
    int year_x, month_x, day_x;
    int day, month, dyear;
    String startdate, enddate;
    ProgressDialog mProgressDialog;
    ArrayList<String> searchArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_grinding);
        Toolbar toolbar=findViewById(R.id.adminGrindingToolbar);
        toolbar.setTitle("GRINDING");
        setSupportActionBar(toolbar);
        dateButton = findViewById(R.id.adminGrindingDateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();

            }
        });
        Graphintent = findViewById(R.id.Graphbutton);
        Graphintent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminGrindingActivity.this, com.kpp.kamalpanditpatil.ui.activities.utilities.GrindingGraphview.class);
                intent.putExtra("startdate", startdate);
                intent.putExtra("enddate", enddate);
                startActivity(intent);

            }
        });

        // getting the data from UserNode at Firebase and then adding the searchArrayList in Arraylist and setting it to Listview
        showProgressDialog();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, searchArrayList);
        listView.setAdapter(adapter);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(AdminGrindingActivity.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
