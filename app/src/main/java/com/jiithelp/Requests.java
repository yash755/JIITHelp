package com.jiithelp;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.Map;

import dmax.dialog.SpotsDialog;

public class Requests {

    public static final String BASEURL = "http://yashgupta.96.lt/lrc/";
    public static final String LOGINURL = "login.php";
    public static final String BOOKURL = "test.php";

    public void loginrequest(Map args, final Context context, final GetResult getResult){
        final AlertDialog dialog = new SpotsDialog(context, R.style.LoginDialog);
        dialog.show();

        String url = BASEURL + LOGINURL;


        JsonArray jsonArrayRequest = new JsonArray(Request.Method.POST, url,args, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                System.out.println("Login Response" + response.toString());
                dialog.dismiss();
                getResult.done(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    dialog.hide();
                    new Util().showerrormessage(context, "Time Out Error.....Try Later!!!");

                } else if (error instanceof AuthFailureError) {
                    dialog.hide();
                    new Util().showerrormessage(context, "Authentication Error.....Try Later!!!");
                } else if (error instanceof ServerError) {
                    dialog.hide();
                    new Util().showerrormessage(context,"Server Error.....Try Later!!!");
                } else if (error instanceof NetworkError) {
                    dialog.hide();
                    new Util().showerrormessage(context, "Network Error.....Try Later!!!");
                } else if (error instanceof ParseError) {
                    dialog.hide();
                    Log.d("Response: ", error.toString());
                    System.out.println("Resonse" + error.toString());
                    new Util().showerrormessage(context, "Unknown Error.....Try Later!!!");
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonArrayRequest);

    }

}
