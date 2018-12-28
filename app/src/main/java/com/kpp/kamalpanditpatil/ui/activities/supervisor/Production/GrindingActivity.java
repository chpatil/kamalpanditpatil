package com.kpp.kamalpanditpatil.ui.activities.supervisor.Production;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GrindingActivity extends AppCompatActivity {
    @BindView(R.id.GrindingProductionTextView)
    TextView GrindingproductionTextview;
    @BindView(R.id.GrindingProductionEditText)
    EditText Grindingproduction;
    @BindView(R.id.GrindingSubmitButton)
    Button grindingButton;
    @BindView(R.id.DateButton)
    Button dateButton;
    String Date,dateDatabase;
    String dayS;
    int year_x,month_x,day_x;
    int day,month, dyear;
    static final int Dialog_id=0;
    String code,message;
    android.support.v7.app.AlertDialog.Builder builder;
    String GrindingProduction;
    ProgressDialog pDialog;
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            selectedMonth = selectedMonth + 1;
            if (selectedDay < 10) {
                dayS = "0" + String.valueOf(selectedDay);

            } else {
                dayS = String.valueOf(selectedDay);
            }
            Date = selectedYear + "-" + (selectedMonth) + "-" + dayS;
            month_x = selectedMonth;
            year_x = selectedYear;
            dateButton.setText(selectedYear + "-" + (selectedMonth) + "-" + dayS);
            dateDatabase = selectedMonth + "-" + selectedYear;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder = new AlertDialog.Builder(this);
        setContentView(R.layout.activity_grinding);
        ButterKnife.bind(this);
        pDialog = new ProgressDialog(this);
        Toolbar toolbar=findViewById(R.id.grindingToolbar);
        toolbar.setTitle("GRINDING");
        setSupportActionBar(toolbar);
        final Calendar cal = Calendar.getInstance();
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        month_x=month_x+1;
        day_x=cal.get(Calendar.DAY_OF_MONTH);
        if(day_x<10){
            dayS ="0"+day_x;
        }else{
            dayS =String.valueOf(day_x);
        }
        dateDatabase=month_x+"-"+year_x;
        Date = year_x + "-" + month_x + "-" + dayS;
        dateButton.setText(Date);
        grindingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GrindingProduction = String.valueOf(Grindingproduction.getText());
                if (TextUtils.isEmpty(GrindingProduction)) {
                    Grindingproduction.setError("It cannot be empty");
                } else {
                    pDialog.setMessage("submitting data");
                    pDialog.show();
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, constants.GRINDINGURL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject=jsonArray.getJSONObject(0);
                                code=jsonObject.getString("code");
                                message=jsonObject.getString("message");
                                if(code.equals("0")){
                                    pDialog.dismiss();
                                    Toast.makeText(GrindingActivity.this, message, Toast.LENGTH_SHORT).show();
                                } else if (code.equals("1")) {
                                    pDialog.dismiss();
                                    Toast.makeText(GrindingActivity.this, "submitted successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(GrindingActivity.this, ProductionMainMenu.class));
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(GrindingActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }



                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            String production=GrindingproductionTextview.getText().toString();
                            Map<String,String> datamap=new HashMap<String,String>();
                            datamap.put("production", GrindingProduction);
                            datamap.put("date",Date);
                            return datamap;
                        }
                    };
                    com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(GrindingActivity.this).addtoRequestQueue(stringRequest);
                }
            }});

//        final Calendar calendar=new GregorianCalendar();
//        final Date date=calendar.getInstance().getTime();

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

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, dyear, month, day);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ProductionMainMenu.class));
        finish();
    }
}
