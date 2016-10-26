package hamburg.walter.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import hamburg.walter.R;
import hamburg.walter.data.Store;
import hamburg.walter.fragments.MainMenuFragment;


public class MainActivity extends AppCompatActivity {

    private static void d(Object... stringableMessage) {
        Store.d("MainActivity", stringableMessage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, MainMenuFragment.class));
            }
        },0);
    }
}


