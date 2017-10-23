package hamburg.walter.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import hamburg.walter.R;
import hamburg.walter.helper.EMailValidator;

public class PasswordResetFragment extends Activity implements View.OnClickListener {

    public Button requesNewPassword;
    public TextView email;
    Context context;

    public PasswordResetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_password_reset);
        context = this;
        email = (TextView) findViewById(R.id.email);
        requesNewPassword = (Button) findViewById(R.id.request_password_btn);

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
                } else {
                    //Show Snackbar
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
