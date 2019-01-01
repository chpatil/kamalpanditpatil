package com.kpp.kamalpanditpatil.ui.activities.utilities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class AttendanceListActivity extends AppCompatActivity {
    static final int Dialog_id = 0;
    public final String TAG = "Attendance.casing.worker_list";
    // Listview Adapter
    ArrayAdapter<String> adapter;
    // Search EditText
    EditText inputSearch;
    String code, startdate, enddate, departmentname;
    AlertDialog.Builder builder;
    Button dateButton;
    // ArrayList for Listview
    ArrayList<String> workerlist;
    //progressdialog
    ProgressDialog pDialog;
    int year_x, month_x, day_x;
    int day, month, dyear;
    AttendanceDialog attendanceDialog;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);
        Toolbar toolbar = findViewById(R.id.attendanceperosnwiseworkerListToolbar);
        startdate = getIntent().getStringExtra("from");
        enddate = getIntent().getStringExtra("to");
        departmentname = getIntent().getStringExtra("department");
        toolbar.setTitle(departmentname);
        setSupportActionBar(toolbar);
        workerlist = new ArrayList<String>();
        dateButton = findViewById(R.id.AttendanceCasingdateButton);
        lv = findViewById(R.id.list_view);
        inputSearch = findViewById(R.id.inputsearch);

        // Adding items to listview
        pDialog = new ProgressDialog(this);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, workerlist);

        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        // Creating volley request obj

        StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.ATTENDANCEDEPARTMENTWORKERLIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    code = jsonObject.getString("code");
                    if (code.equals("0")) {
                        Toast.makeText(AttendanceListActivity.this, "Attendance not submitted", Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    } else if (code.equals("1")) {
//
                        lv.setAdapter(adapter);
                        JSONArray jAry = new JSONArray(response);
                        for (int i = 1; i < jAry.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            worker_model worker = new worker_model();
                            worker.setName(jsonObject1.getString("name"));
                            String name = worker.getName();
                            workerlist.add(name);
                            adapter.notifyDataSetChanged();
                        }

                        hidePDialog();


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
                Map<String, String> datamap = new HashMap<String, String>();

                datamap.put("startdate", startdate);
                datamap.put("enddate", enddate);
                datamap.put("department", departmentname);

                return datamap;
            }
        };
        com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(this).addtoRequestQueue(stringRequest);
        // Adding request to request queue


        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                AttendanceListActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        //get selectedname from the list
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                lv.setAdapter(adapter);
                String value = adapter.getItem(position);
                Intent intent = new Intent(AttendanceListActivity.this, AttendancePersonwiseDetails.class);
                intent.putExtra("startdate", startdate);
                intent.putExtra("enddate", enddate);
                intent.putExtra("department", departmentname);
                intent.putExtra("name", value);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.adminnavigation, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, AdminAttendanceBaseActivity.class));
        finish();
    }
}