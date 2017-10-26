package hamburg.walter.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import hamburg.walter.R;
import hamburg.walter.data.User;
import hamburg.walter.sync.AsyncClient;
import hamburg.walter.sync.ServerRequest;
import hamburg.walter.sync.mJsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegisterFragment extends Activity implements View.OnClickListener{
    private static String TAG = "RegisterFragment";

    SharedPreferences pref;
    EditText email, password;
    Button register;
    String emailtxt, passwordtxt;
    Context context;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
        context = this;

        pref = getSharedPreferences("AppPref", MODE_PRIVATE);

        email = (EditText)findViewById(R.id.registermail);
        password = (EditText)findViewById(R.id.registerpw);
        register = (Button)findViewById(R.id.register_btn);

        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.register_btn:

                emailtxt = email.getText().toString();
                passwordtxt = password.getText().toString();

                RequestParams params = new RequestParams();
                params.put("email", emailtxt);
                params.put("password", passwordtxt);
                AsyncClient.post("/signup", params, new mJsonHttpResponseHandler(this){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try{
                            if(!(response.getString("SERVER_MESSAGE") == null)){

                                String s = response.getString("SERVER_MESSAGE");
                                Toast.makeText(context, R.string.registered, Toast.LENGTH_SHORT).show();

                                Log.v(TAG, "Created user: "+ emailtxt);
                                Intent loginactivity = new Intent(RegisterFragment.this, LoginFragment.class);
                                loginactivity.putExtra("USER_EMAIL", s);
                                startActivity(loginactivity);
                                //onBackPressed();
                                finish();
                            }
                            else{
                                /*
                                TODO: ShowSnackbar unable to create User
                                 */
                            }

                        }catch(JSONException e){
                            Log.v(TAG, e.toString());
                        }

                    }
                });
                break;
        }
    }
    @Override
    public void onBackPressed(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(RegisterFragment.this, LoginFragment.class));
            }
        }, 0);
    }
}
