package rsantillanc.sanjoylao.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import rsantillanc.sanjoylao.R;

public class PlateDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate_detail);

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
