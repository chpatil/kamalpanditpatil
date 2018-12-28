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

public class AttendanceDetailActivity extends AppCompatActivity {
    ArrayList<String> workerDataList;
    ArrayList<String> workerDataTransferList;
    String date, department, code, message;
    worker_model worker;
    ProgressDialog pDiaalog;
    LstViewDatewiseAdpater adapter;
    private ListView lv1;
    private Button EditButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_detail);
        date = getIntent().getStringExtra("date");
        department = getIntent().getStringExtra("department");
        Toolbar toolbar = findViewById(R.id.adminDetailsToolbar);
        toolbar.setTitle(date);
        lv1 = findViewById(R.id.workerDatewiseList);
        workerDataList = new ArrayList<String>();
        pDiaalog = new ProgressDialog(this);
        pDiaalog.setMessage("Fetching data ...");
        pDiaalog.show();
        workerDataList.clear();
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.header_admin_attendance_detail_layout, lv1, false);
        // Add header view to the ListView
        lv1.addHeaderView(headerView);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.ATTENDACEADMINDATEWISE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    code = jsonObject.getString("code");
                    if (code.equals("0")) {
                        Toast.makeText(AttendanceDetailActivity.this, "Unable to fetch data", Toast.LENGTH_SHORT).show();
                    } else if (code.equals("1")) {
                        JSONArray jAry = new JSONArray(response);
                        for (int i = 1; i < jAry.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String name = jsonObject1.get("name") + "__" + jsonObject1.get("shift") + "__" + jsonObject1.get("dailyorpiece");
                            workerDataList.add(name);
                        }
                        adapter = new LstViewDatewiseAdpater(AttendanceDetailActivity.this, R.layout.rowlayout_admin_atttendance_datewise_detail, R.id.names, workerDataList);
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
                datamap.put("date", date);
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
