package rsantillanc.sanjoylao.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.SJLApplication;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.model.RelationPlateSizeModel;
import rsantillanc.sanjoylao.ui.custom.adapter.RecyclerPlateAdapter;
import rsantillanc.sanjoylao.ui.mvp.Plate.IPlateView;
import rsantillanc.sanjoylao.ui.mvp.Plate.PlatePresenterImpl;
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

        if (id == R.id.action_plate_add_order) {
            goToOrderActivity();
            return true;
        } else {
            finish();
            return true;
        }
    }

    private void goToOrderActivity() {
        Intent order = new Intent(getApplicationContext(), OrderActivity.class);
        order.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        order.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(order);
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

    private void setUpAdapter(List<RelationPlateSizeModel> relations) {
        RecyclerPlateAdapter ap = new RecyclerPlateAdapter(relations, this);
        ap.setOnItemPlateClickListener(this);
        mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(ap);
    }


    // {IView}

    @Override
    public void onPlatesLoadSuccess(List<RelationPlateSizeModel> relations) {
        setUpAdapter(relations);
    }

    @Override
    public void goToPlateDetail(PlateModel plate) {

    }

    @Override
    public void onError(CharSequence error) {
        showToast(error);
    }

    @Override
    public void onPlateAddOrderCorrect(Context c, int size) {
        Toast.makeText(c, "Agregado en total: " + size, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlateCounterUpdated(Context c, CharSequence ok) {
        Toast.makeText(c, ok, Toast.LENGTH_SHORT).show();
    }


    // {PLATE ITEM LISTENER}
    @Override
    public void onItemClick(PlateModel plate) {
        goToPlateDetailActivity(plate);
    }

    private void goToPlateDetailActivity(PlateModel plate) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.EXTRA_PLATE_DETAIL,plate);

        Intent in = new Intent(getApplicationContext(), PlateDetailActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        in.putExtras(bundle);

        startActivity(in);
    }

    @Override
    public void onAddPlateClick(View v) {
        showToast("Added!");
    }

    @Override
    public void onPopupItemClick(MenuItem item, PlateSizeModel plateSize) {
        mpresenter.addPlateToOrder(plateSize, app.getUserLogued());
    }

}
