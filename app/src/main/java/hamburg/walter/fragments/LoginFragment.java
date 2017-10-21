package hamburg.walter.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import hamburg.walter.R;
import hamburg.walter.activities.MainActivity;
import hamburg.walter.sync.AsyncClient;
import hamburg.walter.sync.ServerRequest;
import hamburg.walter.sync.mJsonHttpResponseHandler;

public class LoginFragment extends AppCompatActivity implements View.OnClickListener {

    EditText email, password, res_email, code, newpass;
    Button login, cont, cont_code, cancel, cancel1, register, forgotPW;
    String emailtxt, passwordtxt, email_res_txt, code_txt, npass_txt;


    SharedPreferences pref;
    Dialog reset;
    ServerRequest sr;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        sr = new ServerRequest();

        context = this;
        email = (EditText) findViewById(R.id.loginemail);
        password = (EditText) findViewById(R.id.loginpw);

        login = (Button) findViewById(R.id.login_btn);
        register = (Button) findViewById(R.id.register_btn);
        forgotPW = (Button) findViewById(R.id.forgotpw_btn);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        forgotPW.setOnClickListener(this);

        pref = getSharedPreferences("AppPref", MODE_PRIVATE);

        String username = getIntent().getStringExtra("USER_NAME");
        if(username != null){
            email.setText(username);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                emailtxt = email.getText().toString();
                passwordtxt = password.getText().toString();
                RequestParams params = new RequestParams();
                params.put("username", emailtxt);
                params.put("password", passwordtxt);

                AsyncClient.post("/login", params, new mJsonHttpResponseHandler(context) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            if (response.getInt("SERVER_RESPONSE") == 1) {
                                //Toast.makeText(context, response.getString("SERVER_MESSAGE"), Toast.LENGTH_SHORT).show();
                                Intent mainMenuFragment = new Intent(LoginFragment.this, MainMenuFragment.class);
                                startActivity(mainMenuFragment);

                            } else if (response.getInt(context.getString(R.string.server_message)) == 1) {
                                Toast.makeText(context, response.getString("SERVER_MESSAGE"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, R.string.loginfailed, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.register_btn:
                Intent regactivity = new Intent(LoginFragment.this, RegisterFragment.class);
                startActivity(regactivity);
                finish();
                break;
            case R.id.forgotpw_btn:
                reset = new Dialog(LoginFragment.this);
                reset.setTitle("Reset Password");
                reset.setContentView(R.layout.fragment_resetpw);
                cont = (Button) reset.findViewById(R.id.button_resetpw_continue);
                cancel = (Button) reset.findViewById(R.id.button_resetpw_cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reset.dismiss();
                    }
                });
                res_email = (EditText) reset.findViewById(R.id.resetpw_email);

                cont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        email_res_txt = res_email.getText().toString();

                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("email", email_res_txt));

                        //  JSONObject json = sr.getJSON("http://192.168.56.1:8080/api/resetpass", params);
                        JSONObject json = sr.getJSON("http://10.0.2.2:8080/api/resetpass", params);

                        if (json != null) {
                            try {
                                String jsonstr = json.getString("response");
                                if (json.getBoolean("res")) {
                                    Log.e("JSON", jsonstr);
                                    Toast.makeText(getApplication(), jsonstr, Toast.LENGTH_LONG).show();
                                    reset.setContentView(R.layout.fragment_resetpw_code);
                                    cont_code = (Button) reset.findViewById(R.id.button_resetpw_change);
                                    code = (EditText) reset.findViewById(R.id.resetpw_code);
                                    newpass = (EditText) reset.findViewById(R.id.new_pw);
                                    cancel1 = (Button) reset.findViewById(R.id.button_resetpw_cancelcode);
                                    cancel1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            reset.dismiss();
                                        }
                                    });
                                    cont_code.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            code_txt = code.getText().toString();
                                            npass_txt = newpass.getText().toString();
                                            Log.e("Code", code_txt);
                                            Log.e("New pass", npass_txt);
                                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                                            params.add(new BasicNameValuePair("email", email_res_txt));
                                            params.add(new BasicNameValuePair("code", code_txt));
                                            params.add(new BasicNameValuePair("newpass", npass_txt));

                                            JSONObject json = sr.getJSON("http://10.0.2.2:8080/api/resetpass/chg", params);
                                            //   JSONObject json = sr.getJSON("http://192.168.56.1:8080/api/resetpass/chg", params);
                                            if (json != null) {
                                                try {
                                                    String jsonstr = json.getString("response");
                                                    if (json.getBoolean("res")) {
                                                        reset.dismiss();
                                                        Toast.makeText(getApplication(), jsonstr, Toast.LENGTH_LONG).show();
                                                    } else {
                                                        Toast.makeText(getApplication(), jsonstr, Toast.LENGTH_LONG).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(getApplication(), jsonstr, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                reset.show();
                break;
        }
    }
}
