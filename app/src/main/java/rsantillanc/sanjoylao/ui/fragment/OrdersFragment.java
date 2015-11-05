package rsantillanc.sanjoylao.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.ui.custom.adapter.RecyclerViewOrderAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {

    //Statics
    private static OrdersFragment instance;
    private double total = 0.0;

    //Globals
    private Context _context;
    private RecyclerViewOrderAdapter mOrderAdapter;
    private ArrayList<Object> mBanquets ;

    //Views
    private TextView tvPriceTotal;
    private TextView tvNumTable;
    private TextView tvDiscount;
    private TextView tvPercent;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;


    public OrdersFragment() {
        // Required empty public constructor
    }


    public static OrdersFragment newInstance() {
        instance = new OrdersFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_orders, container, false);
        _context = getActivity();

        initUIComponents(v);
        setUpUIComponents();
        return v;
    }

    //---------------------- [SETUPS COMPONENTS]
    private void setUpUIComponents() {
        setUpRecyclerView();
        setUpPriceBar();
    }

    private void setUpPriceBar() {

    }


    private void setUpRecyclerView() {


        //Config recycler
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(false);

        //Set adapter
        mOrderAdapter = new RecyclerViewOrderAdapter(mBanquets,_context);
        mRecyclerView.setAdapter(mOrderAdapter);
    }

    private void initUIComponents(View v) {

        /*Get views*/
        tvNumTable = (TextView) v.findViewById(R.id.tv_number_table);
        tvPriceTotal = (TextView) v.findViewById(R.id.tv_order_price_total);
        tvPercent = (TextView)v.findViewById(R.id.tv_order_percent);
        tvDiscount = (TextView)v.findViewById(R.id.tv_order_price_discount);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rcv_orders);

    }

    @Override
    public void onResume() {
        super.onResume();
        backToFrontPage();
    }

    private void backToFrontPage() {

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Fragment gotToFront = HomeFragment.newInstance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragments_content, gotToFront);
                    fragmentTransaction.commit();
                    return true;
                }
                return false;
            }
        });
    }
}
