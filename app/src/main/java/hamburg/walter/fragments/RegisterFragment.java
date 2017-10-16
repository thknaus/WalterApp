package hamburg.walter.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import hamburg.walter.R;
import hamburg.walter.sync.AsyncClient;
import hamburg.walter.sync.ServerRequest;
import hamburg.walter.sync.mJsonHttpResponseHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegisterFragment extends Activity implements View.OnClickListener{
    EditText email, password;
    Button register;
    String emailtxt, passwordtxt;
    List<NameValuePair> params;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
        context = this;

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
                params.put("username", emailtxt);
                params.put("password", passwordtxt);
                AsyncClient.post("/signup", params, new mJsonHttpResponseHandler(this){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try{

                            if(response.getString("User") == null){
                                Toast.makeText(context, "New User created.", Toast.LENGTH_SHORT).show();
                            }

                        }catch(JSONException e){

                        }

                    }
                });
                break;
        }
    }
}
