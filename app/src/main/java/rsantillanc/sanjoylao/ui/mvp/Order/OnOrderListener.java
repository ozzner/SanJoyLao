package rsantillanc.sanjoylao.ui.mvp.Order;

import android.content.Context;

import java.util.List;

import rsantillanc.sanjoylao.model.OrderDetailModel;
import rsantillanc.sanjoylao.model.OrderModel;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public interface OnOrderListener {
    void onOrdersError(Context c, CharSequence error);
    void onLoadDetails(Context c, List<OrderDetailModel> orderDetails);
    void onDeleteSuccess(CharSequence message);
    void onCounterSuccess(Context c, CharSequence ok, long counter);
    void paymentCorrect(double amount);
    void orderCheckoutSuccess(CharSequence s, OrderModel order);
    void errorUpdating(CharSequence error);
}
