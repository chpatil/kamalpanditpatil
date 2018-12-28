package com.kpp.kamalpanditpatil.ui.activities.admin.Base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kpp.kamalpanditpatil.R;
import com.kpp.kamalpanditpatil.constants.constants;
import com.kpp.kamalpanditpatil.models.worker_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorkerDetailsDisplay extends AppCompatActivity {
    private ListView lv1;
    private Button EditButton;
    ArrayAdapter<String> adapter;
    ArrayList<String> workerDataList;
    ArrayList<String>workerDataTransferList;
    String value;
    worker_model worker;
    ProgressDialog pDiaalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_details_display);
        value=getIntent().getStringExtra("value");
        Toolbar toolbar = findViewById(R.id.adminworkerEditingToolbar);
        toolbar.setTitle(value);
        lv1=findViewById(R.id.workerDialogList);
        EditButton=findViewById(R.id.EditButton);
        workerDataList=new ArrayList<String>();
        workerDataTransferList=new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, workerDataList);
        pDiaalog = new ProgressDialog(this);
        pDiaalog.setMessage("Fetching data ...");
        pDiaalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.WORKERDATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    worker = new worker_model();
                    worker.setName(jsonObject.getString("name"));
                    worker.setAadhar(jsonObject.getString("aadhar"));
                    worker.setAccountno(jsonObject.getString("accountno"));
                    worker.setAddress(jsonObject.getString("address"));
                    worker.setDepartment(jsonObject.getString("department"));
                    worker.setBankname((jsonObject.getString("bankname")));
                    worker.setIFSC_code(jsonObject.getString("IFSC"));
                    worker.setGender(jsonObject.getString("gender"));
                    worker.setPF(jsonObject.getString("PF"));
                    worker.setESIC(jsonObject.getString("ESIC"));
                    worker.setId_no(jsonObject.getInt("idno"));
                    //tranfering theworker data

                    workerDataTransferList.add(worker.getName());
                    workerDataTransferList.add(worker.getGender());
                    workerDataTransferList.add(worker.getAddress());
                    workerDataTransferList.add(worker.getDepartment());
                    workerDataTransferList.add(worker.getAadhar());
                    workerDataTransferList.add(worker.getBankname());
                    workerDataTransferList.add(worker.getIFSC_code());
                    workerDataTransferList.add(worker.getAccountno());
                    workerDataTransferList.add(worker.getPF());
                    workerDataTransferList.add(worker.getESIC());
                    workerDataTransferList.add(String.valueOf(worker.getId_no()));

                    // adding worker to worker array
                    workerDataList.add(constants.workerDisplayarray[0]+"\t"+worker.getName());
                    workerDataList.add(constants.workerDisplayarray[1]+"\t"+worker.getGender());
                    workerDataList.add(constants.workerDisplayarray[2]+"\t"+worker.getAddress());
                    workerDataList.add(constants.workerDisplayarray[3]+"\t"+worker.getId_no());
                    workerDataList.add(constants.workerDisplayarray[4]+"\t"+worker.getDepartment());
                    workerDataList.add(constants.workerDisplayarray[5]+"\t"+worker.getAadhar());
                    workerDataList.add(constants.workerDisplayarray[6]+"\t"+worker.getBankname());
                    workerDataList.add(constants.workerDisplayarray[7]+"\t"+worker.getIFSC_code());
                    workerDataList.add(constants.workerDisplayarray[8]+"\t"+worker.getAccountno());
                    workerDataList.add(constants.workerDisplayarray[9]+"\t"+worker.getPF());
                    workerDataList.add(constants.workerDisplayarray[10]+"\t"+worker.getESIC());
                    lv1.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    pDiaalog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> datamap=new HashMap<String,String>();
                datamap.put("name",value);
                return datamap;
            }
        };
        com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(this).addtoRequestQueue(stringRequest);
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(WorkerDetailsDisplay.this,updationActivity.class);
                intent.putStringArrayListExtra("workerData",workerDataTransferList);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, worker_list.class));
    }
}
