package rsantillanc.sanjoylao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.ui.custom.adapter.ViewPagerAdapter;
import rsantillanc.sanjoylao.util.Android;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.SJLDates;
import rsantillanc.sanjoylao.util.SJLStrings;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    //Views
    private FloatingActionButton mFloatingActionButton;
    private Toolbar toolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    //Globals
    private int[] tabIcons = {
            R.drawable.ic_account,
            R.drawable.ic_favorite
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Init views
        initUIComponents();

        //Config
        setupUIComponents();

    }


    //---------------------- [INIT  COMPONENTS]
    private void initUIComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_profile_save);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tbl_profile);
    }

    private void setUpTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();
    }

    private void setUpViewPager() {
        ViewPagerAdapter pager = new ViewPagerAdapter(
                getSupportFragmentManager(),
                loadTitles(),
                (UserModel) getIntent().getExtras().getSerializable(Const.EXTRA_USER));
        mViewPager.setAdapter(pager);
    }

    private void setupTabIcons() {
        mTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private String[] loadTitles() {
        String[] titles = getResources().getStringArray(R.array.tabs_profile);
        return titles;
    }

    //---------------------- [SETUPS COMPONENTS]
    private void setupUIComponents() {
        setUpViewPager();
        setUpTabLayout();
        setUpToolbar((UserModel) getIntent().getExtras().getSerializable(Const.EXTRA_USER));
        setUpFloatingButton();
    }

    private void setUpFloatingButton() {
        mFloatingActionButton.setOnClickListener(this);
    }

    private void setUpToolbar(UserModel serializable) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(serializable.getFullName());
        getSupportActionBar().setSubtitle(getString(R.string.label_profile_created) +
                SJLDates.customDateConverter(serializable.getCreatedAt(), SJLStrings.PARSE_DATE_FORMAT, SJLDates.FORMAT_DATE_GENERAL));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order, menu);
//        int color = getResources().getColor(R.color.white);
//        MenuColorizer.colorMenu(this, menu, color);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_orders_history) {
            showToast("San Joy Lao | V." + Android.getAppVersion(this));
            return true;
        } else {
            goToMainActivity((UserModel) getIntent().getExtras().getSerializable(Const.EXTRA_USER));
            return true;
        }

    }

    private void goToMainActivity(UserModel currentUser) {
        Intent main = new Intent(getApplicationContext(),MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.EXTRA_USER,currentUser);
        main.putExtras(bundle);
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(main);
    }

    @Override
    public void onBackPressed() {
        goToMainActivity((UserModel) getIntent().getExtras().getSerializable(Const.EXTRA_USER));
    }

    //---------------------- [CALLBAKCS]
    @Override
    public void onClick(View v) {

    }
}
