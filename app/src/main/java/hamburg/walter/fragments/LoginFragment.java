package hamburg.walter.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import hamburg.walter.R;
import hamburg.walter.data.User;
import hamburg.walter.sync.AsyncClient;
import hamburg.walter.sync.mJsonHttpResponseHandler;

public class LoginFragment extends AppCompatActivity implements View.OnClickListener {

    EditText emailTxt, password;
    Button login, register, forgotPW;
    String emailtxt, passwordtxt;

    User user;

    SharedPreferences pref;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        user = User.getInstance();

        context = this;
        emailTxt = (EditText) findViewById(R.id.loginemail);
        password = (EditText) findViewById(R.id.loginpw);

        login = (Button) findViewById(R.id.login_btn);
        register = (Button) findViewById(R.id.register_btn);
        forgotPW = (Button) findViewById(R.id.forgotpw_btn);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        forgotPW.setOnClickListener(this);

        pref = getSharedPreferences("AppPref", MODE_PRIVATE);

        String email = getIntent().getStringExtra("USER_EMAIL");
        if(email != null){
            emailTxt.setText(email);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                emailtxt = emailTxt.getText().toString();
                passwordtxt = password.getText().toString();
                RequestParams params = new RequestParams();
                params.put("username", emailtxt);
                params.put("password", passwordtxt);

                AsyncClient.post("/login", params, new mJsonHttpResponseHandler(context) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            if (response.getInt("SERVER_RESPONSE") == 1) {
                                user.setUserData(response.getJSONObject("SERVER_MESSAGE"));
                                Intent mainMenuFragment = new Intent(LoginFragment.this, MainMenuFragment.class);
                                startActivity(mainMenuFragment);
                                finish();
                            }
                            else{
                                /*
                                TODO: ShowSnackbar unable to login
                                 */
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
