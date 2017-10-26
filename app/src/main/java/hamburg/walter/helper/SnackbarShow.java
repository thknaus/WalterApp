package hamburg.walter.helper;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Achle on 26.10.2017.
 */

public class SnackbarShow {
    public void showSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make( view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
