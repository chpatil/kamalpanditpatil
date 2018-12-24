package com.kpp.kamalpanditpatil.ui.activities.supervisor.Base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.input_name)
    EditText _nameText;
    @BindView(R.id.aadhar)
    EditText _Aadhar;
    @BindView(R.id.input_address)
    EditText _addressText;
    @BindView(R.id.idno)
    EditText _idno;
    @BindView(R.id.IFSC)
    EditText _Ifsc;
    @BindView(R.id.Accountno)
    EditText _Accountno;
    @BindView(R.id.btn_signup)
    Button _signupButton;
    @BindView(R.id.bankname)
    EditText _BankName;
    @BindView(R.id.PFnumber)
    EditText _PFnumber;
    @BindView(R.id.ESICNumber)
    EditText _ESICnumber;
    @BindView(R.id.Spinner_gender)
    Spinner _spinnerGender;
    @BindView(R.id.Spinner_department)
    Spinner _spinnerDepartment;
    String gender,department,IFSC_code,bankname,address,aadhar,name,accountno,esic,pf;
    int id_no;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        builder=new AlertDialog.Builder(this);
        Toolbar toolbar=findViewById(R.id.registerToolbar);
        toolbar.setTitle("REGISTERATION");
        setSupportActionBar(toolbar);



        //spinner Department Defination
        _spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
                department = (String) _spinnerDepartment.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        ArrayAdapter<String> adapter_role = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, constants.departmentRoleString);
        adapter_role
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spinnerDepartment.setAdapter(adapter_role);



        //Spinner Gender Defination
        _spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
                gender = (String) _spinnerGender.getSelectedItem();

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ArrayAdapter<String> adapter_role1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, constants.genderRoleString);
        adapter_role
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spinnerGender.setAdapter(adapter_role1);

        //Worker Registration method


        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }





                //main worker registration method
            public void signup() {
                Log.d(name,"Worker Registration");

                if (!validate()) {
                    onSignupFailed();
                    return;
                }

//        _signupButton.setEnabled(false);

                final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                        R.style.AppTheme);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Creating Worker...");
                progressDialog.show();

                // TODO: Implement your own signup logic here.
                serverSignup();

                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "registration success", Toast.LENGTH_LONG).show();

            }



        });






    }

    private void serverSignup() {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, constants.WORKERREGISTRATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            String code=jsonObject.getString("code");
                            String message =jsonObject.getString("message");
                            builder.setTitle("Server Response ...");
                            if (code.equals("1")){
                                displayAlert("registration success");
                            }else if(code.equals("0")){
                                displayAlert("registration failure");
                            }
                            builder.setMessage(message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                final String name = _nameText.getText().toString();
                final String address = _addressText.getText().toString();
                final String idno = _idno.getText().toString();
                final String Aadhar = _Aadhar.getText().toString();
                String Bankname = _BankName.getText().toString();
                final String Ifsc = _Ifsc.getText().toString();
                String Accountno = _Accountno.getText().toString();
                String ESICnumber=_ESICnumber.getText().toString();
                String PFnumber=_PFnumber.getText().toString();
                gender = (String) _spinnerGender.getSelectedItem();
                department = (String) _spinnerDepartment.getSelectedItem();
                HashMap<String,String> datamap=new HashMap<String,String>();

                datamap.put("name",name);
                datamap.put("address",address);
                datamap.put("aadhar",Aadhar);
                datamap.put("ifsc",Ifsc);
                datamap.put("bankname",Bankname);
                datamap.put("accountno",Accountno);
                datamap.put("department",department);
                datamap.put("gender",gender);
                datamap.put("idno",idno);
                datamap.put("ESIC",ESICnumber);
                datamap.put("PF",PFnumber);
                return  datamap;
            }
        };
        com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(RegisterActivity.this).addtoRequestQueue(stringRequest);

    }
    public void displayAlert(final String code){
        builder.setPositiveButton("ok ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(code.equals("reg_success")){
                    onSignupSuccess();
                }else{
                    onSignupFailed();
                }
            }
        });
    }
    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent intent=new Intent(RegisterActivity.this,MenuActivity.class) ;
        startActivity(intent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String idno = _idno.getText().toString();
        String Aadhar = _Aadhar.getText().toString();
        String Bankname = _BankName.getText().toString();
        String Ifsc = _Ifsc.getText().toString();
        String Accountno = _Accountno.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }
        if (idno.isEmpty()) {
            _idno.setError("Enter valid ID no");
            valid=false;
        } else {
            _idno.setError(null);
        }
        if (Aadhar.isEmpty() || Aadhar.length() != 12) {
            _Aadhar.setError("Enter Valid AADHAAR");
            valid=false;
        } else {
            _Aadhar.setError(null);
        }


        return valid;
    }
}
