package com.kpp.kamalpanditpatil.ui.activities.utilities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kpp.kamalpanditpatil.R;
import com.kpp.kamalpanditpatil.constants.constants;
import com.kpp.kamalpanditpatil.models.worker_model;
import com.kpp.kamalpanditpatil.ui.activities.admin.Attendance.AdminAttendanceBaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttendancePersonwiseDetails extends AppCompatActivity {
    ArrayList<String> workerDataList;
    ArrayList<String> workerDataTransferList;
    String startdate, enddate, department, code, name;
    worker_model worker;
    ProgressDialog pDiaalog;
    LstViewPersonwiseAdapter adapter;
    private ListView lv1;
    private Button EditButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_personwise_details);
        startdate = getIntent().getStringExtra("startdate");
        enddate = getIntent().getStringExtra("enddate");
        department = getIntent().getStringExtra("department");
        name = getIntent().getStringExtra("name");
        Toolbar toolbar = findViewById(R.id.adminAttendacePersonwiseDetailsToolbar);
        toolbar.setTitle(name);
        lv1 = findViewById(R.id.workerPersonwiseList);
        workerDataList = new ArrayList<String>();
        pDiaalog = new ProgressDialog(this);
        pDiaalog.setMessage("Fetching data ...");
        pDiaalog.show();
        workerDataList.clear();
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.header_admin_attendance_personwise_details_layout, lv1, false);
        // Add header view to the ListView
        lv1.addHeaderView(headerView);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.ATTENDANCEADMINPERSONWISE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    code = jsonObject.getString("code");
                    if (code.equals("0")) {
                        Toast.makeText(AttendancePersonwiseDetails.this, "Unable to fetch data", Toast.LENGTH_SHORT).show();
                    } else if (code.equals("1")) {
                        JSONArray jAry = new JSONArray(response);
                        for (int i = 1; i < jAry.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String name = jsonObject1.get("date") + "__" + jsonObject1.get("shift") + "__" + jsonObject1.get("dailyorpiece");
                            workerDataList.add(name);
                        }
                        adapter = new LstViewPersonwiseAdapter(AttendancePersonwiseDetails.this, R.layout.rowlayout_attendance_personwise_details, R.id.personwise_dates, workerDataList);
                        lv1.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        pDiaalog.dismiss();


                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data


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
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> datamap = new HashMap<String, String>();
                datamap.put("name", name);
                datamap.put("startdate", startdate);
                datamap.put("enddate", enddate);
                datamap.put("department", department);
                return datamap;
            }
        };
        com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(this).addtoRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, AdminAttendanceBaseActivity.class));
    }
}
