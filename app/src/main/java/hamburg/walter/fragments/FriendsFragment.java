package hamburg.walter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import hamburg.walter.R;

public class FriendsFragment extends AppCompatActivity implements View.OnClickListener {

    Button addNewFrind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_friends);

        addNewFrind = (Button) findViewById(R.id.button_find_friends);
        addNewFrind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_find_friends:
                startActivity(new Intent(FriendsFragment.this, FriendsAddNewFragment.class));
                break;
        }
    }
}
