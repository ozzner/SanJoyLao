package rsantillanc.sanjoylao.view.popup;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.custom.adapter.ViewPagerAdapter;
import rsantillanc.sanjoylao.model.OptionsModel;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.SlidingTabLayout;

public class DetailsOptionsPopup extends ActionBarActivity {
    private OptionsModel oOption;
    private RatingBar mRatingBar;
    private SlidingTabLayout mSlindingTabLayout;
    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_details_options);
        oOption = (OptionsModel) getIntent().getSerializableExtra(Const.TAG_DETAILS_OPTIONS);
        initComponents();
    }

    private void initComponents() {
        /*init views*/
        mRatingBar = (RatingBar)findViewById(R.id.rb_rates_plate);
        mSlindingTabLayout = (SlidingTabLayout)findViewById(R.id.tabs);
        mViewPager = (ViewPager)findViewById(R.id.pager);

        /*setup*/
        setUpRatingBar();
        setUpViewPager();
        setUpSlindingTab();
    }



    private void setUpRatingBar() {
        mRatingBar.setIsIndicator(true);
        mRatingBar.setMax(10);
        mRatingBar.setStepSize((float) 0.5);
        mRatingBar.setNumStars(6);
        mRatingBar.setRating((float) 2.5);
    }

    private void setUpViewPager() {
        CharSequence[] titles = getResources().getStringArray(R.array.tabs_names);

        ViewPagerAdapter mAdapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                getApplicationContext(),
                titles,
                titles.length);

        mViewPager.setAdapter(mAdapter);
    }

    protected void setUpSlindingTab(){
        mSlindingTabLayout.setCustomTabView(R.layout.custom_tab,0);
        mSlindingTabLayout.setDistributeEvenly(true);
        mSlindingTabLayout.setViewPager(mViewPager);
        mSlindingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.whitesmoke);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details_options_popup, menu);
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
}
