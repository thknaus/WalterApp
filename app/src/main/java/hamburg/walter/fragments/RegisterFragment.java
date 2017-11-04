package hamburg.walter.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import hamburg.walter.R;
import hamburg.walter.activities.MainActivity;
import hamburg.walter.sync.AsyncClient;
import hamburg.walter.sync.mJsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterFragment extends Fragment implements View.OnClickListener{
    private static String TAG = "RegisterFragment";

    SharedPreferences pref;
    EditText email, password;
    Button register;
    String emailtxt, passwordtxt;
    Context context;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
       // pref = getSharedPreferences("AppPref", MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        email = (EditText) rootView.findViewById(R.id.registermail);
        password = (EditText) rootView.findViewById(R.id.registerpw);
        register = (Button) rootView.findViewById(R.id.register_btn);

        register.setOnClickListener(this);

        return rootView;
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
                AsyncClient.post("/signup", params, new mJsonHttpResponseHandler(getContext()){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try{
                            if(!(response.getString("SERVER_MESSAGE") == null)){

                                String s = response.getString("SERVER_MESSAGE");
                                // Toast.makeText(context, R.string.registered, Toast.LENGTH_SHORT).show();

                                Log.v(TAG, "Created user: "+ emailtxt);
                                ((MainActivity)getActivity()).showFragment(LoginFragment.class);
                                //loginactivity.putExtra("USER_EMAIL", s);
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
}
