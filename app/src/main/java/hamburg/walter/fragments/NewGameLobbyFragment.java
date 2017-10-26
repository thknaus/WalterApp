package hamburg.walter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


import hamburg.walter.R;
import hamburg.walter.data.Game;
import hamburg.walter.sync.AsyncClient;
import hamburg.walter.sync.mJsonHttpResponseHandler;

/**
 * Created by Thomas on 23.10.2017.
 */

public class NewGameLobbyFragment extends AppCompatActivity implements View.OnClickListener  {

    EditText gameName;
    Button startBtn, backBtn;
    TextView gameId;

    Game game;

    Context context;
    String player;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_newgamelobby);
        context = this;

        game = game.getInstance();

        gameId = (TextView)findViewById(R.id.game_id);
        gameId.setText(game.getGameSessionId());

        gameName = (EditText)findViewById(R.id.text_gamename);
        startBtn = (Button)findViewById(R.id.button_startgame);
        backBtn = (Button)findViewById(R.id.button_startgameback);

        startBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_startgame:
                if(gameName.getText().toString().equals("")){
                    /*
                        TODO: ShowSnackbar choose game name
                     */
                    break;
                }
                game.setGameName(gameName.toString());


                RequestParams params = new RequestParams();
                params.put("gameSessionId", game.getGameSessionId());
                params.put("gameName", gameName.getText().toString());

                AsyncClient.post("/updateGameFromClient", params, new mJsonHttpResponseHandler(context){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                        try{
                                if(response.getInt("SERVER_RESPONSE") == 1){


                            }
                        }catch (JSONException e){

                        }
                    }
                });
                break;
            case R.id.button_startgameback:
                break;
        }
    }
}
