package rsantillanc.sanjoylao.ui.custom.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Collections;
import java.util.List;

import rsantillanc.sanjoylao.model.UserModel;

/**
 * Created by RenzoD on 08/06/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private CharSequence tabTitles[];
    private int numTabs;
    private UserModel currentUser;
    private List<Fragment> fragments = Collections.emptyList();


    public ViewPagerAdapter(FragmentManager fm, CharSequence[] tabTitles,  List<Fragment> list) {
        super(fm);
        this.tabTitles = tabTitles;
        this.numTabs = tabTitles.length;
        this.fragments = list;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
