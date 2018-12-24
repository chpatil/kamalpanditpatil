package com.kpp.kamalpanditpatil.ui.activities.supervisor.Production;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kpp.kamalpanditpatil.R;
import com.kpp.kamalpanditpatil.constants.constants;
import com.kpp.kamalpanditpatil.ui.activities.supervisor.Base.MenuActivity;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grinding);
        ButterKnife.bind(this);
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
        Date=dayS+"-"+month_x+"-"+year_x;
        dateButton.setText(Date);
        grindingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String GrindingProduction = String.valueOf(Grindingproduction.getText());
                if (TextUtils.isEmpty(GrindingProduction)) {
                    Grindingproduction.setError("It cannot be empty");
                } else {
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, constants.GRINDINGURL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject=jsonArray.getJSONObject(0);
                                code=jsonObject.getString("code");
                                message=jsonObject.getString("message");
                                if(code.equals("0")){
                                    builder.setTitle("submision Error ....");
                                    dispalyAlert(message);
                                    update();
                                } else if (code.equals("1")) {
                                    startActivity(new Intent(GrindingActivity.this,MenuActivity.class));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dispalyAlert(error.getMessage());
                            update();
                        }



                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            String production=GrindingproductionTextview.getText().toString();
                            Map<String,String> datamap=new HashMap<String,String>();
                            datamap.put("production",production);
                            datamap.put("date",Date);
                            return datamap;
                        }
                    };
                    com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(GrindingActivity.this).addtoRequestQueue(stringRequest);

                    startActivity(new Intent(GrindingActivity.this,MenuActivity.class));
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
    private void update(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, constants.GRINDINGUPDATEURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    code=jsonObject.getString("code");
                    message=jsonObject.getString("message");
                    if(code.equals("0")){
                        builder.setTitle("submision Error ....");
                        dispalyAlert(message);
                    } else if (code.equals("1")) {
                        startActivity(new Intent(GrindingActivity.this,MenuActivity.class));
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



        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String production=GrindingproductionTextview.getText().toString();
                Map<String,String> datamap=new HashMap<String,String>();
                datamap.put("production",production);
                datamap.put("date",Date);
                return datamap;
            }
        };
        com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(GrindingActivity.this).addtoRequestQueue(stringRequest);

        startActivity(new Intent(GrindingActivity.this,MenuActivity.class));
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, dyear, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            selectedMonth = selectedMonth + 1;
            if (selectedDay < 10) {
                dayS ="0"+ String.valueOf(selectedDay);

            } else {
                dayS =String.valueOf( selectedDay);
            }
            Date = dayS + "-" + (selectedMonth) + "-" + selectedYear;
            month_x=selectedMonth;
            year_x=selectedYear;
            dateButton.setText(dayS + "-" + (selectedMonth) + "-"
                    + selectedYear);
            dateDatabase = selectedMonth + "-" + selectedYear;
        }
    };
    private  void dispalyAlert(String Message){
        builder.setMessage(Message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        android.support.v7.app.AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    }
