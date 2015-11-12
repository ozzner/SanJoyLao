package rsantillanc.sanjoylao.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.SJLApplication;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.storage.dao.UserDao;
import rsantillanc.sanjoylao.ui.fragment.BanquetsFragment;
import rsantillanc.sanjoylao.ui.fragment.CategoryFragment;
import rsantillanc.sanjoylao.ui.fragment.ChefFragment;
import rsantillanc.sanjoylao.ui.fragment.FrontFragment;
import rsantillanc.sanjoylao.ui.fragment.HomeFragment;
import rsantillanc.sanjoylao.ui.fragment.SoupFragment;
import rsantillanc.sanjoylao.ui.mvp.Main.IMainView;
import rsantillanc.sanjoylao.ui.mvp.Main.MainPresenterImpl;
import rsantillanc.sanjoylao.util.Android;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.MenuColorizer;


public class MainActivity extends BaseActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnLoadSuccess,
        IMainView {


    //Debug var
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final long DELAY_SHORT = 0b11111010;
    private static final long DELAY_LONG = 0b111110100;

    //views
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavView;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView username;
    private TextView email;
    private CircleImageView profileImage;


    //Constants
    private static final int INPUT = R.id.nav_main_input;
    private static final int SOUP = R.id.nav_main_soup;
    private static final int PLATES = R.id.nav_main_plates;
    private static final int CHEF = R.id.nav_main_chef;
    //    private static final int CHICKEN_MEAT = R.id.nav_main_chicken_and_meat;
//    private static final int FISH = R.id.nav_main_fish;
//    private static final int VEGETARIAN = R.id.nav_main_vegetarian_food;
    private static final int BANQUETS = R.id.nav_main_banquets;
    private static final int DRINKS = R.id.nav_main_drinks;
    private static final int CENTRAL = R.id.nav_main_call_center;
    private static final int HOME = 10;
    private static final String TAG_COLLAPSED = "collapsed";
    private static final String TAG_EXPANDED = "expanded";


    //Global vars
    private int typeOfDevice = -1;
    private Context mContext = null;
    private boolean backPressedToExitOnce = false;
    private Bundle mBundle = null;
    private static AppBarLayout mAppbarLayout;
    private MainPresenterImpl mPresenter;
    private SJLApplication app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Manager context & data
        initContextAndData();

        //Init Views
        initUIComponents();

        //Configurations
        setUpsUIComponents();

        //Show fragment
        displayFragment(new HomeFragment(this, true), HOME, false);

        //Synchronizing
        sync();

        Log.e(Const.DEBUG,"onCreate main activity");
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState = mBundle;
//        super.onSaveInstanceState(outState);
//    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        mBundle = savedInstanceState;
//    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e(Const.DEBUG, "onStart main activity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(Const.DEBUG, "onRestart main activity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(Const.DEBUG, "onStop main activity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(Const.DEBUG, "onPause main activity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(Const.DEBUG, "onDestroy main activity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(Const.DEBUG, "onResume main activity");
    }

    private void initContextAndData() {
        app = ((SJLApplication) getApplication());
        mContext = getApplicationContext();
        mPresenter = new MainPresenterImpl(MainActivity.this, this);
    }


    private void setUpsUIComponents() {
        setUpToolbar();
        setUpNavView();
        setUpDrawerToggle();
        setUpOrientation();
        setUpProfile();
        mAppbarLayout.setExpanded(false);
    }

    private void setUpAppBarLayout() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                setCollapseAppBarLayout(true);
//            }
//        }, DELAY_LONG);
    }

    private void setUpProfile() {

        if (getIntent().getExtras() == null)
            return;

        final UserModel serializable = (UserModel) getIntent().getExtras().getSerializable(Const.EXTRA_USER);
        app.setUserLogued(serializable);

        username.setText(serializable.getFullName());
        email.setText(serializable.getEmail());

        //Long process
        mPresenter.loadProfileImage(serializable.getUrlProfileImage(), profileImage);
        mPresenter.loadOrders(serializable.getObjectId());

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

    private void setUpToolbar() {

        LayoutInflater inflate = LayoutInflater.from(this);
        View custom = inflate.inflate(R.layout.custom_tab, null);
        toolbar.addView(custom);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Const.EMPTY);
        getSupportActionBar().setSubtitle(Const.EMPTY);

    }

    private void initUIComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        username = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.email);
        profileImage = (CircleImageView) findViewById(R.id.circle_image);
        mAppbarLayout = (AppBarLayout) findViewById(R.id.appbarLayout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //All gray color
        int gray = getResources().getColor(R.color.gray);
        MenuColorizer.colorMenu(this, menu, gray);

        //Orders white color
        int white = getResources().getColor(R.color.white);
        MenuItem item = menu.findItem(R.id.action_orders);
        MenuColorizer.colorMenuItem(item, white);

        //Show all icons overflow
        MenuColorizer.showIcons(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.END);
        } else if (id == R.id.action_orders) {
            goToOrderActivity();
        } else if (id == R.id.action_profile) {
            goToProfileActivity(mBundle);
        } else if (id == R.id.action_about) {
            showToast("San Joy Lao App | V." + Android.getAppVersion(this));
        } else if (id == R.id.action_logout) {
            goToLoginActivity();
        } else {
            showToast("View profile");
        }

        return super.onOptionsItemSelected(item);
    }


    //-----------------------[GoTo]

    private void goToProfileActivity(Bundle bundle) {
        Intent profile = new Intent(mContext, ProfileActivity.class);
        profile.putExtras(bundle);
        startActivity(profile);
    }

    private void goToLoginActivity() {
        Intent login = new Intent(mContext, LoginActivity.class);
        startActivity(login);
        new UserDao(this).logout();

        //End main
        finish();
    }

    private void goToOrderActivity() {
        Intent order = new Intent(mContext, OrderActivity.class);
        order.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        order.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(order);
    }

    //-----------------------[Sub routines]

    private void sync() {
        mPresenter.loadCategories();
        mPresenter.loadSizes();
        mPresenter.loadPlatesSize();
        mPresenter.loadPlate();
        mPresenter.loadOrderType();
        mPresenter.loadStatus();
        mPresenter.savePreferences();
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

    private void setCollapseAppBarLayout(boolean on) {
        AppBarLayout.Behavior behavior;
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppbarLayout.getLayoutParams();
        behavior = (AppBarLayout.Behavior) params.getBehavior();


        if (on) { //Collapse
            if (behavior != null) {
                behavior.onNestedFling(null, mAppbarLayout, null, 0, 10000, true);
            }
        } else {//Expanded
            if (behavior != null) {
                behavior.setTopAndBottomOffset(0);
                behavior.onNestedPreScroll(null, mAppbarLayout, null, 0, 1, new int[2]);
            }
        }
    }

    public static void collapseAppBarLayout() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppbarLayout.getLayoutParams();
//                AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
//                behavior.onNestedFling(null, mAppbarLayout, null, 0, 10000, true);
//            }
//        }, DELAY_SHORT);

    }

    private void displayFragment(Fragment ui, int position, boolean isBackStack) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final Fragment expanded = fragmentManager.findFragmentByTag(TAG_EXPANDED);
        final Fragment collapsed = fragmentManager.findFragmentByTag(TAG_COLLAPSED);

        if (isBackStack) {

            if (expanded != null)
                fragmentTransaction.remove(expanded);
            fragmentTransaction.replace(R.id.fragments_content2, ui, TAG_COLLAPSED);

        } else {

            if (collapsed != null)
                fragmentTransaction.remove(collapsed);
            fragmentTransaction.replace(R.id.fragments_content, ui, TAG_EXPANDED);
        }

        fragmentTransaction.commit();

        if (typeOfDevice > Const.PHONE_SCREEN && position == HOME) {
            Fragment details = FrontFragment.getInstance();
            FragmentTransaction secondTransaction = fragmentManager.beginTransaction();
            secondTransaction.replace(R.id.container_details_main, details);
            secondTransaction.commit();
        }
    }


    /*------------------------ LISTENERS ---------------------*/
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        boolean isTransaction;
        Fragment ui;
        CharSequence title = null;
        boolean back = false;


        switch (menuItem.getItemId()) {

            case INPUT:
                ui = HomeFragment.newInstance();
                title = getString(R.string.item_title_inputs);
                isTransaction = true;
                setCollapseAppBarLayout(true);

                break;
            case SOUP:
                ui = SoupFragment.newInstance();
                isTransaction = true;
                title = getString(R.string.item_title_soup);
                setCollapseAppBarLayout(true);
                break;
            case PLATES:
                ui = CategoryFragment.newInstance();
                isTransaction = true;
                back = true;
                title = getString(R.string.item_title_plates);
                setCollapseAppBarLayout(false);
                break;
            case CHEF:
                ui = ChefFragment.newInstance();
                isTransaction = true;
                title = getString(R.string.item_title_chef);
                setCollapseAppBarLayout(true);
                break;
//            case CHICKEN_MEAT:
//                ui = HomeFragment.newInstance();
//                title = getString(R.string.item_title_meat_and_chicken);
//                break;
//            case FISH:
//                ui = HomeFragment.newInstance();
//                title = getString(R.string.item_title_inputs);
//                break;
//            case VEGETARIAN:
//                ui = HomeFragment.newInstance();
//                break;
            case BANQUETS:
                ui = BanquetsFragment.newInstance();
                title = getString(R.string.item_title_banquets);
                isTransaction = true;
                setCollapseAppBarLayout(true);

                break;
            case DRINKS:
                ui = HomeFragment.newInstance();
                isTransaction = true;
                setCollapseAppBarLayout(true);

                break;
            case CENTRAL:
                ui = HomeFragment.newInstance();
                isTransaction = true;
                setCollapseAppBarLayout(true);

                break;
            default:
                ui = HomeFragment.newInstance();
                typeOfDevice = Android.getTypeDevice(this);
                isTransaction = true;
                setCollapseAppBarLayout(true);
                break;
        }

        if (isTransaction) {

            //Show indicate fragment
            displayFragment(ui, menuItem.getItemId(), back);

            //Display a title
            if (title != null)
                getSupportActionBar().setTitle("");

            //Active row
            menuItem.setChecked(true);

            //Close menu list
            mDrawerLayout.closeDrawers();
        }

        return false;
    }


    //----------------------[IMainView]
    @Override
    public void viewloaded() {
        setUpAppBarLayout();
    }


    @Override
    public void goToLogin() {

    }

    @Override
    public void goToProfile() {

    }

    @Override
    public void goToOrders() {

    }

    @Override
    public void showLoader() {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void uptateTitle(CharSequence title) {

    }

    @Override
    public void closeMenu() {

    }

    @Override
    public void openMenu() {

    }
}
