package hamburg.walter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import hamburg.walter.data.Store;
import hamburg.walter.fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private static void d(Object... stringableMessage) {
        Store.d("MainActivity", stringableMessage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(MainActivity.this, LoginFragment.class));
    }
}


