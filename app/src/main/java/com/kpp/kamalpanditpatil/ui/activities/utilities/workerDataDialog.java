package com.kpp.kamalpanditpatil.ui.activities.utilities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kpp.kamalpanditpatil.R;
import com.kpp.kamalpanditpatil.constants.constants;
import com.kpp.kamalpanditpatil.models.worker_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class workerDataDialog extends Dialog {
    ListView lv;
    ArrayList<String> workerDataList;
    ArrayAdapter adapter;
    ProgressDialog pDialog;
    private final String TAG="Workerdatadialog";
    String name;

    public workerDataDialog(@NonNull Context context) {
        super(context);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater li = LayoutInflater.from(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater=this.getLayoutInflater();
        View view =inflater.inflate(R.layout.workerdatadialog,null);
        lv=view.findViewById(R.id.workerDialogList);
        pDialog=new ProgressDialog(getContext());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        adapter = new ArrayAdapter<String>(getContext(), R.layout.list_item, R.id.product_name, workerDataList);
        lv.setAdapter(adapter);
        JsonArrayRequest workerReq = new JsonArrayRequest(constants.WORKERDATA,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                worker_model worker = new worker_model();
                                worker.setName(obj.getString("name"));
                                worker.setAadhar(obj.getString("aadhar"));
                                worker.setAccountno(obj.getString("accountno"));
                                worker.setAddress(obj.getString("address"));
                                worker.setBankname((obj.getString("bankname")));
                                worker.setIFSC_code(obj.getString("IFSC"));
                                worker.setGender(obj.getString("gender"));
                                worker.setPF(obj.getString("PF"));
                                worker.setESIC(obj.getString("ESIC"));
                                worker.setId_no(obj.getInt("idno"));
                                name=worker.getName();

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
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        pDialog.hide();

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("workerlist", "Error: " + error.getMessage());
                pDialog.hide();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> datamap=new HashMap<String,String>();
                datamap.put("value",name);
                return datamap;
            }
        };

        // Adding request to request queue
        com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(getContext()).addtoRequestQueue(workerReq);


        pDialog = new ProgressDialog(getContext());

        builder.setView(view)
                .setTitle(name).
                setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                               dialog.dismiss();

                            }
                        });
        builder.show();

    }
    public void setValue(String name){
        this.name=name;
    }
    }



