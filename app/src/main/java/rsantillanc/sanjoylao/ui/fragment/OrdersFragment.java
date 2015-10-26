package rsantillanc.sanjoylao.ui.fragment;


import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.BanquetModel;
import rsantillanc.sanjoylao.ui.custom.adapter.ListViewAdapter;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.SJLStrings;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {

    private static OrdersFragment instance;
    private ListView mListView;
    private TextView tvPriceTotal,tvNumTable;
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
        tvPercent = (TextView)v.findViewById(R.id.tv_order_percent);
        tvDiscount = (TextView)v.findViewById(R.id.tv_order_price_discount);


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
        tvPriceTotal.setPaintFlags(tvPriceTotal.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        tvPercent.setText("Dscto. 15%");
        tvDiscount.setText(Const.PRICE_PEN + SJLStrings.format(total * 0.85, SJLStrings.FORMAT_MILES));
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
                    Fragment gotToFront = MainFragment.newInstance();
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
