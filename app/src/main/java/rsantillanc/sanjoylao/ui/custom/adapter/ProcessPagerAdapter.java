package rsantillanc.sanjoylao.ui.custom.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import rsantillanc.sanjoylao.R;

/**
 * Created by Computer on 02/12/2015.
 */
public class ProcessPagerAdapter extends PagerAdapter {


    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(View container, int position) {
        int resId = 0;
        switch (position) {
            case 0:
                resId = R.id.lay_process_booking;
                break;
            case 1:
                resId = R.id.lay_process_delivery;
                break;
            case 2:
                resId = R.id.lay_process_payment;
                break;

        }
        return container.findViewById(resId);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }


}
