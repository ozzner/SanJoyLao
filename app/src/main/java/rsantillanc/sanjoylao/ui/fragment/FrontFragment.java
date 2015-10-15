package rsantillanc.sanjoylao.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rsantillanc.sanjoylao.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrontFragment extends Fragment {


    private static FrontFragment instance;

    public FrontFragment() {
        // Required empty public constructor
    }

    public static FrontFragment getInstance() {
        if (instance == null)
            instance =  new FrontFragment();

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_front, container, false);
    }


}
