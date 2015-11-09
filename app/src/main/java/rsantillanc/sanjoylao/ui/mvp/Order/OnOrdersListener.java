package rsantillanc.sanjoylao.ui.mvp.Order;

import java.util.List;

import rsantillanc.sanjoylao.model.OrderDetailModel;
import rsantillanc.sanjoylao.model.OrderModel;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public interface OnOrdersListener {
    void onOrderCreated(OrderModel orders);
    void onOrderLoaded();
    void onOrdersError(CharSequence error);
    void onLoadDetails(List<OrderDetailModel> orderDetails);
}
