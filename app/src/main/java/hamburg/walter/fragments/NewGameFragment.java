package hamburg.walter.fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import hamburg.walter.R;

public class NewGameFragment extends AppCompatActivity implements View.OnClickListener {

    private Button addplayer, addplayer1, addplayer2, addplayer3, addplayer4, addplayer5, addplayer6;
    private int numberPlayer = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_newgame);

        addplayer = (Button) findViewById(R.id.button_newgame_addplayer);

        addplayer1 = (Button) findViewById(R.id.button_newgame_player1);
        addplayer2 = (Button) findViewById(R.id.button_newgame_player2);
        addplayer3 = (Button) findViewById(R.id.button_newgame_player3);
        addplayer4 = (Button) findViewById(R.id.button_newgame_player4);
        addplayer5 = (Button) findViewById(R.id.button_newgame_player5);
        addplayer6 = (Button) findViewById(R.id.button_newgame_player6);

        addplayer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_newgame_addplayer:
                numberPlayer += 1;
                newPlayer();
                break;
        }
    }
    private void newPlayer() {
        switch(numberPlayer) {
            case 2:
                addplayer2.setVisibility(View.VISIBLE);
                break;
            case 3:
                addplayer3.setVisibility(View.VISIBLE);
                break;
            case 4:
                addplayer4.setVisibility(View.VISIBLE);
                break;
            case 5:
                addplayer5.setVisibility(View.VISIBLE);
                break;
            case 6:
                addplayer6.setVisibility(View.VISIBLE);
                break;
        }
    }
}
