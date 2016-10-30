package hamburg.walter.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import hamburg.walter.R;
import hamburg.walter.sync.ServerRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 30.10.2016.
 */

public class RegisterFragment extends Activity {
    EditText email, password;
    Button login, register;
    String emailtxt, passwordtxt;
    List<NameValuePair> params;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);

        email = (EditText)findViewById(R.id.registermail);
        password = (EditText)findViewById(R.id.registerpw);
        register = (Button)findViewById(R.id.button_register);
        login = (Button)findViewById(R.id.button_loginregister);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent regfragment = new Intent(RegisterFragment.this, LoginFragment.class);
                startActivity(regfragment);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailtxt = email.getText().toString();
                passwordtxt = password.getText().toString();
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email", emailtxt));
                params.add(new BasicNameValuePair("password", passwordtxt));
                ServerRequest sr = new ServerRequest();
                JSONObject json = sr.getJSON("http://localhost:8080/register", params);

                if(json != null){
                    try{
                        String jsonstr = json.getString("response");
                        Toast.makeText(getApplication(), jsonstr, Toast.LENGTH_LONG).show();

                        Log.d("Hello", jsonstr);
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
