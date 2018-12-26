package com.kpp.kamalpanditpatil.ui.activities.supervisor.Production;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.kpp.kamalpanditpatil.ui.activities.supervisor.Attendance.AttendanceBaseActivity;
import com.kpp.kamalpanditpatil.ui.activities.utilities.AttendanceDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class QandAActivity extends AppCompatActivity {
    static final int Dialog_id = 0;
    public final String TAG = "casing.worker_list";
    ArrayAdapter<String> adapter;
    // Search EditText
    EditText inputSearch;
    String code, message, name, Day, Date, DatabaseDate;
    AlertDialog.Builder builder;
    Button dateButton;
    // ArrayList for Listview
    ArrayList<String> workerlist;
    //progressdialog
    ProgressDialog pDialog;
    int year_x, month_x, day_x;
    int day, month, dyear;
    AttendanceDialog attendanceDialog;
    String department;
    private ListView lv;
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            selectedMonth = selectedMonth + 1;
            if (selectedDay < 10) {
                Day = "0" + selectedDay;

            } else {
                Day = String.valueOf(selectedDay);
            }
            month_x = selectedMonth;
            year_x = selectedYear;
            dateButton.setText(Day + "-" + (selectedMonth) + "-" + selectedYear);
            DatabaseDate = selectedYear + "-" + (selectedMonth) + "-" + Day;
            adapter.clear();
            serverretrieval();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qand_a);
        Toolbar toolbar = findViewById(R.id.QandAactivityToolbar);
        toolbar.setTitle("Q AND A");
        setSupportActionBar(toolbar);
        workerlist = new ArrayList<String>();
        dateButton = findViewById(R.id.QandAdateButton);
        lv = findViewById(R.id.list_view);
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        month_x = month_x + 1;
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        if (day_x < 10) {
            Day = "0" + day_x;
        } else {
            Day = String.valueOf(day_x);
        }
        Date = Day + "-" + month_x + "-" + year_x;
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        dyear = cal.get(Calendar.YEAR);
        DatabaseDate = year_x + "-" + month_x + "-" + Day;
        dateButton.setText(Date);
        inputSearch = findViewById(R.id.inputsearch);

        // Adding items to listview
        pDialog = new ProgressDialog(this);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, workerlist);

        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        // Creating volley request obj

        serverretrieval();
        // Adding request to request queue


        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                QandAActivity.this.adapter.getFilter().filter(cs);
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
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Dialog_id);
            }
        });
    }

    private void serverretrieval() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.QANDAPRODUCTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    code = jsonObject.getString("code");
                    if (code.equals("0")) {
                        builder.setTitle("0");
                        Toast.makeText(QandAActivity.this, "Failure to submit", Toast.LENGTH_SHORT).show();
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
                datamap.put("date", DatabaseDate);
                return datamap;

            }
        };
        com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(this).addtoRequestQueue(stringRequest);
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
        startActivity(new Intent(this, AttendanceBaseActivity.class));
        finish();
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, dyear, month, day);
    }
}
