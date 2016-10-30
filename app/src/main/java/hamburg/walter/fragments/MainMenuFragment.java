package hamburg.walter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import hamburg.walter.R;
import hamburg.walter.data.Store;

public class MainMenuFragment extends AppCompatActivity implements View.OnClickListener {
    private static void d(Object... stringableMessage) {
        Store.d("FavoriteFragment", stringableMessage);
    }

    private Button newgameBtn, existingGameBtn, gameSettingBtn, exitGameBtn, joinGameBtn, friendsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_mainmenu);

        newgameBtn = (Button) findViewById(R.id.button_newgame);
        existingGameBtn = (Button) findViewById(R.id.button_existinggame);
        joinGameBtn = (Button) findViewById(R.id.button_joingame);
        friendsBtn = (Button) findViewById(R.id.button_friends);
        gameSettingBtn = (Button) findViewById(R.id.button_gamesettings);
        exitGameBtn = (Button) findViewById(R.id.button_exitgame);

        newgameBtn.setOnClickListener(this);
        existingGameBtn.setOnClickListener(this);
        joinGameBtn.setOnClickListener(this);
        friendsBtn.setOnClickListener(this);
        gameSettingBtn.setOnClickListener(this);
        exitGameBtn.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainMenuFragment.this, ExitGameFragment.class));
            }
        }, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_newgame:
                startActivity(new Intent(MainMenuFragment.this, NewGameFragment.class));
                break;
            case R.id.button_existinggame:
                startActivity(new Intent(MainMenuFragment.this, ExistingGamesFragment.class));
                break;
            case R.id.button_joingame:
                startActivity(new Intent(MainMenuFragment.this, JoinGameFragment.class));
                break;
            case R.id.button_friends:
                startActivity(new Intent(MainMenuFragment.this, FriendsFragment.class));
                break;
            case R.id.button_gamesettings:
                startActivity(new Intent(MainMenuFragment.this, GameSettingsFragment.class));
                break;
            case R.id.button_exitgame:
                onBackPressed();
                break;
        }
    }
}
