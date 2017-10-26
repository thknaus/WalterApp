package hamburg.walter.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import hamburg.walter.R;
import hamburg.walter.helper.EMailValidator;
import hamburg.walter.helper.SnackbarShow;
import hamburg.walter.sync.AsyncClient;
import hamburg.walter.sync.mJsonHttpResponseHandler;

public class PasswordResetFragment extends Activity implements View.OnClickListener {

    public Button requesNewPassword;
    public TextView email;
    Context context;
    View parentLayout;

    public PasswordResetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_password_reset);
        context = this;
        email = (TextView) findViewById(R.id.email);
        requesNewPassword = (Button) findViewById(R.id.request_password_btn);
        parentLayout = findViewById(android.R.id.content);
        requesNewPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        final String myEmail;
        myEmail = email.getText().toString();

        switch (view.getId()) {
            case R.id.request_password_btn:
                if (EMailValidator.isValid(myEmail)) {
                    //Send Email
                    RequestParams params = new RequestParams();
                    params.put("email", myEmail);

                    AsyncClient.post("/sendrequestmail", params, new mJsonHttpResponseHandler(context) {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                if (response.getInt("SERVER_RESPONSE") == 1) {
                                    new SnackbarShow().showSnackbar(parentLayout, getString(R.string.send_email));
                                } else if (response.getInt("SERVER_RESPONSE") == 0) {
                                    new SnackbarShow().showSnackbar(parentLayout, getString(R.string.unknow_email));
                                } else {
                                    new SnackbarShow().showSnackbar(parentLayout, getString(R.string.unknow_error));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    new SnackbarShow().showSnackbar(parentLayout, getString(R.string.forgotpw_message));
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent loginfragment = new Intent(PasswordResetFragment.this, LoginFragment.class);
        startActivity(loginfragment);
        finish();
    }
}
