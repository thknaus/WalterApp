package hamburg.walter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import hamburg.walter.R;
import hamburg.walter.sync.AsyncClient;
import hamburg.walter.sync.mJsonHttpResponseHandler;

/**
 * Created by Thomas on 23.10.2017.
 */

public class NewGameLobbyFragment extends AppCompatActivity implements View.OnClickListener  {

    EditText gameName;
    Button startBtn, backBtn;
    Context context;
    String player;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        context = this;

        gameName = (EditText)findViewById(R.id.gamename_text);
        startBtn = (Button)findViewById(R.id.button_startgame);
        backBtn = (Button)findViewById(R.id.button_startgameback);

        startBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_startgame:
                RequestParams params = new RequestParams();
                params.put("gamesession", gameName.getText());

                AsyncClient.post("/login", params, new mJsonHttpResponseHandler(context){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                        try{
                            if(response.getString("SERVER_MESSAGE") == null){

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
