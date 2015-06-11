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
import rsantillanc.sanjoylao.view.fragment.DescriptionFragment;
import rsantillanc.sanjoylao.view.fragment.DrawerFragment;
import rsantillanc.sanjoylao.view.fragment.BanquetsFragment;
import rsantillanc.sanjoylao.view.fragment.InputsFragment;
import rsantillanc.sanjoylao.view.fragment.MainFragment;


public class MainActivity extends ActionBarActivity implements DrawerFragment.FragmentDrawerListener {
    private Toolbar toBa;
    private DrawerFragment fragDra;

    /*Const*/
    private static final int INPUT = 0;
    private static final int RICE = 1;
    private static final int SOUP = 2;
    private static final int CHEF = 3;
    private static final int CHICKEN_MEAT = 4;
    private static final int FISH = 5;
    private static final int VEGETARIAN = 6;
    private static final int BANQUETS = 7;
    private static final int DRINKS = 8;
    private static final int CENTRAL = 9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpActionBar();
        fragDra = (DrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        fragDra.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toBa);
        fragDra.setDrawerListener(this);
        displayView(10);
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

//

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {


            case INPUT:
                fragment = DescriptionFragment.newInstance(null, null);
                break;
            case RICE:
                fragment = DescriptionFragment.newInstance(null, null);
                break;
            case SOUP:
                fragment = DescriptionFragment.newInstance(null, null);
                break;
            case CHEF:
                fragment = DescriptionFragment.newInstance(null, null);
                break;
            case CHICKEN_MEAT:
                title = getString(R.string.item_title_meat_and_chicken);
                break;
            case FISH:
                fragment = DescriptionFragment.newInstance(null, null);
                break;
            case VEGETARIAN:
                fragment = DescriptionFragment.newInstance(null, null);
                break;
            case BANQUETS:
                fragment = new BanquetsFragment();
                title = getString(R.string.item_title_banquets);
                break;
            case DRINKS:
                fragment = DescriptionFragment.newInstance(null, null);
                break;
            case CENTRAL:
                fragment = DescriptionFragment.newInstance(null, null);
                break;

            default:
                fragment = MainFragment.newInstance();
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
