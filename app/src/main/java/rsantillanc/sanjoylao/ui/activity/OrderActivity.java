package rsantillanc.sanjoylao.ui.activity;

import android.content.Context;
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
import android.widget.TextView;

import java.util.ArrayList;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.BanquetModel;
import rsantillanc.sanjoylao.ui.custom.adapter.RecyclerViewOrderAdapter;
import rsantillanc.sanjoylao.util.Android;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.SJLStrings;

public class OrderActivity extends BaseActivity implements View.OnClickListener {

    //Views
    private Toolbar toolbar;
    private FloatingActionButton mFloatingActionButton;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private ArrayList<Object> mBanquets;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerViewOrderAdapter mOrderAdapter;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private double total = 0.0;
    private TextView tvPriceTotal;
    private TextView tvPercent;
    private TextView tvDiscount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mContext = getApplicationContext();

        //Init views
        initUIComponents();

        //Config
        setUpUIComponents();

    }

//    private void displayFragment(Fragment ui) {
//        FragmentManager man = getSupportFragmentManager();
//        FragmentTransaction tran = man.beginTransaction();
//        tran.replace(R.id.orders_fragment_content, ui).commit();
//    }


    //---------------------- [INIT  COMPONENTS]
    private void initUIComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_order_payment);
        mRecyclerView = (RecyclerView) findViewById(R.id.rcv_orders);

        //Price
        tvDiscount = (TextView)findViewById(R.id.tv_order_price_discount);
        tvPriceTotal = (TextView)findViewById(R.id.tv_order_price_total);
        tvPercent = (TextView)findViewById(R.id.tv_order_percent);
    }


    //---------------------- [SETUPS COMPONENTS]
    private void setUpUIComponents() {
        setUpToolbar();
        setUpPrice();
        setUpFloatingButton();
        setUpRecyclerView();
    }

    private void setUpPrice() {
        //Load dummy data
        BanquetModel ban= new BanquetModel();
        mBanquets = ban.dummyBanquets();

        for (Object banquet : mBanquets) {
            total += ((BanquetModel) banquet).getPrice();
        }

        tvPriceTotal.setText(Const.PRICE_PEN + SJLStrings.format(total,SJLStrings.FORMAT_MILES_EN));
        tvPriceTotal.setPaintFlags(tvPriceTotal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        tvPercent.setText(15 + "%" + "\ndesc.");
        tvDiscount.setText(Const.PRICE_PEN + SJLStrings.format((total * 0.85),SJLStrings.FORMAT_MILES_EN));

        getSupportActionBar().setTitle(tvDiscount.getText().toString());
    }

    private void setUpRecyclerView() {

        //Config recycler
        mLinearLayoutManager = new LinearLayoutManager(_context);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(false);

        //Set adapter
        mOrderAdapter = new RecyclerViewOrderAdapter(mBanquets,_context);
        mRecyclerView.setAdapter(mOrderAdapter);
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
            showToast("San Joy Lao | V."+ Android.getAppVersion(this));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    //---------------------- [CALLBAKCS]
    @Override
    public void onClick(View v) {
        if (v == mFloatingActionButton) {
            showSnackbar("snack", v);
        }
    }
}
