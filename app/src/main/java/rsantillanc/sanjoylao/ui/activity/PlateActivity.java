package rsantillanc.sanjoylao.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.ui.mvp.Plate.IPlateView;
import rsantillanc.sanjoylao.ui.mvp.Plate.PlatePresenterImpl;
import rsantillanc.sanjoylao.util.Android;
import rsantillanc.sanjoylao.util.Const;

public class PlateActivity extends BaseActivity implements IPlateView {

    //View
    Toolbar mtoolbar;


    //Runtime
    PlatePresenterImpl mpresenter;


    //[Activity lifecycle ]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate);

        //Init
        init(getIntent().getExtras());

        //Views
        initUIElements();

        //Setups
        initSetUpsElements();
    }



    //[Navite properties]

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plate, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_plate_info) {
            showToast("San Joy Lao | V." + Android.getAppVersion(this));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    //[Custom properties]

    private void init(Bundle extras) {
        String categoryID = extras.getString(Const.EXTRA_CATEGORY_ID);
        mpresenter = new PlatePresenterImpl(this, this);
        mpresenter.loadPlatesByCategory(categoryID);
    }

    private void initUIElements() {
        mtoolbar = ((Toolbar) findViewById(R.id.toolbar));

    }

    private void initSetUpsElements() {
        setUpToolbar();
    }

    private void setUpToolbar() {
        String name = getIntent().getExtras().getString(Const.EXTRA_CATEGORY_NAME);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(name);
    }



    // [IView]

    @Override
    public void listPlatesByCategory(List<PlateModel> plates) {
    }

    @Override
    public void goToPlateDetail(PlateModel plate) {

    }
}
