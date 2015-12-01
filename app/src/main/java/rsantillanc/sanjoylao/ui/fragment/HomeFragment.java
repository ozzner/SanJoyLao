package rsantillanc.sanjoylao.ui.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private TextView tvAddress;
    private LinearLayout layLocals;
    private LinearLayout layDelivery;


    public static HomeFragment newInstance() {
        if (instance == null)
            instance = new HomeFragment(null, false);

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

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initUIComponent(view);
        setupUIComponent();
        return view;

    }

    private void setupUIComponent() {
        tvAddress.setText(Html.fromHtml(getActivity().getString(R.string.sjl_home_address)));
        layDelivery.setOnClickListener(this);
        layLocals.setOnClickListener(this);
    }

    private void initUIComponent(View view) {
        tvAddress = (TextView) view.findViewById(R.id.tv_home_address);
        layLocals = (LinearLayout) view.findViewById(R.id.lay_locals);
        layDelivery = (LinearLayout) view.findViewById(R.id.lay_delivery);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLoad)
            load.viewLoaded();
    }

    @Override
    public void onClick(View v) {

        if (v instanceof LinearLayout) {
            switch (v.getId()) {
                case R.id.lay_locals:
                    goToMapActivity();
                    break;
                case R.id.lay_delivery:
                    call(getActivity());
                    break;
            }
        }


    }

    private void goToMapActivity() {
        Intent i = new Intent(getActivity(), MapActivity.class);
        startActivity(i);
    }

    public static void call(Activity activity){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:012790888"));
        activity.startActivity(intent);
    }

    public interface OnLoadSuccess {
        void viewLoaded();
    }


}
