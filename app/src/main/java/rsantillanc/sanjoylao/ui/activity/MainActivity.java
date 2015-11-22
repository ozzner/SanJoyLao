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

import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import de.hdodenhof.circleimageview.CircleImageView;
import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.SJLApplication;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.storage.dao.UserDao;
import rsantillanc.sanjoylao.ui.mvp.Main.IMainView;
import rsantillanc.sanjoylao.ui.mvp.Main.MainPresenterImpl;
import rsantillanc.sanjoylao.util.Android;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.MenuColorizer;


public class MainActivity extends BaseActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
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



    public static final int HOME = 10;
    public static final String TAG_COLLAPSED = "collapsed";
    public static final String TAG_EXPANDED = "expanded";


    //Global vars
    private int typeOfDevice = -1;
    private Context mContext = null;
    private boolean backPressedToExitOnce = false;
    private Bundle mBundle = null;
    private static AppBarLayout mAppbarLayout;
    private MainPresenterImpl mPresenter;
    private SJLApplication app;
    private TextView tvTitle;
    private TextView tvSubtitle;


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

        //Display home
        mPresenter.displayFragment(HOME);

        //Synchronizing
        sync();

        Log.e(Const.DEBUG, "onCreate main activity");
    }




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
        setupTitle(getString(R.string.app_name), ((UserModel) getIntent().getExtras().getSerializable(Const.EXTRA_USER)));

    }

    private void setupTitle(String title, UserModel serializable) {
        tvTitle.setText(title);
        tvSubtitle.setText(serializable.getFullName());
    }

    public void updateTitle(CharSequence title) {
        tvTitle.setText(title);
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

        //Get view
        LayoutInflater inflate = LayoutInflater.from(this);
        View custom = inflate.inflate(R.layout.custom_tab, null);

        //Get child
        tvTitle = (TextView) custom.findViewById(R.id.tv_custom_toolbar_title);
        tvSubtitle = (TextView) custom.findViewById(R.id.tv_custom_toolbar_subtitle);
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

        if (id == android.R.id.home) {
            openMenu();
        } else if (id == R.id.action_orders) {
            goToOrderActivity();
        } else if (id == R.id.action_profile) {
            goToProfileActivity(getIntent().getExtras());
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
        profile.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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


    public void showFragment(Fragment ui, boolean isSecondary) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final Fragment expanded = fragmentManager.findFragmentByTag(TAG_EXPANDED);
        final Fragment collapsed = fragmentManager.findFragmentByTag(TAG_COLLAPSED);

        if (isSecondary) {

            if (expanded != null)
                fragmentTransaction.remove(expanded);
            fragmentTransaction.replace(R.id.fragments_content2, ui, TAG_COLLAPSED);

        } else {

            if (collapsed != null)
                fragmentTransaction.remove(collapsed);
            fragmentTransaction.replace(R.id.fragments_content, ui, TAG_EXPANDED);
        }

        fragmentTransaction.commit();

//        if (typeOfDevice > Const.PHONE_SCREEN && position == HOME) {
//            Fragment details = FrontFragment.getInstance();
//            FragmentTransaction secondTransaction = fragmentManager.beginTransaction();
//            secondTransaction.replace(R.id.container_details_main, details);
//            secondTransaction.commit();
//        }
    }


    /*------------------------ LISTENERS ---------------------*/
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        mPresenter.displayFragment(menuItem.getItemId());
        return false;
    }




    //----------------------[IMainView]



    @Override
    public void closeMenu() {
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void openMenu() {
        mDrawerLayout.openDrawer(GravityCompat.END);
    }

    @Override
    public void displayFragment(Fragment ui, boolean isSecondary) {
        showFragment(ui,isSecondary);
    }

    @Override
    public void markItemSelected(int item) {
        mNavView.setCheckedItem(item);
    }

    @Override
    public void collapse(boolean b) {
        setCollapseAppBarLayout(b);
    }


}
