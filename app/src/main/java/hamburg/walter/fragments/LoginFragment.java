package hamburg.walter.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import hamburg.walter.R;
import hamburg.walter.activities.MainActivity;
import hamburg.walter.data.User;
import hamburg.walter.helper.ShowSnackbar;
import hamburg.walter.sync.AsyncClient;
import hamburg.walter.sync.mJsonHttpResponseHandler;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText email, password;
    private Button login, forgotPW;
    private String emailtxt, passwordtxt;
    private User user;
    private SharedPreferences pref;
    Context context;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        user = User.getInstance();
        pref = (getActivity()).getSharedPreferences("AppPref", MODE_PRIVATE);
        email = (EditText) rootView.findViewById(R.id.loginemail);
        password = (EditText) rootView.findViewById(R.id.loginpw);

        login = (Button) rootView.findViewById(R.id.login_btn);
        forgotPW = (Button) rootView.findViewById(R.id.forgotpw_btn);

        login.setOnClickListener(this);
        forgotPW.setOnClickListener(this);


        return rootView;
    }

    public void updateView() {

        String myEmail =(getActivity()).getIntent().getStringExtra("USER_EMAIL");
            if(email != null){
            email.setText(myEmail);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                emailtxt = email.getText().toString();
                passwordtxt = password.getText().toString();
                Log.d(passwordtxt, emailtxt);
                if (!emailtxt.isEmpty() || !passwordtxt.isEmpty()) {
                    RequestParams params = new RequestParams();
                    params.put("username", emailtxt);
                    params.put("password", passwordtxt);

                    AsyncClient.post("/login", params, new mJsonHttpResponseHandler(context) {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                if (response.getInt("SERVER_RESPONSE") == 1) {
                                    user.setUserData(response.getJSONObject("SERVER_MESSAGE"));
                                    ((MainActivity) getActivity()).showFragment(MainMenuFragment.class);
                                } else {

                                    // TODO: ShowSnackbar unable to login

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    new ShowSnackbar().showSnackbar(getView(), "Bitte E-Mail-Adresse und Passwort eingeben");
                }
                break;
            case R.id.forgotpw_btn:
                ((MainActivity)getActivity()).showFragment(PasswordResetFragment.class);
                break;
        }
    }
}
