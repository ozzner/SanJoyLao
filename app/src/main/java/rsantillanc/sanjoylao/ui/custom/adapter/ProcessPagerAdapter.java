package rsantillanc.sanjoylao.ui.custom.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;

import rsantillanc.sanjoylao.R;

/**
 * Created by Computer on 02/12/2015.
 */
public class ProcessPagerAdapter extends PagerAdapter {


    @Override
    public int getCount() {
        return 2;
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
                resId = R.id.lay_process_location;
                break;
            case 1:
                resId = R.id.lay_process_payment;
                break;

        }
        return container.findViewById(resId);
    }




}
