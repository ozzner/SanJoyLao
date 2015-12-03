package rsantillanc.sanjoylao.ui.custom.dialog;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.ui.custom.adapter.ProcessPagerAdapter;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by RenzoD on 18/11/2015.
 */
public class ProcessOrderDialog extends DialogFragment implements
        View.OnClickListener,
        OnMapReadyCallback,
        CompoundButton.OnCheckedChangeListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, RadioGroup.OnCheckedChangeListener {

    //Views
    private Button btCancel;
    private Button btSend;
    private AppCompatRadioButton appRbDelivery;
    private AppCompatRadioButton appRbBooking;
    private SwitchCompat switchHere;
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
    private GoogleMap googleMap;
    private View view = null;
    SupportMapFragment mapFragment;
    private GoogleApiClient googleApiClient;
    private Location mLastLocation;
    private RadioGroup rgroup;


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

    @Override
    public void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FragmentManager fm = getActivity().getSupportFragmentManager();
//        mapFragment = (fm.findFragmentById(R.id.map));
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(mapFragment);
        ft.commit();
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
        rgroup = (RadioGroup) view.findViewById(R.id.rg_type_order);
        switchHere = (SwitchCompat) view.findViewById(R.id.sw_here);


        //Set listener
        appRbDelivery.setOnClickListener(this);
        appRbBooking.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        btSend.setOnClickListener(this);
        chbCash.setOnCheckedChangeListener(this);
        rgroup.setOnCheckedChangeListener(this);
        switchHere.setOnCheckedChangeListener(this);

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

        //Google api client
        buildGoogleApiClient();


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

    private void launchCamera() {
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(googleApiClient);

        if (mLastLocation != null) {
            googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                            new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 16f));

            googleMap.setMyLocationEnabled(true);
        }


    }

    /**
     * Creating google api client object
     */
    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    private void launchMap() {
//         Obtain the SupportMapFragment and get notified when the googleMap is ready to be used.

        mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//
//        mapFragment = SupportMapFragment.newInstance();
//        FragmentTransaction fragmentTransaction =
//                getFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.map_fragment_content, mapFragment);
//        fragmentTransaction.commit();

        mapFragment.getMapAsync(this);

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


    @Override
    public void onMapReady(GoogleMap gmap) {
        googleMap = gmap;
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.rg_type_order:
                if (b)
                    enableCash(true);
                else
                    enableVisa(true);
                break;
             case R.id.sw_here:
                 if (b)
                     showMessage("Usaremos tu ubicación para traer el pedido.");
                 else
                     showMessage("Elige un punto en el mapa.");


                 break;

        }



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


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {

            case R.id.app_rb_types_1:
                    Toast.makeText(getActivity(),
                            appRbBooking.getText().toString(), Toast.LENGTH_LONG).show();
                break;
            case R.id.app_rb_types_2:
                    Toast.makeText(getActivity(),
                            appRbDelivery.getText().toString(), Toast.LENGTH_LONG).show();
                break;
        }
    }


    //{Google Client}

    @Override
    public void onConnected(Bundle bundle) {
        Log.e(Const.DEBUG, "onConnected!");
        launchCamera();
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(Const.DEBUG, "Connection failed: ConnectionResult = "
                + connectionResult.getErrorCode());
    }


    //{Listener}

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
