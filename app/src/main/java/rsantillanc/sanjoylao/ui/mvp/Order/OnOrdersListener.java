package rsantillanc.sanjoylao.ui.mvp.Order;

import android.content.Context;

import java.util.List;

import rsantillanc.sanjoylao.model.OrderDetailModel;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public interface OnOrdersListener {
    void onOrdersError(Context c, CharSequence error);
    void onLoadDetails(Context c, List<OrderDetailModel> orderDetails);
}
