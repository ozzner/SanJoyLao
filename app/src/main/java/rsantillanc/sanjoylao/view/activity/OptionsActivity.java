package rsantillanc.sanjoylao.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.custom.adapter.RecyclerViewOptionsAdapter;
import rsantillanc.sanjoylao.model.BanquetModel;
import rsantillanc.sanjoylao.model.OptionsModel;
import rsantillanc.sanjoylao.util.Const;

public class OptionsActivity extends ActionBarActivity {


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
        init(getApplicationContext());
        BanquetModel model = (BanquetModel)getIntent().getSerializableExtra(Const.TAG_BANQUET);
        setUpActionBar(model);
    }

    private void setUpActionBar(BanquetModel model) {
        mToolbar = (Toolbar)findViewById(R.id.toolbar_options);
        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle(model.getName());
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
                Toast.makeText(v.getContext(),"Title: " + options.get(index).getTitle(), Toast.LENGTH_LONG).show();
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
//                finish();

                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                                    // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
