package com.jiithelp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Next extends AppCompatActivity {

    WebView webview;
    ProgressDialog progressBar;
    String title,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        if(getIntent().hasExtra("title"))
            title = getIntent().getStringExtra("title");

        if(title.equals("Admission"))
            url = "http://www.jiit.ac.in/admission-results";
        else if (title.equals("Announcements"))
            url = "http://www.jiit.ac.in/announcements";
        else if (title.equals("Upcoming Events"))
            url = "http://www.jiit.ac.in/guidelines-research";
        else if (title.equals("Notice Board"))
            url = "http://www.jiit.ac.in/important-notices-parents-students-must-read";
        else if (title.equals("Academic Calendar"))
            url = "http://www.jiit.ac.in/academic-calendars-0";
        else if (title.equals("Important Links"))
            url = "http://www.jiit.ac.in/importantlinks";

        webview = (WebView)findViewById(R.id.wb);

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        if(!new Util().check_connection(this)){
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("No Internent Connection")
                    .setContentText("Won't be able to login!")
                    .setConfirmText("Go to Settings!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            startActivity(new Intent(Settings.ACTION_SETTINGS));
                            sDialog.cancel();
                        }
                    })
                    .show();
        }

        progressBar = ProgressDialog.show(this, "JIIT Page", "Loading...");

        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

        });
        webview.loadUrl(url);
    }
}

