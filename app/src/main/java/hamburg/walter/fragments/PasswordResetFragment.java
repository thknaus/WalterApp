package hamburg.walter.fragments;




import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import hamburg.walter.R;
import hamburg.walter.helper.EMailValidator;
import hamburg.walter.helper.ShowSnackbar;
import hamburg.walter.sync.AsyncClient;
import hamburg.walter.sync.mJsonHttpResponseHandler;

public class PasswordResetFragment extends Fragment implements View.OnClickListener {

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

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_password_reset, container, false);

        email = (TextView) rootView.findViewById(R.id.email);
        requesNewPassword = (Button) rootView.findViewById(R.id.request_password_btn);
        parentLayout = rootView.findViewById(android.R.id.content);

        requesNewPassword.setOnClickListener(this);

        return rootView;
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
                                    new ShowSnackbar().showSnackbar(parentLayout, getString(R.string.send_email));
                                } else if (response.getInt("SERVER_RESPONSE") == 0) {
                                    new ShowSnackbar().showSnackbar(parentLayout, getString(R.string.unknow_email));
                                } else {
                                    new ShowSnackbar().showSnackbar(parentLayout, getString(R.string.unknow_error));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    new ShowSnackbar().showSnackbar(parentLayout, getString(R.string.forgotpw_message));
                }
                break;
        }
    }
}
