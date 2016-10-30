package hamburg.walter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import hamburg.walter.R;

public class FriendsAddNewFragment extends AppCompatActivity implements View.OnClickListener {

    Button search_friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_friends_add_new);

        search_friend = (Button) findViewById(R.id.button_search_friends);
        search_friend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_search_friends:
                startActivity(new Intent(FriendsAddNewFragment.this, FriendsSearchResultFragment.class));
                break;
        }
    }
}
