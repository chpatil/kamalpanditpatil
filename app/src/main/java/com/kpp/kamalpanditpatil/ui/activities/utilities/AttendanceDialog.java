package com.kpp.kamalpanditpatil.ui.activities.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

public class AttendanceDialog extends Dialog {
    public Activity activity;
    String departmentname, name, databaseDate, code, message;
    Spinner overtime, otshift, shift, pieceordaily;
    Button AttendanceSubmitButton;

    public AttendanceDialog(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        SharedPreferences prefs = context
                .getSharedPreferences("NAMEDEPARTMENTDATE", Context.MODE_PRIVATE);

        name = prefs.getString("name", "");
        departmentname = prefs.getString("deaprtment", "");
        databaseDate = prefs.getString("databaseDate", "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("ATTENDANCE DETAILS");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.attendance_dialog);
        overtime = findViewById(R.id.overtimeSpinner);
        otshift = findViewById(R.id.otShiftspinner);
        shift = findViewById(R.id.shiftSpinner);
        pieceordaily = findViewById(R.id.pieceordailyspinner);
        AttendanceSubmitButton = findViewById(R.id.attendanceSubmitbutton);


        ArrayAdapter<String> adapter_piecerate_role = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, constants.piecerateorDailyString);
        adapter_piecerate_role
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pieceordaily.setAdapter(adapter_piecerate_role);


        ArrayAdapter<String> adaptershift_role = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, constants.shiftString);
        adaptershift_role
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shift.setAdapter(adaptershift_role);


        ArrayAdapter<String> adapetovertime_role = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, constants.otshiftString);
        adaptershift_role
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        otshift.setAdapter(adapetovertime_role);

        ArrayAdapter<String> adapter_role = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, constants.presentORabsentString);
        adapter_role
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        overtime.setAdapter(adapter_role);


        AttendanceSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.ATTENDANCEINSERT, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            code = jsonObject.getString("code");
                            message = jsonObject.getString("message");
                            if (code.equals("0")) {
                                Toast.makeText(getContext(), "Attendance not submitted", Toast.LENGTH_SHORT).show();
                            } else if (code.equals("1")) {
                                Toast.makeText(getContext(), "Attendance Submitted", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }


                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        String shift1 = (String) shift.getSelectedItem();
                        String piecerate = (String) pieceordaily.getSelectedItem();
                        String otShift = (String) otshift.getSelectedItem();
                        String overtime1 = (String) overtime.getSelectedItem();
                        Map<String, String> datamap = new HashMap<String, String>();

                        datamap.put("name", name);
                        datamap.put("shift", shift1);
                        datamap.put("dailyorpiece", piecerate);
                        datamap.put("overtime", overtime1);
                        datamap.put("overtimeshift", otShift);
                        datamap.put("date", databaseDate);
                        datamap.put("department", departmentname);

                        return datamap;
                    }
                };
                com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(getContext()).addtoRequestQueue(stringRequest);
            }
        });

    }
}
