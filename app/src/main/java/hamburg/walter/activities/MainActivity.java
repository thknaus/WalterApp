package hamburg.walter.activities;

import android.content.Intent;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import hamburg.walter.R;
import hamburg.walter.fragments.LoginFragment;
import hamburg.walter.helper.FragmentReplacer;

public class MainActivity extends FragmentActivity {

    public static final String TAG = "Basic Network Demo";
    // Whether there is a Wi-Fi connection.
    private static boolean wifiConnected = false;
    // Whether there is a mobile connection.
    private static boolean mobileConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View splashScreen =  findViewById(R.layout.splash_screen);
        
        checkNetworkConnection();
        showFragment(LoginFragment.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* switch (item.getItemId()) {
            // When the user clicks TEST, display the connection status.
            case R.id.test_action:
                checkNetworkConnection();
                return true;
            // Clear the log view fragment.
            case R.id.clear_action:
                mLogFragment.getLogView().setText("");
                return true;
        }*/
        return false;
    }


    private void checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if(wifiConnected) {
                Log.i(TAG, "wifi_connection");
            } else if (mobileConnected){
                Log.i(TAG, "mobile_connection");
            }
        } else {
            Log.i(TAG,"no_wifi_or_mobile");
        }
    }

    public void showFragment(Class fragment) {
        showFragment(fragment, null);
    }

    public void showFragment(Fragment fragment, Bundle args) {
        FragmentReplacer.replace(R.id.main_activity_container, getSupportFragmentManager(), fragment);
    }

    public void showFragment(Class fragment, Bundle args) {
        FragmentReplacer.replace(R.id.main_activity_container, getSupportFragmentManager(), fragment, args);
    }
}


