package rsantillanc.sanjoylao.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.FeastModel;
import rsantillanc.sanjoylao.ui.custom.adapter.GridViewAdapter;
import rsantillanc.sanjoylao.ui.custom.dialog.SJLAlertDialog;
import rsantillanc.sanjoylao.model.OptionsModel;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.storage.sp.SJLPreferences;
import rsantillanc.sanjoylao.ui.popup.DetailsOptionsPopup;

public class OptionsGridActivity extends ActionBarActivity implements GridViewAdapter.OnPlateClickListener,SJLAlertDialog.OnBookingListener  {

    private Toolbar mToolbar;
    private Button btOrder;
    private GridView mGridView;
    private GridViewAdapter mGridAdapter;
    private ArrayList<OptionsModel> options;
    private OptionsModel opModel;
    private GridViewAdapter.OnPlateClickListener mListener;
    private int columnWidth;
    private Context _context;
    private SJLPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_grid);
        mPreferences = new SJLPreferences(getApplicationContext());
        SJLAlertDialog mDialog = new SJLAlertDialog();
        mDialog.set(this);

        init(getApplicationContext());
        FeastModel model = (FeastModel) getIntent().getSerializableExtra(Const.TAG_BANQUET);
        setUpActionBar(model);
    }

    private void init(final Context ctx) {
        _context = ctx;
        btOrder = (Button) findViewById(R.id.bt_options_grid_order);
        //Borrar esto luego.
        btOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int val = mPreferences.getSaveStoredByKey(Const.KEY_ORDER_TYPE);
                String title = getString(R.string.title_booking_options);
                if (val == SJLPreferences.INT_DEFAULT_VALUE)
                    SJLAlertDialog.showTypeBookAlert(OptionsGridActivity.this, title);
                else {

                    Intent popup = new Intent(getApplicationContext(), DetailsOptionsPopup.class);
                    startActivity(popup);
                    Toast.makeText(ctx, "¡Agregado!", Toast.LENGTH_LONG).show();
                }

            }
        });
        mGridView = (GridView) findViewById(R.id.gv_options);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_options);

        /*Setup*/
        opModel = new OptionsModel();
        options = opModel.testData();
        InitilizeGridLayout();
        mGridAdapter = new GridViewAdapter(ctx, options, columnWidth);
        mGridAdapter.setOnPlateClickListener(this);
        mGridView.setAdapter(mGridAdapter);

    }

    private void setUpActionBar(FeastModel model) {
        mToolbar.setTitle("Banqute de la Amistad (S/. 152.00)");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
        }

        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
          //  return true;
        //} else if (id == R.id.home) {
        //    finish();
       // }

        return super.onOptionsItemSelected(item);
    }


    //********************************************

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @SuppressWarnings("deprecation")
    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) getApplicationContext()
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


    /**
     * Method to calculate the grid dimensions Calculates number columns and
     * columns width in grid
     */
    private void InitilizeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                Const.GRID_PADDING, r.getDisplayMetrics());

        // Column width
        columnWidth = (int) ((getScreenWidth() - ((4) * padding)) / 2);

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


    @Override
    public void onClicked(View v) {

        Intent popup = new Intent(getApplicationContext(), DetailsOptionsPopup.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(Const.TAG_DETAILS_OPTIONS,options.get(0));
//        popup.putExtras(bundle);
        startActivity(popup);

    }

    @Override
    public void onClick(DialogInterface dialog, int index) {
        mPreferences = new SJLPreferences(_context);
        mPreferences.saveOrderType(index);
        Toast.makeText(_context,"¡Agregado option: !" + index,Toast.LENGTH_LONG).show();
        dialog.cancel();
    }
}
