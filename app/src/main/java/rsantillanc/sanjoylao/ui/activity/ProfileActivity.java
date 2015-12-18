package rsantillanc.sanjoylao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockApplication;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.SJLApplication;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.ui.custom.adapter.ViewPagerAdapter;
import rsantillanc.sanjoylao.ui.fragment.AccountFragment;
import rsantillanc.sanjoylao.ui.fragment.FavoriteFragment;
import rsantillanc.sanjoylao.ui.mvp.Profile.IProfileView;
import rsantillanc.sanjoylao.ui.mvp.Profile.ProfilePresenterImpl;
import rsantillanc.sanjoylao.util.Android;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.SJLDates;
import rsantillanc.sanjoylao.util.SJLStrings;

public class ProfileActivity extends BaseActivity implements View.OnClickListener,IProfileView {

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

    private ProfilePresenterImpl presenter;
    private ViewPagerAdapter pagerAdapter;
    private List<Fragment> fragments;
    private SJLApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //First
        init();

        //Init views
        initUIComponents();

        //Config
        setupUIComponents();

    }

    private void init() {
        app = ((SJLApplication) getApplication());
        presenter = new ProfilePresenterImpl(this);
        fragments = new ArrayList<>();
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
        buildFragments();
        pagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                loadTitles(),getFragments());
        mViewPager.setAdapter(pagerAdapter);
    }

    private void buildFragments() {
        fragments.add(new AccountFragment(app.getCurrentUser()));
        fragments.add(new FavoriteFragment());
        setFragments(fragments);
    }

    private void setupTabIcons() {
        mTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private String[] loadTitles() {
       return getResources().getStringArray(R.array.tabs_profile);
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
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
        getMenuInflater().inflate(R.menu.menu_profile, menu);
//        int color = getResources().getColor(R.color.white);
//        MenuColorizer.colorMenu(this, menu, color);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile_about) {
            showToast(getString(R.string.app_name) + Android.getAppVersion(this));
            return true;
        } else {
            goToMainActivity(app.getCurrentUser());
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
        goToMainActivity(app.getCurrentUser());
    }

    //---------------------- [CALLBAKCS]
    @Override
    public void onClick(View v) {
        if (v instanceof FloatingActionButton);
        presenter.validateFields(((AccountFragment) getFragments().get(0)).getFields());

    }

    @Override
    public void showLoader() {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void validateFieldsError() {
        showToast(getString(R.string.error_validate_fields));
    }

    @Override
    public void validateFieldsOk() {
        presenter.save(((AccountFragment) getFragments().get(0)).getFields(),app.getCurrentUser());
    }




}
