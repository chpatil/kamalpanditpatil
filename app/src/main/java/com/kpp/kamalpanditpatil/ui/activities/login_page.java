package com.kpp.kamalpanditpatil.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kpp.kamalpanditpatil.R;
import com.kpp.kamalpanditpatil.constants.constants;
import com.kpp.kamalpanditpatil.ui.activities.admin.Base.AdminMainMenu;
import com.kpp.kamalpanditpatil.ui.activities.supervisor.Base.MenuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class login_page extends AppCompatActivity {
    String password,username;
    @BindView(R.id.input_username)
    EditText _userText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.spinner_login)
    Spinner _spinnerLogin;
    String userrole,user,pass,code;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        ButterKnife.bind(this);

        //spinner declaration and application
        _spinnerLogin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                userrole = (String) _spinnerLogin.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        ArrayAdapter<String> adapter_role = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, constants.userRoleString);
        adapter_role
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spinnerLogin.setAdapter(adapter_role);

        //login button onclick method

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user=_userText.getText().toString();
                pass=_passwordText.getText().toString();
                if(user.equals("")||pass.equals("")){
                    Toast.makeText(login_page.this, "kogin page credentials cannot be empty", Toast.LENGTH_SHORT).show();
                }else{
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, constants.LOGINURL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject=jsonArray.getJSONObject(0);
                                code=jsonObject.getString("code");
                                if(code.equals("login_failed")){
                                    Toast.makeText(login_page.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }else if(userrole.equals("ADMIN")&&code.equals("login_success")){
                                    startActivity(new Intent(login_page.this,AdminMainMenu.class));
                                } else if (userrole.equals("SUPERVISOR")&&code.equals("login_success")) {
                                    startActivity(new Intent(login_page.this,MenuActivity.class));
                                }
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
                            Map<String,String> datamap=new HashMap<String,String>();
                            datamap.put("userrole",userrole);
                            datamap.put("username",user);
                            datamap.put("password",pass);
                            return datamap;
                        }
                    };
                    com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(login_page.this).addtoRequestQueue(stringRequest);
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }
}
