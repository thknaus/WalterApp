package hamburg.walter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


import hamburg.walter.R;
import hamburg.walter.data.Game;
import hamburg.walter.helper.ShowSnackbar;
import hamburg.walter.sync.AsyncClient;
import hamburg.walter.sync.mJsonHttpResponseHandler;

/**
 * Created by Thomas on 23.10.2017.
 */

public class NewGameLobbyFragment extends Fragment implements View.OnClickListener  {

    EditText gameName;
    Button startBtn, backBtn;
    TextView gameId;
    private String name;

    Game game;

    Context context;
    String player;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        game = game.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_newgamelobby, container, false);

        gameId = (TextView) rootView.findViewById(R.id.game_id);
        gameId.setText(game.getGameSessionId());

        gameName = (EditText) rootView.findViewById(R.id.text_gamename);
        startBtn = (Button) rootView.findViewById(R.id.button_startgame);
        backBtn = (Button) rootView.findViewById(R.id.button_startgameback);

        startBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_startgame:
                name = gameName.getText().toString();
                if(name.isEmpty()){
                    new ShowSnackbar().showSnackbar(getView(), "Bitte wähle einen Namen für das Spiel");
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
