package rsantillanc.sanjoylao.ui.mvp.Order;

import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.model.RelationOrder;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public interface IOrderPresenter {
    void sendPushNotification(RelationOrder order);
}
