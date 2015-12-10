package rsantillanc.sanjoylao.ui.custom.dialog;


import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.IOException;
import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.LocalRestaurantModel;
import rsantillanc.sanjoylao.model.LocationDeliveryModel;
import rsantillanc.sanjoylao.model.ParseGeoPointModel;
import rsantillanc.sanjoylao.model.RelationOrder;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.ui.custom.adapter.ProcessPagerAdapter;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by RenzoD on 18/11/2015.
 */
@SuppressLint("ValidFragment")
public class ProcessOrderDialog extends DialogFragment implements
        View.OnClickListener,
        OnMapReadyCallback,
        CompoundButton.OnCheckedChangeListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        RadioGroup.OnCheckedChangeListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerDragListener {


    private static final int BOOKING = 0;
    private static final int DELIVERY = 1;

    //Data passing
    private boolean isDelivery;
    private RelationOrder buildOrder;
    private UserModel user;

    //Views
    private Button btCancel;
    private Button btSend;
    private SwitchCompat switchHere;
    private AppCompatCheckBox chbCash;
    private ViewPager viewPager;
    private Spinner spinBookLocals;
    private EditText etCardNumber;
    private EditText etCardExpires;
    private EditText etCardCVV;
    private EditText etCardNames;
    private EditText etCash;
    private EditText typeTelephone;
    private EditText typeAddress;
    private EditText typeReference;
    private EditText etBookDNI;
    private EditText etBookPhone;
    private TextView etLocalDetails;


    //Properties
    OnProcessOrderClickListener listener;
    private ProcessPagerAdapter processAdapter;
    private GoogleMap googleMap;
    private View view = null;
    private SupportMapFragment mapFragment;
    private GoogleApiClient googleApiClient;
    private Location mLastLocation;
    private int step = 0;
    private Marker addMarker;
    private LatLng latLonOrder;


    @SuppressLint("ValidFragment")
    public ProcessOrderDialog(Bundle bundle) {
        if (bundle != null) {
            user = (UserModel) bundle.getSerializable(Const.EXTRA_USER);
            buildOrder = (RelationOrder) bundle.getSerializable(Const.EXTRA_BUILD_ORDER);
            isDelivery = bundle.getBoolean(Const.EXTRA_PARAM_IS_DELIVERY);
        }
    }


    public static ProcessOrderDialog newInstance(Bundle bundle) {
        return new ProcessOrderDialog(bundle);
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


        //Delivery
        switchHere = (SwitchCompat) view.findViewById(R.id.sw_here);
        typeTelephone = (EditText) view.findViewById(R.id.et_input_type_telephone);
        typeAddress = (EditText) view.findViewById(R.id.et_input_type_address);
        typeReference = (EditText) view.findViewById(R.id.et_input_type_reference);

        //Booking
        spinBookLocals = (Spinner) view.findViewById(R.id.spinner_process_locales);
        etBookDNI = (EditText) view.findViewById(R.id.et_order_booking_dni);
        etBookPhone = (EditText) view.findViewById(R.id.et_order_booking_phone);


        //Set listener
        btCancel.setOnClickListener(this);
        btSend.setOnClickListener(this);
        chbCash.setOnCheckedChangeListener(this);
        switchHere.setOnCheckedChangeListener(this);

        //Adapter
        processAdapter = new ProcessPagerAdapter();
        viewPager.setAdapter(processAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(Const.DEBUG, "onPageScrolled: " + position);
                step = position;
                if (position == processAdapter.getCount() - 1) {
                    btSend.setText(getActivity().getString(R.string.button_pay));
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(Const.DEBUG, "onPageSelected: " + position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e(Const.DEBUG, "onPageScrollStateChanged: " + state);
            }
        });

        if (user.getPhoneNumber() > 0)
            typeTelephone.setText(String.valueOf(user.getPhoneNumber()));

        //Load address
        buildLocalArrayAdapter(buildOrder.getLocalRestaurant().getLocals());

        //Map
        launchMap();

        //Google api client
        buildGoogleApiClient();

    }

    private void buildLocalArrayAdapter(List<LocalRestaurantModel> locals) {
        String[] array = new String[locals.size()];
        for (int i = 0; i < locals.size(); i++) {
            array[i] = locals.get(i).getAddress();
        }
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, array);
        spinBookLocals.setAdapter(adapter);
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
        //Obtain the SupportMapFragment and list notified when the googleMap is ready to be used.
        mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        step = 1;

        if (isDelivery) {
            viewPager.setCurrentItem(1);
        } else {
            viewPager.setCurrentItem(0);
        }

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

                    if (step == processAdapter.getCount() - 1) {
                        if (checkFieldsPayment()) {
                            getDialog().cancel();
                            listener.onClickSendButton();
                            addLocation(isDelivery ? DELIVERY : BOOKING);
                        }

                    } else {

                        if (isDelivery) {
                            if (checkFieldsDelivery()) {
                                step++;
                                viewPager.setCurrentItem(2);
                            }

                        } else {
                            //Is reserva
                            if (checkFieldsBooking()) {
                                step++;
                                viewPager.setCurrentItem(2);
                            }

                        }
                    }

                    break;
            }
        }
    }

    private void addLocation(int type) {
        final LocationDeliveryModel deliveryModel = new LocationDeliveryModel();
        switch (type) {
            case BOOKING:
                buildOrder.getOrder().setLocationDelivery(null);
                listener.onStart(buildOrder);
                break;
            case DELIVERY:
                final ParseObject delivery = ParseObject.create(Const.CLASS_LOCATION_DELIVERY);
                delivery.put("address", typeAddress.getText().toString());
                delivery.put("location", new ParseGeoPoint(latLonOrder.latitude, latLonOrder.longitude));
                delivery.put("reference", typeReference.getText().toString());
                delivery.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            deliveryModel.setObjectId(delivery.getObjectId());
                            deliveryModel.setCreatedAt(delivery.getCreatedAt().toString());
                            deliveryModel.setUpdatedAt(delivery.getUpdatedAt().toString());
                            deliveryModel.setLocation(new ParseGeoPointModel(latLonOrder.latitude, latLonOrder.longitude));
                            deliveryModel.setAddress(typeAddress.getText().toString());
                            deliveryModel.setReference(typeReference.getText().toString());
                            buildOrder.getOrder().setLocationDelivery(deliveryModel);
                            listener.onStart(buildOrder);
                        } else
                            listener.onError(e.getMessage()+ "\nerror code: "+e.getCode());

                    }
                });
                break;
        }
    }

    private boolean checkFieldsBooking() {

        if (etBookDNI.getText().toString().isEmpty() && etBookDNI.getText().length() < 8) {
            etBookDNI.requestFocus();
            etBookDNI.setError("Error. Longitud incorrecta.");
            return false;
        }

        if (etBookPhone.getText().toString().isEmpty() && etBookPhone.getText().length() < 11) {
            etBookPhone.requestFocus();
            etBookPhone.setError("Error. N° Telefónico incorrecto.");
            return false;
        }

//

        return true;
    }

    private boolean checkFieldsPayment() {

        if (chbCash.isChecked()) {
            if (etCash.getText().toString().isEmpty()) {
                etCash.requestFocus();
                etCash.setError("Monto requerido");
            } else
                return true;
        }

        if (etCardNumber.getText().toString().isEmpty() && etCardNumber.getText().length() < 8) {
            etCardNumber.requestFocus();
            etCardNumber.setError("Error número de tarjeta");
            return false;
        }

        if (etCardExpires.getText().toString().isEmpty() && etCardExpires.getText().length() < 4) {
            etCardExpires.requestFocus();
            etCardExpires.setError("Error.");
            return false;
        }

        if (etCardCVV.getText().toString().isEmpty() && etCardCVV.getText().length() < 3) {
            etCardCVV.requestFocus();
            etCardCVV.setError("Error. CVV 3 digit");
            return false;
        }

        if (etCardNames.getText().toString().isEmpty()) {
            etCardNames.requestFocus();
            etCardNames.setError("No puede estar vacío.");
            return false;
        }


        return true;
    }

    private boolean checkFieldsDelivery() {

        //Check address
        if (typeAddress.getText().toString().isEmpty()) {
            typeAddress.requestFocus();
            typeAddress.setError("Campo obligatorio.");
            return false;
        }

        //Check reference
        if (typeReference.getText().toString().isEmpty()) {
            typeReference.requestFocus();
            typeReference.setError("Este campo es necesario.");
            return false;
        }

        //Check telephone
        if (typeTelephone.getText().toString().isEmpty()) {
            typeTelephone.requestFocus();
            typeTelephone.setError("Campo requierido.");
            return false;
        }

        //Check telephone long
        if (typeTelephone.getText().toString().length() < 9) {
            typeTelephone.requestFocus();
            typeTelephone.setError("Ingrese un número valido");
            return false;
        }


        if (switchHere.isChecked()) {
            latLonOrder = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        } else {
            latLonOrder = addMarker.getPosition();
        }

        return true;

    }

    //{Google map callbacks}
    @Override
    public void onMapReady(GoogleMap gmap) {
        googleMap = gmap;
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMarkerDragListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();

        if (addMarker != null)
            addMarker.remove();

        addMarker = googleMap.addMarker(markerOptions
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_add))
                .draggable(true));

        getAddress(addMarker);


    }

    private void getAddress(Marker marker) {

        Address address = null;
        Geocoder coder = new Geocoder(getContext());
        List<Address> list_address = null;
        LatLng latLng = marker.getPosition();

        try {
            list_address = coder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            address = list_address.get(0);
            if (address.getSubLocality() != null)
                marker.setTitle(address.getSubLocality());
            else {
                marker.setTitle(address.getAdminArea());
            }
            marker.setSnippet(address.getThoroughfare());
        } catch (IOException e) {

            if (marker.isVisible()) {
                marker.hideInfoWindow();
            }

            e.printStackTrace();
        }

        marker.showInfoWindow();
        typeAddress.getText().clear();
        if (address != null)
            typeAddress.setText((address.getSubLocality() == null) ? address.getAdminArea() + ", " + address.getThoroughfare()
                    : address.getSubLocality() + ", " + address.getThoroughfare());

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId()) {

            case R.id.chb_process_cash:
                if (b)
                    enableCash(true);
                else
                    enableVisa(true);
                break;

            case R.id.sw_here:
                if (b) {
                    clearMap();
                    showMessage("Usaremos tu ubicación para traer el pedido.");
                } else
                    showMessage("Elige un punto en el mapa.");
                break;
        }
    }

    private void clearMap() {
        googleMap.clear();
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
        Toast toast = Toast.makeText(getActivity(), msj, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {


        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDrag(Marker marker) {
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        addMarker = marker;
        getAddress(marker);
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
        void onStart(RelationOrder buildOrder);
    }

}
