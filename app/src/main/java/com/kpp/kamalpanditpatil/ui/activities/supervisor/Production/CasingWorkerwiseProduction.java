package com.kpp.kamalpanditpatil.ui.activities.supervisor.Production;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CasingWorkerwiseProduction extends AppCompatActivity {
    static final int Dialog_id = 0;
    @BindView(R.id.dateProductionCasingButton)
    Button dateButton;
    @BindView(R.id.casing_worker_listview)
    ListView casing_listview;
    String DatabaseDate, departmentname;
    String Date, Day;
    int year_x, month_x, day_x;
    int day, month, dyear;
    String value, name, code, message;
    android.support.v7.app.AlertDialog.Builder builder;
    ProgressDialog pDialog;
    private EditText editTextProduction;
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
        setContentView(R.layout.activity_casing_workerwise_production);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.casingWorkerProductiontoolbar);
        name = getIntent().getStringExtra("value");
        toolbar.setTitle(name);
        pDialog = new ProgressDialog(this);
        setSupportActionBar(toolbar);
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
        DatabaseDate = year_x + "-" + month_x + "-" + Day;
        Date = Day + "-" + month_x + "-" + year_x;
        dateButton.setText(Date);
        departmentname = "Casing";
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.Casing, android.R.layout.simple_list_item_1);
        casing_listview.setAdapter(adapter);
        casing_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                value = casing_listview.getItemAtPosition(position).toString();
                opendialog();
            }
        });

        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        dyear = cal.get(Calendar.YEAR);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Dialog_id);
            }
        });
    }

    private void opendialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.cnc_casing_input_dialog, null);
        editTextProduction = view.findViewById(R.id.productionTypeEditTExt);
        builder.setView(view)
                .setTitle("CASING PRODUCTION").
                setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                pDialog.setMessage("Loading...");
                                pDialog.show();
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.CASINGWORKERWISEPRODUCTION, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            code = jsonObject.getString("code");
                                            message = jsonObject.getString("message");
                                            if (code.equals("0")) {
                                                pDialog.dismiss();
                                                Toast.makeText(CasingWorkerwiseProduction.this, "Submission error", Toast.LENGTH_SHORT).show();
                                            } else if (code.equals("1")) {
                                                pDialog.dismiss();
                                                Toast.makeText(CasingWorkerwiseProduction.this, "submitted successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(CasingWorkerwiseProduction.this, CasingWorkerList.class));
                                                finish();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        dispalyAlert(error.getMessage());
                                    }


                                }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        String production = editTextProduction.getText().toString();
                                        Map<String, String> datamap = new HashMap<String, String>();
                                        datamap.put("production", production);
                                        datamap.put("type", value);
                                        datamap.put("date", DatabaseDate);
                                        datamap.put("name", name);
                                        return datamap;
                                    }
                                };
                                com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(CasingWorkerwiseProduction.this).addtoRequestQueue(stringRequest);
                            }
                        });
        builder.show();
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, dyear, month, day);
    }

    private void dispalyAlert(String Message) {
        builder.setMessage(Message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, CasingWorkerList.class));
        finish();
    }
}