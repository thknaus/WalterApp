package hamburg.walter.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import hamburg.walter.R;
import hamburg.walter.data.Game;
import hamburg.walter.data.IP;
import hamburg.walter.data.Store;
import hamburg.walter.data.User;
import hamburg.walter.sync.AsyncClient;
import hamburg.walter.sync.mJsonHttpResponseHandler;

public class MainMenuFragment extends AppCompatActivity implements View.OnClickListener {
    private static void d(Object... stringableMessage) {
        Store.d("FavoriteFragment", stringableMessage);
    }

    private Socket mSocket;
    {
        try{

            mSocket = IO.socket(new IP().URL);
        }catch(URISyntaxException e){

        }
    }
    private Emitter.Listener updateFromServer = new Emitter.Listener() {
        @Override
        public void call(final Object[] args) {
            JSONObject data = (JSONObject) args[0];
        }
    };
    private Button newgameBtn, exitGameBtn, joinGameBtn;
    private EditText playerNameTxt;
    User user;

    Game game;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
            Connext to webservice. socket.io
         */
        mSocket.connect();
        mSocket.on("test", updateFromServer);


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
                    /*
                    TODO: ShowSnackbar choose playername
                     */
                    break;
                }
                user.setName(playerNameTxt.getText().toString());

                game = game.getInstance();
                RequestParams params = new RequestParams();
                params.put("hostuser", user.getID());

                AsyncClient.post("/newgame", params, new mJsonHttpResponseHandler(context) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            if (response.getInt("SERVER_RESPONSE") == 1) {
                                game.setGameSessionId(response.getString("SERVER_MESSAGE"));

                                startActivity(new Intent(MainMenuFragment.this, NewGameLobbyFragment.class));
                                finish();
                            } else {
                                /*
                                    TODO: ShowSnackbar throw error in game create
                                 */
                                Toast.makeText(context, R.string.loginfailed, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

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
