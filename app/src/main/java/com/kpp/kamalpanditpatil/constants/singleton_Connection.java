package com.kpp.kamalpanditpatil.constants;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class singleton_Connection {
    private static singleton_Connection mInstance;
    private RequestQueue requestQueue;
    private Context context;
    private singleton_Connection(Context con){
        context=con;
        requestQueue=getRequestQueue();
    }


    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue=Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized  singleton_Connection getInstance(Context con){
        if(mInstance==null){
            mInstance=new singleton_Connection(con);
        }
        return mInstance;
    }
    public <T> void addtoRequestQueue(Request<T> request){
        requestQueue.add(request);
    }
}
