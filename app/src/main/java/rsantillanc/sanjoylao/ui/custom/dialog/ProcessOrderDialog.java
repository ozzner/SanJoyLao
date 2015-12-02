package rsantillanc.sanjoylao.ui.custom.dialog;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.*;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.ui.custom.adapter.ProcessPagerAdapter;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by RenzoD on 18/11/2015.
 */
public class ProcessOrderDialog extends DialogFragment implements View.OnClickListener, OnMapReadyCallback {

    //Views
    private Button btCancel;
    private Button btSend;
    private AppCompatRadioButton appRbDelivery;
    private AppCompatRadioButton appRbBooking;
    private ViewPager viewPager;

    //Properties
    OnProcessOrderClickListener listener;
    private ProcessPagerAdapter processAdapter;
    private GoogleMap mMap;


    public ProcessOrderDialog() {
    }


    public static ProcessOrderDialog newInstance() {
        return new ProcessOrderDialog();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_process_order, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        initUIElements(view);
        return view;
    }

    private void initUIElements(View view) {


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        appRbBooking = (AppCompatRadioButton) view.findViewById(R.id.app_rb_types_1);
        appRbDelivery = (AppCompatRadioButton) view.findViewById(R.id.app_rb_types_2);
        btCancel = (Button) view.findViewById(R.id.bt_cancel);
        btSend = (Button) view.findViewById(R.id.bt_send);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_process_order);


        //Set listener
        appRbDelivery.setOnClickListener(this);
        appRbBooking.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        btSend.setOnClickListener(this);

        //Adapter
        processAdapter = new ProcessPagerAdapter();
        viewPager.setAdapter(processAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        Timer tm = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Log.e(Const.DEBUG,"Change");
                viewPager.setCurrentItem(0);
            }
        };

        tm.schedule(task,10500);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onClick(View view) {
        if (view instanceof Button) {
            switch (view.getId()) {
                case R.id.bt_cancel:
                    getDialog().cancel();
                    break;
                case R.id.bt_send:
                    getDialog().cancel();
                    listener.onClickSendButton();
                    break;
            }

        }

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.app_rb_types_1:
                if (checked)
                    Toast.makeText(getActivity(),appRbBooking.getText().toString(),Toast.LENGTH_LONG).show();
                    break;
            case R.id.app_rb_types_2:
                if (checked)
                    Toast.makeText(getActivity(),appRbDelivery.getText().toString(),Toast.LENGTH_LONG).show();
                break;
        }
    }

    public OnProcessOrderClickListener getListener() {
        return listener;
    }

    public void setListener(OnProcessOrderClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public interface OnProcessOrderClickListener {
        void onClickSendButton();

        void onError(CharSequence sc);
    }

}
