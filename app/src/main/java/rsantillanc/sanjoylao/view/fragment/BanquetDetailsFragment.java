package rsantillanc.sanjoylao.view.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.custom.adapter.GridViewAdapter;
import rsantillanc.sanjoylao.custom.adapter.RecyclerViewOptionsAdapter;
import rsantillanc.sanjoylao.custom.dialog.SJLAlertDialog;
import rsantillanc.sanjoylao.model.BanquetModel;
import rsantillanc.sanjoylao.model.OptionsModel;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.SJLPreferences;
import rsantillanc.sanjoylao.view.popup.DetailsOptionsPopup;

/**
 * A simple {@link Fragment} subclass.
 */
public class BanquetDetailsFragment extends Fragment implements GridViewAdapter.OnPlateClickListener
        ,SJLAlertDialog.OnBookingListener {

    private static BanquetDetailsFragment instance;

    private Toolbar mToolbar;
    private Button btOrder;
    private GridView mGridView;
    private GridViewAdapter mGridAdapter;
    private ArrayList<OptionsModel> options;
    private GridViewAdapter.OnPlateClickListener mListener;
    private OptionsModel opModel;
    private BanquetModel oBanquet;
    private int columnWidth;
    private RecyclerView mRecyclerView;
    private RecyclerViewOptionsAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private SJLAlertDialog customDialog;
    private SJLPreferences mPreferences;




    public static BanquetDetailsFragment getInstance() {
        instance =  new BanquetDetailsFragment();
        return instance;
    }

    public BanquetDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oBanquet = (BanquetModel)getArguments().getSerializable(Const.TAG_BANQUET);
        mPreferences = new SJLPreferences(getActivity());
        customDialog = new SJLAlertDialog();
        customDialog.set(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  vi;
        if (oBanquet.isFlagOptions()){
            vi = inflater.inflate(R.layout.activity_options, container, false);
            initListComponents(vi);
        }else{
            vi = inflater.inflate(R.layout.activity_options_grid, container, false);
            initGridComponents(vi);
        }

        return vi;
    }


    private void initListComponents(final View vi) {
        final Context ctx = vi.getContext();

         /*Init views*/
        mRecyclerView = (RecyclerView)vi.findViewById(R.id.rv_options);

        /*Setup*/
        OptionsModel model = new OptionsModel();
        options = model.testData();
        mAdapter = new RecyclerViewOptionsAdapter(options,ctx);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(false);
        mLinearLayoutManager = new LinearLayoutManager(ctx);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mAdapter.setOnItemClickListener(new RecyclerViewOptionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {

                Intent popup = new Intent(ctx, DetailsOptionsPopup.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Const.TAG_DETAILS_OPTIONS,options.get(index));
                popup.putExtras(bundle);
                startActivity(popup);
            }
        });
    }

    private void initGridComponents(View vi) {
        btOrder = (Button)vi.findViewById(R.id.bt_options_grid_order);
        //Borrar esto luego.
        btOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = mPreferences.getSaveStoredByKey(SJLPreferences.KEY_ORDER_TYPE);
                String title = getString(R.string.title_booking_options);
                if (val == SJLPreferences.INT_DEFAULT_VALUE)
                SJLAlertDialog.showTypeBookAlert(getActivity(),title);
                else
                    Toast.makeText(getActivity(),"¡Agregado!",Toast.LENGTH_LONG).show();
            }
        });
        mGridView = (GridView)vi.findViewById(R.id.gv_options);
        mToolbar = (Toolbar)vi.findViewById(R.id.toolbar_options);
//        BanquetModel model = (BanquetModel)getArguments().getSerializable(Const.TAG_BANQUET);

        /*Setup*/
        opModel = new OptionsModel();
        options = opModel.testData();
        InitilizeGridLayout();
        mGridAdapter = new GridViewAdapter(getActivity(),options,columnWidth);
        mGridAdapter.setOnPlateClickListener(this);
        mGridView.setAdapter(mGridAdapter);


    }

    private void InitilizeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                Const.GRID_PADDING, r.getDisplayMetrics());

        // Column width
        columnWidth = (int) ((getScreenWidth()/2 - ((4) * padding)) /2);

        // Setting number of grid columns
        mGridView.setNumColumns(2);
        mGridView.setColumnWidth(columnWidth);
        mGridView.setStretchMode(GridView.NO_STRETCH);
        mGridView.setPadding((int) padding, (int) padding, (int) padding,
                (int) padding);

        // Setting horizontal and vertical padding
        mGridView.setHorizontalSpacing((int) padding);
        mGridView.setVerticalSpacing((int) padding);
    }

    private float getScreenWidth() {

        int columnWidth;
        WindowManager wm = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) {
            // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }


    @Override
    public void onClicked(View v) {


        Intent popup = new Intent(getActivity(), DetailsOptionsPopup.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(Const.TAG_DETAILS_OPTIONS,options.get(0));
//        popup.putExtras(bundle);
        startActivity(popup);
    }

    @Override
    public void onClick(DialogInterface dialog, int index) {
        mPreferences = new SJLPreferences(getActivity());
        mPreferences.saveOrderType(index);
        Toast.makeText(getActivity(),"¡Agregado option: !" + index,Toast.LENGTH_LONG).show();
        dialog.cancel();
    }
}
