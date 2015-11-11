package rsantillanc.sanjoylao.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.OrderDetailModel;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.ui.custom.adapter.RecyclerOrderAdapter;
import rsantillanc.sanjoylao.ui.custom.dialog.SJLAlertDialog;
import rsantillanc.sanjoylao.ui.mvp.Order.IOrderView;
import rsantillanc.sanjoylao.ui.mvp.Order.OrderPresenterImpl;
import rsantillanc.sanjoylao.util.Android;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.SJLStrings;

public class OrderActivity extends BaseActivity implements
        View.OnClickListener,
        IOrderView,
        RecyclerOrderAdapter.OnOrderItemClickListener, AppBarLayout.OnOffsetChangedListener {

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
    private AppBarLayout mAppBar;


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
        //Price
        tvDiscount = (TextView) findViewById(R.id.tv_order_price_discount);
        tvTotalPrice = (TextView) findViewById(R.id.tv_order_price_total);
        tvPercent = (TextView) findViewById(R.id.tv_order_percent);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_order_payment);
        mRecyclerView = (RecyclerView) findViewById(R.id.rcv_orders);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_orders);
        mCollapsiong = (CollapsingToolbarLayout) findViewById(R.id.ctlLayout);
        mAppBar = (AppBarLayout) findViewById(R.id.appbarLayout);
    }


    //---------------------- [SETUPS COMPONENTS]
    private void setUpUIComponents() {
        setUpToolbar();
        setUpFloatingButton();
        setUpRecyclerView();
        setUpAppBarLayout();
    }

    private void setUpAppBarLayout() {
        mAppBar.addOnOffsetChangedListener(this);
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
        tvPercent.setVisibility(View.INVISIBLE);
        tvTotalPrice.setVisibility(View.INVISIBLE);
    }


    //---------------------- [CALLBAKCS]

    //Onclik
    @Override
    public void onClick(View v) {
        if (v == mFloatingActionButton) {
            showToast("Open payment methods!");
        }
    }


    // {IOrderView}

    @Override
    public void hideLoader() {
        mProgressBar.setVisibility(View.GONE);
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

        enableAllPriceViews();

    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offSet) {

        //Open
        if (Math.abs(offSet) / 2 <= toolbar.getHeight()) {
            mCollapsiong.requestLayout();
            mCollapsiong.setTitle(getString(R.string.title_my_orders));
            mCollapsiong.setTitleEnabled(true);

            //Collapsed
        } else {
            mCollapsiong.setTitleEnabled(false);
            getSupportActionBar().setTitle(tvDiscount.getText().toString());
        }
    }

    //{ON ORDERS ITEM LISTENER}

    @Override
    public void onItemClick(View v, int index) {
        showToast("onItemClick");
    }

    @Override
    public void onOpenImage(PlateSizeModel plateSize) {
        Intent viewer = new Intent(getApplicationContext(), ViewerActivity.class);
        startActivity(viewer);
    }

    @Override
    public void onOpenIngredients(PlateModel plate) {
        String HTML_BODY =
                "              <p style=\"text-align: justify;\">" +
                        "        <h4><font color='#D32F2F'>" + getString(R.string.label_description) + "</font></h4>" +
                        "        <font color='#607D8B'>" +
                        "        La sopa wantán es una sopa china, hecha a base de caldo de pollo, carne de pollo, cerdo y Wantán.<br><br>" +

                        "        Usualmente lleva tres o cuatro wantanes y se sirve con cebolla china.\n<br><br>" +
                        "        Esta sopa también puede llevar col y fideos chinos, además de langostinos y por" +
                        "        lo general se le agregan sillao (salsa de soya).<br><br>" +

                        "        Es usual consumirla previa a un plato de fondo como Chow mein o arroz frito.\n" +
                        "        </font><br><br><br>" +
                        "        <h4><font color='#D32F2F'>" + getString(R.string.label_ingredients) + "</font></h4>" +
                        "        <b>Wantan, pollo, langostino, chancho, pato, huevo de codorniz.<b>\n<br><br><br>" +
                        "        </p>" +
                        "";
        plate.setIngredients(Html.fromHtml(HTML_BODY));
        SJLAlertDialog.showCustomAlert(OrderActivity.this, plate);
    }

    @Override
    public void onIncrementCount() {
        mOrdersAdapter.notifyDataSetChanged();
        mPresenter.buildTotalPrice(mOrdersAdapter.getOrders());
    }

    @Override
    public void onDecrementCount() {
        mOrdersAdapter.notifyDataSetChanged();
        mPresenter.buildTotalPrice(mOrdersAdapter.getOrders());
    }

    @Override
    public void onDeleteItem(OrderDetailModel itemDetail) {
        mPresenter.deleteAnItemDetail(getApplicationContext(), itemDetail);
        mPresenter.buildTotalPrice(mOrdersAdapter.getOrders());
        mOrdersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteSuccess(CharSequence message) {
        showToast(message);
    }


}
