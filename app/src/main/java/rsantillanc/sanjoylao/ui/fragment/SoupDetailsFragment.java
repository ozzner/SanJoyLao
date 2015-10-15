package rsantillanc.sanjoylao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.ui.custom.adapter.ViewPagerAdapter;
import rsantillanc.sanjoylao.ui.custom.view.SlidingTabLayout;

public class SoupDetailsFragment extends Fragment {
    private static final String KEY_TITLES = "pager_titles";
    private static SoupDetailsFragment instance;
    private RatingBar mRatingBar;
    private SlidingTabLayout mSlindingTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private CharSequence[] titles = null;


    public static SoupDetailsFragment getInstance() {
            instance =  new SoupDetailsFragment();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titles = getResources().getStringArray(R.array.tabs_names);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequenceArray(KEY_TITLES, titles);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_details_options, container, false);
        initComponents(v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        titles = savedInstanceState.getCharSequenceArray(KEY_TITLES);
    }

    private void initComponents(View v) {
        /*init views*/
        mRatingBar = (RatingBar)v.findViewById(R.id.rb_rates_plate);
        mSlindingTabLayout = (SlidingTabLayout)v.findViewById(R.id.tabs);
        mViewPager = (ViewPager)v.findViewById(R.id.pager);

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

        mAdapter = new ViewPagerAdapter(getFragmentManager(),
                getActivity(),
                titles,
                titles.length);

        mViewPager.setAdapter(mAdapter);
    }

    protected void setUpSlindingTab(){
        mSlindingTabLayout.setCustomTabView(R.layout.custom_tab, 0);
        mSlindingTabLayout.setDistributeEvenly(true);
        mSlindingTabLayout.setViewPager(mViewPager);
        mSlindingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.whitesmoke);
            }

        });
    }



}
