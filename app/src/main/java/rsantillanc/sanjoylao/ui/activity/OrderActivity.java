package rsantillanc.sanjoylao.ui.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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
import rsantillanc.sanjoylao.model.OrderDetailModel;
import rsantillanc.sanjoylao.ui.custom.adapter.RecyclerOrderAdapter;
import rsantillanc.sanjoylao.ui.mvp.Order.IOrderView;
import rsantillanc.sanjoylao.ui.mvp.Order.OrderPresenterImpl;
import rsantillanc.sanjoylao.util.Android;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.SJLStrings;

public class OrderActivity extends BaseActivity
        implements View.OnClickListener, IOrderView, RecyclerOrderAdapter.OnOrderItemClickListener {

    private static final double MIN_TO_DISCOUNT = 100.00;
    //Views
    private Toolbar toolbar;
    private FloatingActionButton mFloatingActionButton;
    private RecyclerView mRecyclerView;
    private TextView tvTotalPrice;
    private TextView tvPercent;
    private TextView tvDiscount;
    private ProgressBar mProgressBar;

    //Globals
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerOrderAdapter mOrdersAdapter;
    private double total = 0.0;

    //MVP
    private OrderPresenterImpl mPresenter;
    private CollapsingToolbarLayout mCollapsiong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mPresenter = new OrderPresenterImpl(this, OrderActivity.this);

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
        mProgressBar = (ProgressBar) findViewById(R.id.pb_orders);
        mCollapsiong = (CollapsingToolbarLayout) findViewById(R.id.ctlLayout);

        //Price
        tvDiscount = (TextView) findViewById(R.id.tv_order_price_discount);
        tvTotalPrice = (TextView) findViewById(R.id.tv_order_price_total);
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
        mPresenter.loadOrders();
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

    private void enableAllPriceViews() {
        tvPercent.setVisibility(View.VISIBLE);
        tvDiscount.setVisibility(View.VISIBLE);
        tvTotalPrice.setVisibility(View.VISIBLE);
    }

    private void enableTotalPrice() {
        tvDiscount.setVisibility(View.VISIBLE);
    }


    //---------------------- [CALLBAKCS]

    //Onclik
    @Override
    public void onClick(View v) {
        if (v == mFloatingActionButton) {
            showToast("Open payment methods!");
        }
    }


    // IOrderView
    @Override
    public void showLoader() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        mProgressBar.setVisibility(View.GONE);
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
    public void onOrderDetailsLoaded(List<OrderDetailModel> orders) {
        //Set adapter
        mOrdersAdapter = new RecyclerOrderAdapter(orders, _context);
        mOrdersAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mOrdersAdapter);
    }

    @Override
    public void printAmount(double amount) {
        tvDiscount.setText(Const.PRICE_PEN + SJLStrings.format(amount, SJLStrings.FORMAT_MILES_EN));
        mCollapsiong.setTitle(getString(R.string.title_my_orders));
        enableTotalPrice();
    }

    @Override
    public void printDiscount(double amount, double amountWithDiscount, CharSequence percent) {

        //Discount
        tvPercent.setText(percent);
        tvDiscount.setText(Const.PRICE_PEN + SJLStrings.format(amountWithDiscount, SJLStrings.FORMAT_MILES_EN));

        //Total
        tvTotalPrice.setText(Const.PRICE_PEN + SJLStrings.format(amount, SJLStrings.FORMAT_MILES_EN));
        tvTotalPrice.setPaintFlags(tvTotalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        //Title
        mCollapsiong.setTitle(getString(R.string.title_my_orders));
        enableAllPriceViews();
    }


    //{ON ORDERS ITEM LISTENER}

    @Override
    public void onItemClick(View v, int index) {
        showToast("onItemClick");
    }

    @Override
    public void onOpenImage() {
        showToast("onOpenImage");
    }

    @Override
    public void onOpenIngredients() {
        showToast("onOpenIngredients");
    }

    @Override
    public void onAddCount() {
        mOrdersAdapter.notifyDataSetChanged();
        mPresenter.buildTotalPrice(mOrdersAdapter.getOrders());
    }

    @Override
    public void onRemoveCount() {
        mOrdersAdapter.notifyDataSetChanged();
        mPresenter.buildTotalPrice(mOrdersAdapter.getOrders());
    }

    @Override
    public void onDeleteItem() {
        showToast("onDeleteItem");
    }
}
