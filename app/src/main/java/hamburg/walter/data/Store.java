package hamburg.walter.data;

import android.util.Log;

import hamburg.walter.BuildConfig;


public class Store {

    private static final String PRODUCTION_URL = "http://10.0.2.2:8080/api/resetpass";

    private final static boolean DEBUG = BuildConfig.DEBUG;
    public static void d(String name, Object... stringableMessage) {
        if (DEBUG) {
            StringBuilder s = new StringBuilder();
            s.append(name).append(" \t->\t ");
            for (Object o : stringableMessage) {
                s.append(o != null ? o.toString() : "null").append(" | ");
            }

            Log.d("Walter", s.toString());
        }
    }
}

