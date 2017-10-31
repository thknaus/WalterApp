package hamburg.walter.helper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class FragmentReplacer {
    /**
     * Find existing Fragment if tag is not null or create new instance, replace it and add to backStack if backStack
     *
     * @param id
     * @param fragmentManager
     */
    private static boolean change(int id, FragmentManager fragmentManager, Fragment newFrag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(id, newFrag).addToBackStack(fragmentManager.getClass().getName());
        transaction.commit();
        return true;
    }


    public static boolean replace(int id, FragmentManager fragmentManager, Class fragment, Bundle args) {
        try {
            Fragment newFrag = (Fragment) fragment.newInstance();

            if (args != null) {
                newFrag.setArguments(args);
            }

            return change(id, fragmentManager, newFrag);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean replace(int id, FragmentManager fragmentManager, Fragment fragment) {
        return change(id, fragmentManager, fragment);
    }
}