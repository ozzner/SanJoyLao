package rsantillanc.sanjoylao.ui.custom.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.ui.fragment.AccountFragment;
import rsantillanc.sanjoylao.ui.fragment.FavoriteFragment;

/**
 * Created by RenzoD on 08/06/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private CharSequence tabTitles[];
    private int numTabs;
    private UserModel currentUser;


    public ViewPagerAdapter(FragmentManager fm, CharSequence[] tabTitles, UserModel user) {
        super(fm);
        this.tabTitles = tabTitles;
        this.numTabs = tabTitles.length;
        this.currentUser = user;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


    @Override
    public Fragment getItem(int position) {
        Fragment page;

        switch (position) {
            case 0:
                page = AccountFragment.newInstance(currentUser);
                break;
            default:
                page = FavoriteFragment.newInstance();
                break;
        }

        return page;
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
