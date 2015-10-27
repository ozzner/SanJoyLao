package rsantillanc.sanjoylao.ui.custom.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.ui.fragment.CommentsFragment;
import rsantillanc.sanjoylao.ui.fragment.DescriptionFragment;

/**
 * Created by RenzoD on 08/06/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private CharSequence tabTitles[];
    private int numTabs;
    private Context _context;
    private int[] icons = {R.drawable.ic_info_plate,R.drawable.ic_comment};


    public ViewPagerAdapter(FragmentManager fm, Context _context, CharSequence[] tabTitles, int numTabs) {
        super(fm);
        this._context = _context;
        this.tabTitles = tabTitles;
        this.numTabs = numTabs;
    }


   @Override
    public CharSequence getPageTitle(int position){
        Drawable image = _context.getResources().getDrawable(icons[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image);
        sb.setSpan(imageSpan, 0,sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }


    @Override
    public Fragment getItem(int position) {

        Fragment page = null;

        switch (position){
            case 0:
                page = DescriptionFragment.newInstance(null,null);
                break;
            case 1:
                page = CommentsFragment.newInstance(null,null);
                break;
            default:
                page = CommentsFragment.newInstance(null,null);
                break;
        }

        return page;
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
