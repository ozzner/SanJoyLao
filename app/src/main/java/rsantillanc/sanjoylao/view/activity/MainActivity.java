package rsantillanc.sanjoylao.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.view.fragment.DrawerFragment;
import rsantillanc.sanjoylao.view.fragment.PrimerFragment;
import rsantillanc.sanjoylao.view.fragment.SegundoFragment;


public class MainActivity extends ActionBarActivity implements DrawerFragment.FragmentDrawerListener{
    private Toolbar toBa;
    private DrawerFragment fragDra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpActionBar();
        fragDra = (DrawerFragment)getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        fragDra.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toBa);
        fragDra.setDrawerListener(this);
        displayView(0);
    }



    private void setUpActionBar() {
        toBa = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toBa);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }


    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new PrimerFragment();
                title = getString(R.string.item_title_banquets);
                break;
            case 1:
                fragment = new SegundoFragment();
                title = getString(R.string.item_title_meat_and_chicken);
                break;
          default:
              fragment = new SegundoFragment();
              title = getString(R.string.item_title_soup);
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the custom_toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}
