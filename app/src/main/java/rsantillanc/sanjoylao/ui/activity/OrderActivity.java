package rsantillanc.sanjoylao.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.SJLApplication;
import rsantillanc.sanjoylao.model.LocalRestaurantModel;
import rsantillanc.sanjoylao.model.OrderDetailModel;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.model.OrderTypeModel;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.model.RelationLocalRestaurant;
import rsantillanc.sanjoylao.model.RelationOrder;
import rsantillanc.sanjoylao.ui.custom.adapter.RecyclerOrderAdapter;
import rsantillanc.sanjoylao.ui.custom.dialog.SJLAlertDialog;
import rsantillanc.sanjoylao.ui.custom.view.DividerItemDecoration;
import rsantillanc.sanjoylao.ui.mvp.Order.IOrderView;
import rsantillanc.sanjoylao.ui.mvp.Order.OrderPresenterImpl;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.MenuColorizer;
import rsantillanc.sanjoylao.util.SJLStrings;

public class OrderActivity extends BaseActivity implements
        View.OnClickListener,
        IOrderView,
        RecyclerOrderAdapter.OnOrderItemClickListener,
        AppBarLayout.OnOffsetChangedListener {

    private static final int BOOKING_POSITION = 0;
    private static final int DELIVERY_POSITION = 1;
    //Views
    private Toolbar toolbar;
    private FloatingActionButton fabMain;
    private FloatingActionButton fabBooking;
    private FloatingActionButton fabDelivery;
    private RecyclerView mRecyclerView;
    private TextView tvTotalPrice;
    private TextView tvPercent;
    private TextView tvDiscount;
    private ProgressBar mProgressBar;
    private ProgressDialog mProgressDialog;
    private AppBarLayout mAppBar;
    private View clickView;

    //Globals
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerOrderAdapter mOrdersAdapter;
    private RelationOrder buildOrder;
    private SJLApplication app;
    private boolean isOpen = false;
    private boolean isDelivery = false;
    private ArrayList<OrderTypeModel> ordersType;

    //MVP
    private OrderPresenterImpl presenter;
    private CollapsingToolbarLayout mCollapsing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //Data
        init();

        //Init views
        initUIComponents();

        //Config
        setUpUIComponents();

    }

    private void init() {
        app = ((SJLApplication) getApplication());
        presenter = new OrderPresenterImpl(this, OrderActivity.this);
        buildOrder = new RelationOrder();
        presenter.loadLocals();
        presenter.loadOrdersType();
    }


    //---------------------- [INIT  COMPONENTS]

    private void initUIComponents() {
        //Price
        tvDiscount = (TextView) findViewById(R.id.tv_order_price_discount);
        tvTotalPrice = (TextView) findViewById(R.id.tv_order_price_total);
        tvPercent = (TextView) findViewById(R.id.tv_order_percent);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fabMain = (FloatingActionButton) findViewById(R.id.fab_order_payment);
        fabBooking = (FloatingActionButton) findViewById(R.id.fab_order_booking);
        fabDelivery = (FloatingActionButton) findViewById(R.id.fab_order_delivery);
        mRecyclerView = (RecyclerView) findViewById(R.id.rcv_orders);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_orders);
        mCollapsing = (CollapsingToolbarLayout) findViewById(R.id.ctlLayout);
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
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        presenter.loadOrders();
    }


    private void setUpFloatingButton() {
        fabMain.setOnClickListener(this);
        fabBooking.setOnClickListener(this);
        fabDelivery.setOnClickListener(this);
    }


    //----------------[ Menu handler ]

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order, menu);
        int color = getResources().getColor(R.color.white);
        MenuColorizer.colorMenu(this, menu, color);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_orders_history) {
            goToOrderHistory();
            return true;
        } else if (id == android.R.id.home) {
            goToMainActivity();

        }
        return true;
    }

    private void goToOrderHistory() {
        Intent history = new Intent(getApplicationContext(), OrderHistoryActivity.class);
        history.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(history);

        if (mOrdersAdapter != null)
            presenter.saveChanges(mOrdersAdapter.getDetails());
    }

    @Override
    public void onBackPressed() {
        goToMainActivity();
    }

    private void goToMainActivity() {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Const.EXTRA_USER, app.getCurrentUser());

        Intent main = new Intent(getApplicationContext(), MainActivity.class);
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        main.putExtras(bundle);
        startActivity(main);

        if (mOrdersAdapter != null)
            presenter.saveChanges(mOrdersAdapter.getDetails());
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

    //OnClick
    @Override
    public void onClick(View v) {
        clickView = v;

        if (v == fabMain) {

            if (mOrdersAdapter == null) {
                showMessage(getString(R.string.error_empty_orders));
                return;
            } else if (!mOrdersAdapter.getDetails().isEmpty() || mOrdersAdapter.getDetails().size() > 0) {
                isOpen = !isOpen;
                showSubmenu(isOpen);
            } else
                showMessage(getString(R.string.error_empty_orders));

        } else if (v == fabBooking) {

            showMessage("Reserva");
            isDelivery = false;
            buildOrder.getCurrentOrder().setOrderType(ordersType.get(BOOKING_POSITION));
            presenter.showAlertDialogOrder(app.getCurrentUser(), buildOrder, isDelivery);

        } else if (v == fabDelivery) {

            isDelivery = true;
            buildOrder.getCurrentOrder().setOrderType(ordersType.get(DELIVERY_POSITION));
            presenter.showAlertDialogOrder(app.getCurrentUser(), buildOrder, isDelivery);
            showMessage("Delivery");

        }

    }

    private void showSubmenu(boolean on) {
        if (on) {

            fabBooking.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.FadeIn).duration(1000).playOn(fabBooking);

            fabDelivery.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.FadeIn).duration(500).playOn(fabDelivery);


        } else {
            YoYo.with(Techniques.FadeOut).duration(500).playOn(fabBooking);
            YoYo.with(Techniques.FadeOut).duration(1000).playOn(fabDelivery);

        }

    }


    // {IOrderView}

    @Override
    public void hideLoader() {
        mProgressBar.setVisibility(View.GONE);

        if (mProgressDialog != null)
            if (mProgressDialog.isShowing())
                mProgressDialog.cancel();

    }


    @Override
    public void onOrderDetailsLoaded(List<OrderDetailModel> orders) {
        mOrdersAdapter = new RecyclerOrderAdapter(orders, this);
        mOrdersAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mOrdersAdapter);
        presenter.saveSizeOrders(mOrdersAdapter.countAll());
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
            mCollapsing.requestLayout();
            mCollapsing.setTitle(getString(R.string.title_my_orders));
            mCollapsing.setTitleEnabled(true);
            showFabs(true);

            //Collapsed
        } else {
            showFabs(false);
            mCollapsing.setTitleEnabled(false);
            getSupportActionBar().setTitle(tvDiscount.getText().toString());
        }
    }

    private void showFabs(boolean on) {
        if (on) {
            if (isOpen)
                fabDelivery.setVisibility(View.VISIBLE);

            if (isOpen)
                fabBooking.setVisibility(View.VISIBLE);

            fabMain.setVisibility(View.VISIBLE);

        } else {
            if (isOpen)
                fabDelivery.setVisibility(View.INVISIBLE);

            if (isOpen)
                fabBooking.setVisibility(View.INVISIBLE);

            fabMain.setVisibility(View.INVISIBLE);
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
                        "        Wantan, pollo, langostino, chancho, pato, huevo de codorniz." +
                        "        </p>" +
                        "";
        plate.setIngredients(Html.fromHtml(HTML_BODY));
        SJLAlertDialog.showCustomAlert(OrderActivity.this, plate);
    }

    @Override
    public void onIncrementCount() {
        mOrdersAdapter.notifyDataSetChanged();
        presenter.buildTotalPrice(mOrdersAdapter.getDetails());

    }

    @Override
    public void onDecrementCount() {
        mOrdersAdapter.notifyDataSetChanged();
        presenter.buildTotalPrice(mOrdersAdapter.getDetails());
    }

    @Override
    public void onDeleteItem(OrderDetailModel itemDetail) {

        presenter.deleteItemDetail(getApplicationContext(), itemDetail);
        presenter.buildTotalPrice(mOrdersAdapter.getDetails());
        mOrdersAdapter.notifyDataSetChanged();
        counter = mOrdersAdapter.getDetails().size();
    }

    @Override
    public void onDeleteSuccess(CharSequence message) {
        showMessage(message);
        mOrdersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPaymentSuccess(double amount) {
        showSubmenu(false);
        buildOrder.getCurrentOrder().setPrice(amount);
        presenter.processOrder(buildOrder);
    }

    @Override
    public void showLoader(CharSequence message) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void enabledPaymentButton(boolean on) {
        fabMain.setEnabled(on);

        if (!on) {
            fabMain.setImageResource(R.drawable.vec_disabled);
            DrawableCompat.setTint(fabMain.getDrawable(), Color.WHITE);
        }

    }

    @Override
    public void updateMessageProgressDialog(CharSequence message) {
        if (mProgressDialog != null)
            if (mProgressDialog.isShowing())
                mProgressDialog.setMessage(message);
    }

    @Override
    public void clearAll() {
        mOrdersAdapter.getDetails().clear();
        mOrdersAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(CharSequence sc) {
        showToast(sc);
    }

    @Override
    public void orderCheckoutSuccess(CharSequence s, OrderModel order) {
        Snackbar.make(clickView, s, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .setAction(getString(R.string.action_goto_order), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToOrderHistory();
                    }
                }).show();
        presenter.sendPushNotification(order);
    }

    @Override
    public void localsLoaded(ArrayList<LocalRestaurantModel> locals) {
        RelationLocalRestaurant rel = new RelationLocalRestaurant();
        rel.setLocals(locals);
        buildOrder.setLocalRestaurant(rel);
    }

    @Override
    public void ordersTypeLoaded(ArrayList<OrderTypeModel> ordersTypeList) {
        //First is reserva (0) and last is delivery (1)
        this.ordersType = ordersTypeList;
        if (mOrdersAdapter != null)
            if (mOrdersAdapter.getDetails().size() > 0)
                this.buildOrder.setCurrentOrder(mOrdersAdapter.getDetails().get(0).getOrder());
    }

    @Override
    public void buildOrderSuccess(RelationOrder buildOrder) {
        this.buildOrder = buildOrder;

    }


}
