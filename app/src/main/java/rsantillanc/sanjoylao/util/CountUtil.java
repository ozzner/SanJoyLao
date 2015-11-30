package rsantillanc.sanjoylao.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.ui.custom.view.CountView;

/**
 * Created by RenzoD on 29/11/2015.
 */
public class CountUtil {
    public static void setCountCountView(Context context, LayerDrawable icon, int count) {

        CountView mCountView;

        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_count_view);
        if (reuse != null && reuse instanceof CountView) {
            mCountView = (CountView) reuse;
        } else
            mCountView = new CountView(context);


        mCountView.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_count_view, mCountView);
    }
}
