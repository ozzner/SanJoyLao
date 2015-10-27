package rsantillanc.sanjoylao.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.util.Android;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    private FloatingActionButton mFloatingActionButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Init views
        initUIComponents();

        //Config
        setUpUIComponents();

    }





    //---------------------- [INIT  COMPONENTS]
    private void initUIComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_profile_save);
    }


    //---------------------- [SETUPS COMPONENTS]
    private void setUpUIComponents() {
        setUpToolbar();
        setUpFloatingButton();
    }

    private void setUpFloatingButton() {
        mFloatingActionButton.setOnClickListener(this);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order, menu);
//        int color = getResources().getColor(R.color.white);
//        MenuColorizer.colorMenu(this, menu, color);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_orders_history) {
            showToast("San Joy Lao | V."+ Android.getAppVersion(this));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    //---------------------- [CALLBAKCS]
    @Override
    public void onClick(View v) {

    }
}
