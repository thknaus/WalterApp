package hamburg.walter.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import hamburg.walter.R;
import hamburg.walter.activities.MainActivity;
import hamburg.walter.data.Game;
import hamburg.walter.data.Store;
import hamburg.walter.data.User;
import hamburg.walter.sync.AsyncClient;
import hamburg.walter.sync.mJsonHttpResponseHandler;

public class MainMenuFragment extends AppCompatActivity implements View.OnClickListener {
    private static void d(Object... stringableMessage) {
        Store.d("FavoriteFragment", stringableMessage);
    }

    private Button newgameBtn, exitGameBtn, joinGameBtn;
    private EditText playerNameTxt;
    User user;

    Game game;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        setContentView(R.layout.fragment_mainmenu);
        user = user.getInstance();

        newgameBtn = (Button) findViewById(R.id.button_newgame);
        joinGameBtn = (Button) findViewById(R.id.button_joingame);
        exitGameBtn = (Button) findViewById(R.id.button_exitgame);

        playerNameTxt = (EditText)findViewById(R.id.text_playername);

        newgameBtn.setOnClickListener(this);
        joinGameBtn.setOnClickListener(this);
        exitGameBtn.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainMenuFragment.this, ExitGameFragment.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_newgame:
                if(playerNameTxt.getText().equals("")){
                    //Snackbar Name wählen
                    break;
                }
                user.setName(playerNameTxt.getText().toString());

                /*
                    TODO: Create new game session on server, get game session id and save in Game
                 */

                game = game.getInstance();
 /*                RequestParams params = new RequestParams();


                AsyncClient.post("/login", params, new mJsonHttpResponseHandler(context) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            if (response.getInt("SERVER_RESPONSE") == 1) {
                                startActivity(new Intent(MainMenuFragment.this, NewGameLobbyFragment.class));
                                finish();

                            } else if (response.getInt(context.getString(R.string.server_message)) == 1) {
                                Toast.makeText(context, response.getString("SERVER_MESSAGE"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, R.string.loginfailed, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });*/
                startActivity(new Intent(MainMenuFragment.this, NewGameLobbyFragment.class));
                finish();
                break;
            case R.id.button_joingame:
                startActivity(new Intent(MainMenuFragment.this, JoinGameFragment.class));
                finish();
                break;
            case R.id.button_exitgame:
                onBackPressed();
                break;
        }
    }
}
