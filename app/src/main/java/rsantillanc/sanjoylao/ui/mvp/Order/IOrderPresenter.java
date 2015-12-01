package rsantillanc.sanjoylao.ui.mvp.Order;

import rsantillanc.sanjoylao.model.OrderModel;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public interface IOrderPresenter {
    void sendPushNotification(OrderModel order);
}
