package rsantillanc.sanjoylao.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.custom.adapter.ListViewAdapter;
import rsantillanc.sanjoylao.model.BanquetModel;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.Strings;
import rsantillanc.sanjoylao.view.activity.SurveyActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {

    private static OrdersFragment instance;
    private ListView mListView;
    private TextView tvPriceTotal,tvNumTable;
    private Button btConfirm;
    private double total = 0.0;
    private List<Object> orders;
    private Context _context;
    private TextView tvDiscount;
    private TextView tvPercent;

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
        initComponents(v);
        return v;
    }

    private void initComponents(View v) {
        _context = getActivity();

        /*Get views*/
        mListView = (ListView) v.findViewById(R.id.lv_orders);
        tvNumTable = (TextView) v.findViewById(R.id.tv_number_table);
        tvPriceTotal = (TextView) v.findViewById(R.id.tv_order_price_total);
        btConfirm = (Button)v.findViewById(R.id.bt_confirm_order);
        tvPercent = (TextView)v.findViewById(R.id.tv_order_percent);
        tvDiscount = (TextView)v.findViewById(R.id.tv_order_price_discount);


        //Delete after this
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent survey = new Intent(_context, SurveyActivity.class);
                startActivity(survey);
            }
        });

        /*Setup*/
        BanquetModel oModel = new BanquetModel();
        orders = oModel.testData();
        tvNumTable.setText("PLATOS DEL PEDIDO");//Delete hardCode
        mListView.setAdapter(new ListViewAdapter(getActivity(), orders, Const.ORDERS));

        for (Object order : orders) {
            total += ((BanquetModel) order).getPrice();
        }

        DecimalFormat myFormatter = new DecimalFormat("##,###.##", DecimalFormatSymbols.getInstance(Locale.US));
        String output = myFormatter.format(total);
//        tvPriceTotal.setText(Const.PRICE_PEN + String.format(Locale.US,"%.2f", total));
        tvPriceTotal.setText(Const.PRICE_PEN + output);
        tvPercent.setText("Dscto. 15%");
        tvDiscount.setText(Const.PRICE_PEN + Strings.format(total*0.85,Strings.FORMAT_MILES));
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    backToFrontPage();
                    return true;
                }
                return false;
            }
        });
    }

    private void backToFrontPage() {
        Fragment gotToFront =MainFragment.newInstance();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, gotToFront);
        fragmentTransaction.commit();
    }
}
