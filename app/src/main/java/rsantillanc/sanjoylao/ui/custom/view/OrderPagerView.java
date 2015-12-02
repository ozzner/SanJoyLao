package rsantillanc.sanjoylao.ui.custom.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Computer on 02/12/2015.
 */
public class OrderPagerView extends ViewPager {


    public OrderPagerView(Context context) {
        super(context);
    }

    public OrderPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
