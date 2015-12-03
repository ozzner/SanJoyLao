package rsantillanc.sanjoylao.ui.custom.dialog;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.ui.custom.adapter.ProcessPagerAdapter;

/**
 * Created by RenzoD on 18/11/2015.
 */
public class ProcessOrderDialog extends DialogFragment implements View.OnClickListener, OnMapReadyCallback, CompoundButton.OnCheckedChangeListener {

    //Views
    private Button btCancel;
    private Button btSend;
    private AppCompatRadioButton appRbDelivery;
    private AppCompatRadioButton appRbBooking;
    private ViewPager viewPager;
    private AppCompatCheckBox chbCash;
    private EditText etCardNumber;
    private EditText etCardExpires;
    private EditText etCardCVV;
    private EditText etCardNames;
    private EditText etCash;


    //Properties
    OnProcessOrderClickListener listener;
    private ProcessPagerAdapter processAdapter;
    private GoogleMap mMap;
    private View view = null;
    SupportMapFragment mapFragment;


    public ProcessOrderDialog() {
    }


    public static ProcessOrderDialog newInstance() {
        return new ProcessOrderDialog();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.dialog_process_order, container);
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            getDialog().setCanceledOnTouchOutside(false);
            initUIElements(view);
        }
        return view;
    }

    private void initUIElements(View view) {
        //Get views

        btCancel = (Button) view.findViewById(R.id.bt_cancel);
        btSend = (Button) view.findViewById(R.id.bt_send);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_process_order);

        //Visa views
        etCardNumber = (EditText) view.findViewById(R.id.et_input_card_number);
        etCardExpires = (EditText) view.findViewById(R.id.et_input_card_expires);
        etCardCVV = (EditText) view.findViewById(R.id.et_input_card_cvv);
        etCardNames = (EditText) view.findViewById(R.id.et_input_card_names);

        //Cash views
        chbCash = (AppCompatCheckBox) view.findViewById(R.id.chb_process_cash);
        etCash = (EditText) view.findViewById(R.id.et_process_cash);


        //Location
        appRbBooking = (AppCompatRadioButton) view.findViewById(R.id.app_rb_types_1);
        appRbDelivery = (AppCompatRadioButton) view.findViewById(R.id.app_rb_types_2);


        //Set listener
        appRbDelivery.setOnClickListener(this);
        appRbBooking.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        btSend.setOnClickListener(this);
        chbCash.setOnCheckedChangeListener(this);

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

        //Map
        launchMap();
        focusMap();

//        Timer tm = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                Log.e(Const.DEBUG, "Change");
//                viewPager.setCurrentItem(0);
//            }
//        };
//
//        tm.schedule(task, 10500);

    }

    private void focusMap() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(-18.142, 178.431), 2));

    }

    private void launchMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        mMapFragment = MapFragment.newInstance();
//        FragmentTransaction fragmentTransaction =
//                getFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.my_container, mMapFragment);
//        fragmentTransaction.commit();

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
                    getDialog().onBackPressed();
                    break;
                case R.id.bt_send:
                    getDialog().cancel();
                    listener.onClickSendButton();
                    break;
            }

        }

    }


//    public void onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch (view.getId()) {
//            case R.id.app_rb_types_1:
//                if (checked)
//                    Toast.makeText(getActivity(), appRbBooking.getText().toString(), Toast.LENGTH_LONG).show();
//                break;
//            case R.id.app_rb_types_2:
//                if (checked)
//                    Toast.makeText(getActivity(), appRbDelivery.getText().toString(), Toast.LENGTH_LONG).show();
//                break;
//        }
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10));
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b)
            enableCash(true);
        else
            enableVisa(true);

    }

    private void enableVisa(boolean b) {
        etCardNumber.setEnabled(b);
        etCardExpires.setEnabled(b);
        etCardCVV.setEnabled(b);
        etCardNames.setEnabled(b);

        if (b)
            enableCash(!b);
    }

    private void enableCash(boolean b) {


        if (b) {
            enableVisa(!b);
            etCash.setVisibility(View.VISIBLE);
        } else
            etCash.setVisibility(View.GONE);

        if (etCash.getText().toString().isEmpty() && b)
            showMessage(getActivity().getString(R.string.info_chash));

    }

    private void showMessage(String msj) {
        Toast.makeText(getActivity(), msj, Toast.LENGTH_LONG).show();
    }


    public OnProcessOrderClickListener getListener() {
        return listener;
    }

    public void setListener(OnProcessOrderClickListener listener) {
        this.listener = listener;
    }

    public interface OnProcessOrderClickListener {
        void onClickSendButton();

        void onError(CharSequence sc);
    }

}
