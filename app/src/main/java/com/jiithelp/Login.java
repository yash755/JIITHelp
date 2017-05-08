package com.jiithelp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {

    UserLocalStore userLocalStore;
    EditText eno,password,dob;
    ImageView seepassword;
    TextView login;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eno = (EditText)findViewById(R.id.eno);
        password = (EditText)findViewById(R.id.password);
        dob = (EditText)findViewById(R.id.dob);
        login = (TextView)findViewById(R.id.login);
        seepassword = (ImageView)findViewById(R.id.passwordhide);

        seepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0)
                {

                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    flag=1;
                    Resources res = getResources();
                    seepassword.setImageDrawable(res.getDrawable(R.drawable.password_hide_icon));

                }
                else
                {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flag=0;
                    Resources res = getResources();
                    seepassword.setImageDrawable(res.getDrawable(R.drawable.password_show_icon));

                }
            }
        });

        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v == login){

            hideSoftKeyboard();

            if(new Util().check_connection(Login.this)) {

                if(new Util().validate(eno,password,dob)) {

                    String enos = eno.getText().toString();
                    String passwords = password.getText().toString();
                    String dobs = dob.getText().toString();

                    authenticate(enos, passwords, dobs);
                }
                else {

                    if(eno.getText().toString().trim().equals("")) {
                        Snackbar.make(v, "Enrollment Number is empty.", Snackbar.LENGTH_SHORT).show();
                    }
                    else if(password.getText().toString().trim().equals("")) {
                        Snackbar.make(v, "Password shouldn't be empty.", Snackbar.LENGTH_SHORT).show();
                    }
                    else if(dob.getText().toString().trim().equals("")) {
                        Snackbar.make(v, "Please provide with DOB.", Snackbar.LENGTH_SHORT).show();
                    }


                }
            }
            else{
                    new Util().nointernet(this,"Won't be able to login!");
            }


        }
    }


    public void authenticate(final String eno, final String password,final String dob){

        Map<String, String> params = new HashMap<String, String>();
        params.put("eno",eno);
        params.put("password",password);
        params.put("dob",dob);
        new Requests().loginrequest(params,this, new GetResult() {
            @Override
            public void done(JSONArray response) {
                try {
                JSONObject jsonObject = response.getJSONObject(0);
                String result = jsonObject.getString("response");
                if(result.equals("Success")){
                    userLocalStore = new UserLocalStore(getApplicationContext());
                    userLocalStore.setUserloggedIn(true);
                    userLocalStore.userData(eno,password,dob);
                    userLocalStore.setWishlistCount(0);
                    userLocalStore.setNotifyFlag(0);
                    Intent i = new Intent(Login.this,HomeScreen.class);
                    startActivity(i);

                }
                else
                    new Util().showerrormessage(Login.this,result);

                }catch (JSONException e) {
                }
            }
        });
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed();

    }

    @Override
    public void onResume(){
        super.onResume();

        if(new UserLocalStore(this).getuserloggedIn()){
            Intent i = new Intent(this, HomeScreen.class);
            startActivity(i);
        }
    }

}
