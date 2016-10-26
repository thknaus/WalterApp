package hamburg.walter.fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import hamburg.walter.R;

public class NewGameFragment extends AppCompatActivity {

    private Button okayBtn;
    private Button cancleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_newgame);
    }
}
