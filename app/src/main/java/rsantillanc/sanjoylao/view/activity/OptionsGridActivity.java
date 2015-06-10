package rsantillanc.sanjoylao.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.custom.adapter.GridViewAdapter;
import rsantillanc.sanjoylao.model.BanquetModel;
import rsantillanc.sanjoylao.model.OptionsModel;
import rsantillanc.sanjoylao.util.Const;

public class OptionsGridActivity extends ActionBarActivity {

    private GridViewAdapter mGridAdapter;
    private ArrayList<OptionsModel> options;
    private OptionsModel opModel;
    private Toolbar mToolbar;
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_grid);

        init(getApplicationContext());
        BanquetModel model = (BanquetModel)getIntent().getSerializableExtra(Const.TAG_BANQUET);
        setUpActionBar(model);
    }

    private void init(Context ctx) {
        mGridView = (GridView)findViewById(R.id.gv_options);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_options);

        /*Setup*/
        opModel = new OptionsModel();
        options = opModel.testData();
        mGridAdapter = new GridViewAdapter(ctx,options);
        mGridView.setAdapter(mGridAdapter);

    }

    private void setUpActionBar(BanquetModel model) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle(Const.PRICE_PEN + String.valueOf(model.getPrice()));
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
