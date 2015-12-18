package rsantillanc.sanjoylao.ui.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.LocalRestaurantModel;
import rsantillanc.sanjoylao.model.ParseGeoPointModel;
import rsantillanc.sanjoylao.ui.mvp.Map.IMapView;
import rsantillanc.sanjoylao.ui.mvp.Map.MapPresenterImpl;

public class MapActivity extends FragmentActivity implements
        OnMapReadyCallback, IMapView {

    private GoogleMap map;
    private MapPresenterImpl presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //Data & context
        init();


    }

    private void init() {
        startMap();
        presenter = new MapPresenterImpl(MapActivity.this);
    }

    private void startMap() {
        // Obtain the SupportMapFragment and list notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    private void buildMarkers(List<LocalRestaurantModel> locals) {
        ParseGeoPointModel local = null;

        for (int i = 0; i < locals.size(); i++) {
            local = locals.get(i).getLocation();

            map.addMarker(new MarkerOptions().position(
                    new LatLng(
                            local.getLatitude(),
                            local.getLongitude())
            ).title(locals.get(i).getRestaurant().getName())
                    .snippet(locals.get(i).getAddress())).showInfoWindow();
        }

        if (local != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(local.getLatitude(), local.getLongitude()),16f));

        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
//        map.setOnMyLocationButtonClickListener(this);
        presenter.findLocals();
    }


    //{IMapView}

    @Override
    public void printLocals(List<LocalRestaurantModel> locals) {
        buildMarkers(locals);
    }

}
