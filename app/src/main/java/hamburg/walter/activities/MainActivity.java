package hamburg.walter.activities;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import hamburg.walter.R;
import hamburg.walter.fragments.InitFragment;
import hamburg.walter.fragments.LoginFragment;
import hamburg.walter.helper.FragmentReplacer;
import hamburg.walter.helper.ShowSnackbar;

public class MainActivity extends FragmentActivity {

    public static final String TAG = "Basic Network Demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View splashScreen =  findViewById(R.layout.splash_screen);

        checkNetworkConnection();
        showFragment(InitFragment.class);
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
        assert connMgr != null;
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            boolean wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            boolean mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
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

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        Log.d("onBackPressed count", count+"");
        if (count == 0) {
            // SnackBar quit Game?
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
        hideKeyboard();
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}


