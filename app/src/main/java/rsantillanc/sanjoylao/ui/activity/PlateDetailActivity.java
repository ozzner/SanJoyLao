package rsantillanc.sanjoylao.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.ui.mvp.PlateDetail.IPlateDetailView;

public class PlateDetailActivity extends BaseActivity implements IPlateDetailView {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate_detail);

        //Data & context
        init();

        //views
        initUIElements();

        //Config
        setupUIElements();


    }

    private void setupUIElements() {
        setSupportActionBar(toolbar);
    }

    private void initUIElements() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void init() {

    }

}
