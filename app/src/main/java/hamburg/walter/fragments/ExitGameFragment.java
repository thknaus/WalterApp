package hamburg.walter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import hamburg.walter.R;

public class ExitGameFragment extends AppCompatActivity {

    private Button okayBtn;
    private Button cancleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_exitgame);
        initOkay();
        initCancle();
    }

    private void initOkay() {
        okayBtn = (Button) findViewById(R.id.button_exit_yes);
        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    private void initCancle() {
        cancleBtn = (Button) findViewById(R.id.button_exit_no);
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(ExitGameFragment.this, MainMenuFragment.class));
            }
        }, 0);
    }

}
