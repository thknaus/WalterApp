package hamburg.walter.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hamburg.walter.R;
import hamburg.walter.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class InitFragment extends Fragment implements View.OnClickListener {

    private Button login, register_btn;


    public InitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_init, container, false);

        login = (Button) rootView.findViewById(R.id.login_btn);
        register_btn = (Button) rootView.findViewById(R.id.register_btn);

        login.setOnClickListener(this);
        register_btn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                ((MainActivity)getActivity()).showFragment(LoginFragment.class);
                break;
            case R.id.register_btn:
                ((MainActivity)getActivity()).showFragment(RegisterFragment.class);
                break;
        }

    }
}
