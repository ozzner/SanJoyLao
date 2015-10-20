package rsantillanc.sanjoylao.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.storage.sp.SJLPreferences;
import rsantillanc.sanjoylao.ui.fragment.BanquetsFragment;
import rsantillanc.sanjoylao.ui.fragment.ChefFragment;
import rsantillanc.sanjoylao.ui.fragment.FrontFragment;
import rsantillanc.sanjoylao.ui.fragment.MainFragment;
import rsantillanc.sanjoylao.ui.fragment.OrdersFragment;
import rsantillanc.sanjoylao.ui.fragment.RiceFragment;
import rsantillanc.sanjoylao.ui.fragment.SoupFragment;
import rsantillanc.sanjoylao.util.Android;
import rsantillanc.sanjoylao.util.Const;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Debug var
    private static final String TAG = MainActivity.class.getSimpleName();

    //views
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavView;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView username;
    private TextView email;


    //Constants
    private static final int INPUT = R.id.nav_main_input;
    private static final int SOUP = R.id.nav_main_soup;
    private static final int RICE = R.id.nav_main_rice;
    private static final int CHEF = R.id.nav_main_chef;
    private static final int CHICKEN_MEAT = R.id.nav_main_chicken_and_meat;
    private static final int FISH = R.id.nav_main_fish;
    private static final int VEGETARIAN = R.id.nav_main_vegetarian_food;
    private static final int BANQUETS = R.id.nav_main_banquets;
    private static final int DRINKS = R.id.nav_main_drinks;
    private static final int CENTRAL = R.id.nav_main_call_center;
    private static final int MAIN = 10;

    private static final int DEFAULT_FONT_SIZE = 22;

    //Global vars
    private int typeOfDevice = -1;
    private Context mContext = null;
    private boolean backPressedToExitOnce = false;
    private Bundle mBundle = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //delete this and paste in splash activity
        SJLPreferences preferences = new SJLPreferences(getApplicationContext());
        preferences.run();

        //Get context
        mContext = getApplicationContext();
        mBundle = getIntent().getExtras();

        //Init Views
        initUIComponents();

        //Configurations
        setUpsUIComponents();

        //Show fragment
        displayFragment(MainFragment.newInstance(), MAIN);

    }

    private void setUpsUIComponents() {
        setUpActionBar();
        setUpNavView();
        setUpDrawerToggle();
        setUpOrientation();
        setUpProfile();
    }

    private void setUpProfile() {
        if (mBundle!= null){
            UserModel user = ((UserModel) mBundle.getSerializable(Const.EXTRA_USER));
            username.setText(user.getFullName());
            email.setText(user.getEmail());
        }
    }

    private void setUpNavView() {
        mNavView.setNavigationItemSelectedListener(this);
    }


    private void setUpOrientation() {
        Android.setScreenOrientation(this);
    }


    private void setUpDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void initUIComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        username = (TextView)findViewById(R.id.username);
        email = (TextView)findViewById(R.id.email);
    }


    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu != null) {

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_orders) {

            Fragment fragment = OrdersFragment.newInstance();
            FragmentManager man = getSupportFragmentManager();
            FragmentTransaction tran = man.beginTransaction();
            tran.replace(R.id.fragments_content, fragment).commit();

            getSupportActionBar().setTitle(getString(R.string.title_orders));
            return true;

        } else if (id == R.id.action_about) {
            Toast.makeText(getApplication(), "San Joy Lao App | V."+Android.getAppVersion(this), Toast.LENGTH_LONG).show();
        } else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.END);
        } else {
            Intent login = new Intent(mContext, LoginActivity.class);
            startActivity(login);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        if (backPressedToExitOnce) {
            super.onBackPressed();

        } else {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                mDrawerLayout.closeDrawers();
            } else {

                this.backPressedToExitOnce = true;
                Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        backPressedToExitOnce = false;
                    }
                }, 3000);
            }
        }
    }


    /*------------------------ LISTENERS ---------------------*/
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        boolean isTransaction = false;
        Fragment ui;
        CharSequence title = null;

        switch (menuItem.getItemId()) {

            case INPUT:
                ui = MainFragment.newInstance();
                title = getString(R.string.item_title_inputs);
                break;
            case SOUP:
                ui = SoupFragment.newInstance();
                isTransaction = true;
                title = getString(R.string.item_title_soup);
                break;
            case RICE:
                ui = RiceFragment.newInstance();
                isTransaction = true;
                title = getString(R.string.item_title_rices);
                break;
            case CHEF:
                ui = ChefFragment.newInstance();
                isTransaction = true;
                title = getString(R.string.item_title_chef);
                break;
            case CHICKEN_MEAT:
                ui = MainFragment.newInstance();
                title = getString(R.string.item_title_meat_and_chicken);
                break;
            case FISH:
                ui = MainFragment.newInstance();
                title = getString(R.string.item_title_inputs);
                break;
            case VEGETARIAN:
                ui = MainFragment.newInstance();
                break;
            case BANQUETS:
                ui = BanquetsFragment.newInstance();
                title = getString(R.string.item_title_banquets);
                isTransaction = true;
                break;
            case DRINKS:
                ui = MainFragment.newInstance();
                break;
            case CENTRAL:
                ui = MainFragment.newInstance();
                break;
            default:
                ui = MainFragment.newInstance();
                typeOfDevice = Android.getTypeDevice(this);
                break;
        }

        if (isTransaction) {

            //Show indicate fragment
            displayFragment(ui, menuItem.getItemId());

            //Display a title
            if (title != null)
                getSupportActionBar().setTitle(title);

            //Active row
            menuItem.setChecked(true);

            //Close menu list
            mDrawerLayout.closeDrawers();
        }


        return false;
    }

    private void displayFragment(Fragment ui, int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments_content, ui);
        fragmentTransaction.commit();

        if (typeOfDevice > Const.PHONE_SCREEN && position == MAIN) {
            Fragment details = FrontFragment.getInstance();
            FragmentTransaction secondTransaction = fragmentManager.beginTransaction();
            secondTransaction.replace(R.id.container_details_main, details);
            secondTransaction.commit();
        }
    }
}
