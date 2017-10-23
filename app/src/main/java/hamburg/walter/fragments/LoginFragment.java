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
                Intent registerFragment = new Intent(LoginFragment.this, RegisterFragment.class);
                startActivity(registerFragment);
                finish();
                break;
            case R.id.forgotpw_btn:
                Intent passwordresetfragment = new Intent(LoginFragment.this, PasswordResetFragment.class);
                startActivity(passwordresetfragment);
                finish();
                break;
        }
    }
}
