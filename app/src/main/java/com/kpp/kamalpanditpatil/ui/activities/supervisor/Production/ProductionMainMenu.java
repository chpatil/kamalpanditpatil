package com.kpp.kamalpanditpatil.ui.activities.supervisor.Production;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class ProductionMainMenu extends AppCompatActivity {

    private TextView mTextMessage;
    String code, message;
    Button DeleteButton1, DeleteButton2, DeleteButton5, DeleteButton3, DeleteButton4;
    private int count1 = 0, count2 = 0, count3 = 0, count5 = 0, count4 = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_casing:
                    startActivity(new Intent(ProductionMainMenu.this,com.kpp.kamalpanditpatil.ui.activities.supervisor.Production.CasingActivity.class));
                    finish();
                    return true;
                case R.id.navigation_cnc:
                    startActivity(new Intent(ProductionMainMenu.this,com.kpp.kamalpanditpatil.ui.activities.supervisor.Production.CNCActivity.class));
                    finish();
                    return true;
                case R.id.navigation_grinding:
                    startActivity(new Intent(ProductionMainMenu.this,com.kpp.kamalpanditpatil.ui.activities.supervisor.Production.GrindingActivity.class));
                    finish();
                    return true;
                case R.id.navigation_qanda:
                    startActivity(new Intent(ProductionMainMenu.this,com.kpp.kamalpanditpatil.ui.activities.supervisor.Production.QandAActivity.class));
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_main_menu);
        Toolbar toolbar=findViewById(R.id.ProductionMainMenuToolbar);
        toolbar.setTitle("PRODUCTION MENU");
        setSupportActionBar(toolbar);
        DeleteButton1 = findViewById(R.id.deleteGrindingTotalProductionButton);
        DeleteButton2 = findViewById(R.id.deletePersonwiseCasingproductionButton);
        DeleteButton3 = findViewById(R.id.deleteTotalCasingproductionButton);
        DeleteButton4 = findViewById(R.id.deletePersonwiseCNCproductionButton);
        DeleteButton5 = findViewById(R.id.deleteTotalProductionCNCproductionButton);
        mTextMessage = findViewById(R.id.DeleteTextviewcount);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        DeleteButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count1 < 5) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.GRINDINGDELETE, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                code = jsonObject.getString("code");
                                if (code.equals("0")) {
                                    Toast.makeText(ProductionMainMenu.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                } else if (code.equals("1")) {
                                    Toast.makeText(ProductionMainMenu.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                    count1++;
                                    mTextMessage.setText("No. of Grinding entries deleted:" + count1);
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
                    com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(ProductionMainMenu.this).addtoRequestQueue(stringRequest);
                } else {
                    Toast.makeText(ProductionMainMenu.this, "You cannot delete more entries", Toast.LENGTH_LONG).show();
                    DeleteButton1.setEnabled(false);

                }
            }
        });


        DeleteButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count2 < 5) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.CASINGPERSONWISEDELETE, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                code = jsonObject.getString("code");
                                if (code.equals("0")) {
                                    Toast.makeText(ProductionMainMenu.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                } else if (code.equals("1")) {
                                    Toast.makeText(ProductionMainMenu.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                    count2++;
                                    mTextMessage.setText("No. of Casing personwise entries deleted:" + count2);
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
                    com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(ProductionMainMenu.this).addtoRequestQueue(stringRequest);
                } else {
                    Toast.makeText(ProductionMainMenu.this, "You cannot delete more entries", Toast.LENGTH_LONG).show();
                    DeleteButton2.setEnabled(false);

                }
            }
        });


        DeleteButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count3 < 5) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.CASINGTOTALPRODUCTIONDELETE, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                code = jsonObject.getString("code");
                                if (code.equals("0")) {
                                    Toast.makeText(ProductionMainMenu.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                } else if (code.equals("1")) {
                                    Toast.makeText(ProductionMainMenu.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                    count3++;
                                    mTextMessage.setText("No. of Casing total entries deleted:" + count3);
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
                    com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(ProductionMainMenu.this).addtoRequestQueue(stringRequest);
                } else {
                    Toast.makeText(ProductionMainMenu.this, "You cannot delete more entries", Toast.LENGTH_LONG).show();
                    DeleteButton3.setEnabled(false);

                }
            }
        });


        DeleteButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count4 < 5) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.CNCPEROSNWISEPRODUCTIONDELETE, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                code = jsonObject.getString("code");
                                if (code.equals("0")) {
                                    Toast.makeText(ProductionMainMenu.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                } else if (code.equals("1")) {
                                    Toast.makeText(ProductionMainMenu.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                    count4++;
                                    mTextMessage.setText("No. of CNC personwise entries deleted:" + count4);
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
                    com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(ProductionMainMenu.this).addtoRequestQueue(stringRequest);
                } else {
                    Toast.makeText(ProductionMainMenu.this, "You cannot delete more entries", Toast.LENGTH_LONG).show();
                    DeleteButton4.setEnabled(false);

                }
            }
        });


        DeleteButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count5 < 5) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.CNCTOTALPRODUCTIONDELETE, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                code = jsonObject.getString("code");
                                if (code.equals("0")) {
                                    Toast.makeText(ProductionMainMenu.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                } else if (code.equals("1")) {
                                    Toast.makeText(ProductionMainMenu.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                    count5++;
                                    mTextMessage.setText("No. of CNC total entries deleted:" + count5);
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
                    com.kpp.kamalpanditpatil.constants.singleton_Connection.getInstance(ProductionMainMenu.this).addtoRequestQueue(stringRequest);
                } else {
                    Toast.makeText(ProductionMainMenu.this, "You cannot delete more entries", Toast.LENGTH_LONG).show();
                    DeleteButton5.setEnabled(false);

                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MenuActivity.class));
        finish();
    }
}
