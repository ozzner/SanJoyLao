package rsantillanc.sanjoylao.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.custom.adapter.RecyclerViewOptionsAdapter;
import rsantillanc.sanjoylao.model.BanquetModel;
import rsantillanc.sanjoylao.model.OptionsModel;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.view.popup.DetailsOptionsPopup;

public class OptionsListActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RecyclerViewOptionsAdapter mAdapter;
    private ArrayList<OptionsModel> options;
    private LinearLayoutManager mLinearLayoutManager;
    private OptionsModel opModel;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        mToolbar = (Toolbar)findViewById(R.id.toolbar_options);

        init(getApplicationContext());
        BanquetModel model = (BanquetModel)getIntent().getSerializableExtra(Const.TAG_BANQUET);
        setUpActionBar(model);
    }

    private void setUpActionBar(BanquetModel model) {
        mToolbar.setTitle(model.getName() + " (S/. 55.65)");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init(Context ctx) {
        /*Init views*/
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_options);

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
                Intent popup = new Intent(getApplicationContext(), DetailsOptionsPopup.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Const.TAG_DETAILS_OPTIONS,options.get(index));
                popup.putExtras(bundle);
                startActivity(popup);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
