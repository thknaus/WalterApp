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

public class RegisterFragment extends Activity implements View.OnClickListener{
    EditText email, password;
    Button register;
    String emailtxt, passwordtxt;
    List<NameValuePair> params;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);

        email = (EditText)findViewById(R.id.registermail);
        password = (EditText)findViewById(R.id.registerpw);
        register = (Button)findViewById(R.id.register_btn);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.register_btn:
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
                break;
        }
    }
}
