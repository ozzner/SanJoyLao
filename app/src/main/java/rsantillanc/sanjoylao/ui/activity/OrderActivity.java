package rsantillanc.sanjoylao.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.ui.fragment.OrdersFragment;

public class OrderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            Fragment fragment = OrdersFragment.newInstance();
            FragmentManager man = getSupportFragmentManager();
            FragmentTransaction tran = man.beginTransaction();
            tran.replace(R.id.orders_fragment_content, fragment).commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_order_payment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
