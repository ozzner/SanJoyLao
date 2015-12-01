package rsantillanc.sanjoylao.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.SJLApplication;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.ui.custom.adapter.RecyclerOrderAdapter;
import rsantillanc.sanjoylao.ui.mvp.OrderHistory.IOrderHistoryView;
import rsantillanc.sanjoylao.ui.mvp.OrderHistory.OrderHistoryPresenter;
import rsantillanc.sanjoylao.util.Android;

public class OrderHistoryActivity extends BaseActivity implements IOrderHistoryView {

    //Views
    private RecyclerView rcvHistory;
    private Toolbar toolbar;

    private RecyclerOrderAdapter orderAdapter;
    private OrderHistoryPresenter presenter;
    private SJLApplication app;
    public static boolean isActive = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        init();
        initUIElements();
        configUIElements();
        payloads();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActive = false;
    }


    private void init() {
        presenter = new OrderHistoryPresenter(_context,this);
        app = ((SJLApplication) getApplication());
    }

    private void initUIElements() {
        rcvHistory = (RecyclerView) findViewById(R.id.rcv_orders_history);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void payloads() {
        if (app == null)
            app = ((SJLApplication) getApplication());

        presenter.loadOrderHistory(app.getCurrentUser().getObjectId());
    }

    private void configUIElements() {

        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Recycler view
        rcvHistory.setLayoutManager(new LinearLayoutManager(this));
        rcvHistory.setItemAnimator(new DefaultItemAnimator());
        rcvHistory.setHasFixedSize(false);
    }

    private void setupAdapter(List<OrderModel> data) {
        orderAdapter = new RecyclerOrderAdapter(data, this, true);
        rcvHistory.setAdapter(orderAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            close();
        else if (id == R.id.action_history_info)
            showToast("Version " + Android.getAppVersion(this));
        return true;
    }

    private void close() {
        finish();
    }

    //{Order History View}
    @Override
    public void hideLoader() {

    }

    @Override
    public void showLoader() {

    }

    @Override
    public void injectData(List<OrderModel> data) {
        setupAdapter(data);
    }

    @Override
    public void showMessage(CharSequence message) {
        showToast(message);
    }

    @Override
    public void refresh(Context c, String userID) {
        ((OrderHistoryActivity) c.getApplicationContext()).payloads();
    }


}
