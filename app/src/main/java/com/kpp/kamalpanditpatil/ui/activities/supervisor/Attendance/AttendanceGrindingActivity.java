package com.kpp.kamalpanditpatil.ui.activities.supervisor.Attendance;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.kpp.kamalpanditpatil.ui.activities.utilities.AttendanceDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class AttendanceGrindingActivity extends AppCompatActivity {
    static final int Dialog_id = 0;
    public final String TAG = "Attendance.grinding.worker_list";
    // Listview Adapter
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
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_grinding);
        Toolbar toolbar = findViewById(R.id.attendancegrindingworkerListToolbar);
        toolbar.setTitle("GRINDING WORKERS");
        setSupportActionBar(toolbar);
        final String department = constants.GRINDING;
        workerlist = new ArrayList<String>();
        dateButton = findViewById(R.id.AttendancegrindingdateButton);
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
        DatabaseDate = year_x + "-" + month_x + "-" + Day;
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        dyear = cal.get(Calendar.YEAR);
        dateButton.setText(Date);
        inputSearch = findViewById(R.id.inputsearch);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Dialog_id);
            }
        });

        // Adding items to listview
        pDialog = new ProgressDialog(this);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, workerlist);

        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        // Creating volley request obj

        StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.ATTENDANCEGRINDINGINGWORKERLIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    code = jsonObject.getString("code");
                    if (code.equals("0")) {
                        builder.setTitle("0");
                        dispalyAlert(jsonObject.getString("message"));
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
        });
        com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(this).addtoRequestQueue(stringRequest);
        // Adding request to request queue


        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                AttendanceGrindingActivity.this.adapter.getFilter().filter(cs);
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
                SharedPreferences settings = getSharedPreferences("NAMEDEPARTMENTDATE",
                        MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = settings.edit();
                prefEditor.putString("name", value);
                prefEditor.putString("deaprtment", department);
                prefEditor.putString("databaseDate", DatabaseDate);
                prefEditor.commit();
                attendanceDialog = new AttendanceDialog(AttendanceGrindingActivity.this);
                attendanceDialog.show();
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

    private void dispalyAlert(String Message) {
        builder.setMessage(Message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(AttendanceGrindingActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
