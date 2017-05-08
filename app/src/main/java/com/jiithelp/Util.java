package com.jiithelp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.EditText;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by yash on 6/4/17.
 */

public class Util {

    public boolean validate(EditText eno, EditText password, EditText dob) {
        return !eno.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("")
                && !dob.getText().toString().trim().equals("");
    }


    public boolean check_connection(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void showerrormessage(Context context, String message){
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText(message)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();

    }

    public void nointernet(final Context context, String message){
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("No Internent Connection")
                .setContentText(message)
                .setConfirmText("Go to Settings!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        context.startActivity(new Intent(Settings.ACTION_SETTINGS));
                        sDialog.cancel();
                    }
                })
                .show();
    }

}
