package com.kpp.kamalpanditpatil.ui.activities.admin.Base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.kpp.kamalpanditpatil.ui.activities.login_page;
import com.kpp.kamalpanditpatil.ui.activities.supervisor.Base.MenuActivity;
import com.kpp.kamalpanditpatil.ui.activities.utilities.workerDataDialog;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kpp.kamalpanditpatil.R;
import com.kpp.kamalpanditpatil.constants.constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class worker_list extends AppCompatActivity {
    // List view
    private ListView lv;
    public final String TAG="admin.worker_list";

    // Listview Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;
    String code,message;
    AlertDialog.Builder builder;


    // ArrayList for Listview
    ArrayList<String> workerlist;

    //progressdialog
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_list);
        Toolbar toolbar = findViewById(R.id.workerListToolbar);
        toolbar.setTitle("WORKERS");
        setSupportActionBar(toolbar);

        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputsearch);

        // Adding items to listview
        pDialog = new ProgressDialog(this);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, workerlist);

        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        // Creating volley request obj

        StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.WORKERLIST, new Response.Listener<String>() {
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
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = jsonArray.getJSONObject(i);
                                // adding worker to movies array
                                workerlist.add(obj.getString("name"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        hidePDialog();

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        lv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        });
        com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(this).addtoRequestQueue(stringRequest);


//        JsonArrayRequest workerReq = new JsonArrayRequest(constants.WORKERLIST,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.d(TAG, response.toString());
//                        onResponse(String response){
//
//                        };
//
//                        // Parsing json
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//
//                                JSONObject obj = response.getJSONObject(i);
//                                com.kpp.kamalpanditpatil.models.worker_model worker = new com.kpp.kamalpanditpatil.models.worker_model();
//                                worker.setName(obj.getString("name"));
//
//
//                                // adding worker to movies array
//                                workerlist.add(worker.getName());
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                        hidePDialog();
//
//                        // notifying list adapter about data changes
//                        // so that it renders the list view with updated data
//                        lv.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("workerlist", "Error: " + error.getMessage());
//                hidePDialog();
//
//            }
//        });

        // Adding request to request queue


        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                worker_list.this.adapter.getFilter().filter(cs);
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
                String value = adapter.getItem(position);
                workerDataDialog.setValue(value);
                workerDataDialog workerDataDialog = new workerDataDialog(worker_list.this);
                workerDataDialog.show();


//                String value=adapter.getItem(position);
//                Intent intent=new Intent(worker_list.this,updationActivity.class);
//                intent.putExtra("name",value);
//                startActivity(intent);
//
//                startActivity(intent);
//                Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();

            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapter.getItem(i);
                Intent intent = new Intent(worker_list.this, updationActivity.class);
                intent.putExtra("name", value);
                return true;

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
    private  void dispalyAlert(String Message){
        builder.setMessage(Message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(worker_list.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.adminnavigation, menu);
        return true;
    }
    }

