package hamburg.walter.sync;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;
import hamburg.walter.R;

/**
 * Created by Thomas on 31.08.2017.
 */

public class AsyncClient {
    private static final String BASE_URL = "http://192.168.0.103:8080";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responsHandler){
        client.setTimeout(10000);
        client.get(getAbsoluteUrl(url), params, responsHandler);
        Log.v("AsyncClient - GET", getAbsoluteUrl(url));
    }
    
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responsHandler){
        client.post(getAbsoluteUrl(url), params, responsHandler);
        Log.v("Asyncclient - POST", getAbsoluteUrl(url));
    }
    
    public static void delete(String url, Header[] headers, Context context, AsyncHttpResponseHandler responsHandler){
        client.delete(context, getAbsoluteUrl(url), headers, responsHandler);
        Log.v("AsyncClient - DELETE", getAbsoluteUrl(url));
    }

    public static void put(String url, Header[] headers, Context context, AsyncHttpResponseHandler responsHandler){
        client.delete(context, getAbsoluteUrl(url), headers, responsHandler);
    }

    public static void doOnFailure(Context context, int errorCode){
        switch (errorCode){
            case 0:
                Toast.makeText(context, R.string.code_0, Toast.LENGTH_SHORT).show();
                break;
            case 404:
                Toast.makeText(context, R.string.code_404, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public static String getAbsoluteUrl(String relativUrl){
        return BASE_URL + relativUrl;
    }
}
