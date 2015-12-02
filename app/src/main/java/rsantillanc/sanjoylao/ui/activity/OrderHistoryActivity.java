package rsantillanc.sanjoylao.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.SJLApplication;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.ui.custom.adapter.RecyclerOrderAdapter;
import rsantillanc.sanjoylao.ui.mvp.OrderHistory.IOrderHistoryView;
import rsantillanc.sanjoylao.ui.mvp.OrderHistory.OrderHistoryPresenter;
import rsantillanc.sanjoylao.util.Android;
import rsantillanc.sanjoylao.util.Const;

public class OrderHistoryActivity extends BaseActivity implements IOrderHistoryView {

    //Views
    private RecyclerView rcvHistory;
    private Toolbar toolbar;

    private RecyclerOrderAdapter orderAdapter;
    private OrderHistoryPresenter presenter;
    private SJLApplication app;
    public static boolean isActive = false;
    private View view;



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
        presenter = new OrderHistoryPresenter(_context, this);
        app = ((SJLApplication) getApplication());
    }

    private void initUIElements() {
        rcvHistory = (RecyclerView) findViewById(R.id.rcv_orders_history);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void payloads() {
        if (app == null)
            app = ((SJLApplication) getApplication());

        presenter.loadOrderHistory(getApplicationContext(), app.getCurrentUser().getObjectId());
    }

    private void configUIElements() {

        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_activity_history));

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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(Const.DEBUG, "New intent!");

        if (intent.getExtras() == null)
            return;

        presenter.loopOrdersAndUpdate(
                (OrderModel) intent.getSerializableExtra(Const.EXTRA_ORDER),
                orderAdapter.getOrders());

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
        if (presenter == null)
            presenter = new OrderHistoryPresenter(this,this);

        presenter.loadOrderHistory(c,userID);
    }

    @Override
    public void updateOrdersAdapter(List<OrderModel> orders) {
        orderAdapter.setOrders(orders);
    }

    @Override
    public void refreshAdapter() {
        orderAdapter.notifyDataSetChanged();
    }


    public void showSnack() {
        showSnackbar(getString(R.string.notification_order_confirmed),
                rcvHistory, getString(R.string.ok));
    }
}
