package rsantillanc.sanjoylao.ui.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.ui.custom.adapter.RecyclerViewOrderAdapter;
import rsantillanc.sanjoylao.ui.mvp.Order.IOrderView;
import rsantillanc.sanjoylao.ui.mvp.Order.OrderPresenterImpl;
import rsantillanc.sanjoylao.util.Android;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.SJLStrings;

public class OrderActivity extends BaseActivity
        implements View.OnClickListener, IOrderView {

    //Views
    private Toolbar toolbar;
    private FloatingActionButton mFloatingActionButton;
    private RecyclerView mRecyclerView;
    private TextView tvPriceTotal;
    private TextView tvPercent;
    private TextView tvDiscount;

    //Globals

    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerViewOrderAdapter mOrderAdapter;
    private double total = 0.0;

    //MVP
    private OrderPresenterImpl mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mPresenter = new OrderPresenterImpl(this);

        //Init views
        initUIComponents();

        //Config
        setUpUIComponents();

    }


    //---------------------- [INIT  COMPONENTS]
    private void initUIComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_order_payment);
        mRecyclerView = (RecyclerView) findViewById(R.id.rcv_orders);

        //Price
        tvDiscount = (TextView) findViewById(R.id.tv_order_price_discount);
        tvPriceTotal = (TextView) findViewById(R.id.tv_order_price_total);
        tvPercent = (TextView) findViewById(R.id.tv_order_percent);
    }


    //---------------------- [SETUPS COMPONENTS]
    private void setUpUIComponents() {
        setUpToolbar();
        setUpFloatingButton();
        setUpRecyclerView();
    }


    private void setUpRecyclerView() {

        //Config recycler
        mLinearLayoutManager = new LinearLayoutManager(_context);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(false);
    }


    private void setUpFloatingButton() {
        mFloatingActionButton.setOnClickListener(this);
    }


    //----------------[ Menu handler ]

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
            showToast("San Joy Lao | V." + Android.getAppVersion(this));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void enableDiscountView() {
        tvPercent.setVisibility(View.VISIBLE);
        tvDiscount.setVisibility(View.VISIBLE);
    }


    //---------------------- [CALLBAKCS]

    //Onclik
    @Override
    public void onClick(View v) {
        if (v == mFloatingActionButton) {
            showSnackbar("snack", v);
        }
    }


    // IOrderView
    @Override
    public void showLoader() {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void openItem(Object item) {

    }

    @Override
    public void upPrice() {

    }

    @Override
    public void downPrice() {

    }

    @Override
    public void deleteItem(int position) {

    }

    @Override
    public void showPaymentMethod() {

    }

    @Override
    public void onDataLoaded(List<Object> orders) {
        //Set adapter
        mOrderAdapter = new RecyclerViewOrderAdapter(orders, _context);
        mRecyclerView.setAdapter(mOrderAdapter);
    }

    @Override
    public void printAmount(double amount) {

        tvPriceTotal.setText(Const.PRICE_PEN + SJLStrings.format(total, SJLStrings.FORMAT_MILES_EN));
        tvPriceTotal.setPaintFlags(tvPriceTotal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public void printDiscount(double amount) {
        enableDiscountView();
        tvPercent.setText(15 + "%" + "\ndesc.");
        tvDiscount.setText(Const.PRICE_PEN + SJLStrings.format((total * 0.85), SJLStrings.FORMAT_MILES_EN));
        getSupportActionBar().setTitle(tvDiscount.getText().toString());
    }


}
