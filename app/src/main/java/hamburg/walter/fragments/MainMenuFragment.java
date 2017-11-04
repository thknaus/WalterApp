package hamburg.walter.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import hamburg.walter.R;
import hamburg.walter.activities.MainActivity;
import hamburg.walter.data.Game;
import hamburg.walter.data.Store;
import hamburg.walter.data.User;
import hamburg.walter.sync.AsyncClient;
import hamburg.walter.sync.mJsonHttpResponseHandler;
import lib.IOSocket;

public class MainMenuFragment extends Fragment implements View.OnClickListener {
    private static void d(Object... stringableMessage) {
        Store.d("FavoriteFragment", stringableMessage);
    }

    private Button newgameBtn, exitGameBtn, joinGameBtn;
    private EditText playerNameTxt;
    private User user;
    private Game game;
    IOSocket socket;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
            Connext to webservice. socket.io
         */
        user = User.getInstance();
        try {
            socket = new IOSocket("connect");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mainmenu, container, false);

        newgameBtn = (Button) rootView.findViewById(R.id.button_newgame);
        joinGameBtn = (Button) rootView.findViewById(R.id.button_joingame);
        exitGameBtn = (Button) rootView.findViewById(R.id.button_exitgame);
        playerNameTxt = (EditText) rootView.findViewById(R.id.text_playername);

        newgameBtn.setOnClickListener(this);
        joinGameBtn.setOnClickListener(this);
        exitGameBtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_newgame:
                if (playerNameTxt.getText().equals("")) {
                    /*
                    TODO: ShowSnackbar choose playername
                     */
                    break;
                }
                user.setName(playerNameTxt.getText().toString());

                game = game.getInstance();
                RequestParams params = new RequestParams();
                params.put("hostuser", user.getID());

                AsyncClient.post("/newgame", params, new mJsonHttpResponseHandler(getContext()) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            if (response.getInt("SERVER_RESPONSE") == 1) {
                                game.setGameSessionId(response.getString("SERVER_MESSAGE"));
                                ((MainActivity) getActivity()).showFragment(NewGameLobbyFragment.class);
                            } else {
                                /*
                                    TODO: ShowSnackbar throw error in game create
                                 */
                                Toast.makeText(getContext(), R.string.loginfailed, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                break;
            case R.id.button_joingame:
                ((MainActivity) getActivity()).showFragment(JoinGameFragment.class);
                break;
            case R.id.button_exitgame:
                //todo
                break;
        }
    }
}
