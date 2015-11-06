package rsantillanc.sanjoylao.ui.fragment;


import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.ui.activity.MapActivity;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment implements View.OnClickListener {

    private static HomeFragment instance;
    private OnLoadSuccess load;
    private boolean isLoad = false;
    private FloatingActionButton fabMapa;



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

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initUIComponent(view);

        return view;



    }

    private void initUIComponent(View view) {
        fabMapa = ((FloatingActionButton) view.findViewById(R.id.map_id));
        fabMapa.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLoad)
            load.viewloaded();
    }

    @Override
    public void onClick(View v) {

    goToMapaActivity();

    }

    private void goToMapaActivity() {
        Intent i = new Intent(getActivity(), MapActivity.class);
        startActivity(i);
    }

    public interface OnLoadSuccess{
        void viewloaded();
    }




}
