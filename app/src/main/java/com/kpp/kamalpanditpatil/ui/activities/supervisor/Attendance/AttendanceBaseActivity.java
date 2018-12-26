package com.kpp.kamalpanditpatil.ui.activities.supervisor.Attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kpp.kamalpanditpatil.R;
import com.kpp.kamalpanditpatil.constants.constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AttendanceBaseActivity extends AppCompatActivity {
    int count;
    private TextView mTextMessage;
    String code, message;
    private Button Deletebutton;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.attendance_navigation_casing:
                    startActivity(new Intent(AttendanceBaseActivity.this, AttendanceCasingActivity.class));
                    finish();
                    return true;
                case R.id.attendance_navigation_cnc:
                    startActivity(new Intent(AttendanceBaseActivity.this, AttendanceCNCActivity.class));
                    finish();
                    return true;
                case R.id.attendance_navigation_grinding:
                    startActivity(new Intent(AttendanceBaseActivity.this, AttendanceGrindingActivity.class));
                    finish();
                    return true;
                case R.id.attendance_navigation_qanda:
                    startActivity(new Intent(AttendanceBaseActivity.this, AttendanceQandAActivity.class));
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
        Toolbar toolbar = findViewById(R.id.attendancetoolbar);
        toolbar.setTitle("ATTENDANCE");
        setSupportActionBar(toolbar);
        Deletebutton = findViewById(R.id.deleteattendanceButton);
        mTextMessage = findViewById(R.id.countTextview);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        count = 0;

        Deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count < 5) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.ATTENDANCEDELETE, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                code = jsonObject.getString("code");
                                if (code.equals("0")) {
                                    Toast.makeText(AttendanceBaseActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                } else if (code.equals("1")) {
                                    Toast.makeText(AttendanceBaseActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                    count++;
                                    mTextMessage.setText("No. of entries deleted:" + count);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
                    com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(AttendanceBaseActivity.this).addtoRequestQueue(stringRequest);
                } else {
                    Toast.makeText(AttendanceBaseActivity.this, "You cannot delete more entries", Toast.LENGTH_LONG).show();
                    Deletebutton.setEnabled(false);

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, com.kpp.kamalpanditpatil.ui.activities.supervisor.Base.MenuActivity.class));
        finish();
    }
}

