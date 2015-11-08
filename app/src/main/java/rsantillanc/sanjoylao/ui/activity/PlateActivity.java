package rsantillanc.sanjoylao.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.SJLApplication;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.ui.custom.adapter.RecyclerPlateAdapter;
import rsantillanc.sanjoylao.ui.mvp.Plate.IPlateView;
import rsantillanc.sanjoylao.ui.mvp.Plate.PlatePresenterImpl;
import rsantillanc.sanjoylao.util.Android;
import rsantillanc.sanjoylao.util.Const;

public class PlateActivity extends BaseActivity implements IPlateView, RecyclerPlateAdapter.OnItemPlateClickListener {

    //View
    Toolbar mtoolbar;
    RecyclerView mRecycler;


    //Runtime
    PlatePresenterImpl mpresenter;
    private SJLApplication app;


    //[Activity lifecycle ]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate);

        //Init
        init();

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
        }else {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    //[Custom properties]

    private void init() {
        mpresenter = new PlatePresenterImpl(this, this);
        app = ((SJLApplication) getApplication());
    }

    private void initUIElements() {
        mtoolbar = ((Toolbar) findViewById(R.id.toolbar));
        mRecycler = (RecyclerView) findViewById(R.id.rv_plates);
    }

    private void initSetUpsElements() {
        setUpToolbar();
        setUpRecyclerView(getIntent().getExtras());
    }

    private void setUpRecyclerView(Bundle extras) {
        String categoryID = extras.getString(Const.EXTRA_CATEGORY_ID);
        mpresenter.loadPlatesByCategory(categoryID);
    }


    private void setUpToolbar() {
        String name = getIntent().getExtras().getString(Const.EXTRA_CATEGORY_NAME);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpAdapter(List<PlateModel> plates) {
        RecyclerPlateAdapter ap = new RecyclerPlateAdapter(plates, this);
        ap.setOnItemPlateClickListener(this);
        mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(ap);
    }


    // {IView}

    @Override
    public void onPlatesLoadSuccess(List<PlateModel> plates) {
        setUpAdapter(plates);
    }

    @Override
    public void goToPlateDetail(PlateModel plate) {

    }

    @Override
    public void onError(CharSequence error) {
        showToast(error);
    }


    // {PLATE ITEM LISTENER}
    @Override
    public void onItemClick(PlateModel plate) {
        showToast(plate.getName());
    }

    @Override
    public void onAddPlateClick(View v) {
        showToast("Added!");
    }

    @Override
    public void onPopupItemClick(MenuItem item, PlateSizeModel plateSize) {
//        showToast("gropupId " +item.getGroupId() + " Id " + item.getItemId()+ " Name: " + item.getTitle());
        mpresenter.addPlateToOrder(plateSize,app.getUserLogued());
    }


    // {POPUP ITEM}



}
