package rsantillanc.sanjoylao.ui.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rsantillanc.sanjoylao.R;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {

    private static HomeFragment instance;
    private OnLoadSuccess load;
    private boolean isLoad = false;

    public static HomeFragment newInstance() {
        if (instance == null)
            instance =  new HomeFragment(null,false);

        return instance;
    }

    @SuppressLint("ValidFragment")
    public HomeFragment(OnLoadSuccess success, boolean flag) {
       this.load = success;
        this.isLoad = flag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLoad)
            load.viewloaded();
    }

    public interface OnLoadSuccess{
        void viewloaded();
    }

}
