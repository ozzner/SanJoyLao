package rsantillanc.sanjoylao.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {

    private static OrdersFragment instance;
    private ListView mListView;
    private EditText etInput;
    private TextView tvPriceTotal,tvNumTable;
    private double total = 0.0;
    private List<Object> orders;

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
        /*Get views*/
        mListView = (ListView) v.findViewById(R.id.lv_orders);
        tvNumTable = (TextView) v.findViewById(R.id.tv_number_table);
        tvPriceTotal = (TextView) v.findViewById(R.id.tv_order_price_total);

        /*Setup*/
        BanquetModel oModel = new BanquetModel();
        orders = oModel.testData();
        tvNumTable.setText("Mesa N° 19");
        mListView.setAdapter(new ListViewAdapter(getActivity(), orders, Const.ORDERS));

        for (Object order : orders) {
            total += ((BanquetModel) order).getPrice();
        }
        DecimalFormat myFormatter = new DecimalFormat("##,###.##", DecimalFormatSymbols.getInstance(Locale.US));
        String output = myFormatter.format(total);
//        tvPriceTotal.setText(Const.PRICE_PEN + String.format(Locale.US,"%.2f", total));
        tvPriceTotal.setText(Const.PRICE_PEN + output);

    }






}
