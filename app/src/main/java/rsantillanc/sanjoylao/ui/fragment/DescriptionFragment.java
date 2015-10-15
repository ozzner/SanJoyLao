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
public class DescriptionFragment extends Fragment {
    private static DescriptionFragment instance;

    public static DescriptionFragment newInstance(String param1, String param2) {
            instance =  new DescriptionFragment();
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_description, container, false);
    }


}
