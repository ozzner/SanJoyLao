package rsantillanc.sanjoylao.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.lang.reflect.Method;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.util.Android;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.view.fragment.BanquetsFragment;
import rsantillanc.sanjoylao.view.fragment.DescriptionFragment;
import rsantillanc.sanjoylao.view.fragment.DrawerFragment;
import rsantillanc.sanjoylao.view.fragment.FrontFragment;
import rsantillanc.sanjoylao.view.fragment.MainFragment;
import rsantillanc.sanjoylao.view.fragment.OrdersFragment;
import rsantillanc.sanjoylao.view.fragment.SoupFragment;


public class MainActivity extends ActionBarActivity implements DrawerFragment.FragmentDrawerListener {
    //Debug var
    private static final String TAG = MainActivity.class.getSimpleName();

    //views
    private Toolbar toBa;
    private DrawerFragment fragDra;

    /*Const*/
    private static final int INPUT = 0;
    private static final int SOUP = 1;
    private static final int RICE = 2;
    private static final int CHEF = 3;
    private static final int CHICKEN_MEAT = 4;
    private static final int FISH = 5;
    private static final int VEGETARIAN = 6;
    private static final int BANQUETS = 7;
    private static final int DRINKS = 8;
    private static final int CENTRAL = 9;
    private static final int MAIN = 10;

    //Globals vars
    private int typeOfDevice = -1;
    private Context mContext = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        initViews();

        setUpActionBar();
        setUpDrawer();
        setUpOrientation();

        displayView(MAIN);

    }

    private void setUpOrientation() {
        Android.setScreenOrientation(this);
    }

    private void setUpDrawer() {
        fragDra.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toBa);
        fragDra.setDrawerListener(this);
    }

    private void initViews() {
        toBa = (Toolbar) findViewById(R.id.toolbar);
        fragDra = (DrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
    }


    private void setUpActionBar() {
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
        if (id == R.id.action_orders) {

            Fragment fragment = OrdersFragment.newInstance();
            FragmentManager man = getSupportFragmentManager();
            FragmentTransaction tran = man.beginTransaction();
            tran.replace(R.id.container_body, fragment).commit();

            return true;
        } else if (id == R.id.action_about) {
            Toast.makeText(getApplication(), "San Joy Lao App | V.0.9.4", Toast.LENGTH_LONG).show();
        } else {
            Intent login = new Intent(mContext, LoginActivity.class);
            startActivity(login);
            finish();
            Toast.makeText(getApplication(), "Closing...", Toast.LENGTH_LONG).show();
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
            case SOUP:
                fragment = SoupFragment.newInstance();
                break;
            case RICE:
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
                typeOfDevice = Android.getTypeDevice(this);

                if (typeOfDevice > Const.PHONE_SCREEN) {
                    Fragment details = FrontFragment.getInstance();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_details_body, details);
                    fragmentTransaction.commit();
                }
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

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "onMenuOpened", e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
}
