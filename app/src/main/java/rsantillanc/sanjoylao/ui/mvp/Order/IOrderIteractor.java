package rsantillanc.sanjoylao.ui.mvp.Order;

import android.content.Context;

import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.model.RelationOrder;
import rsantillanc.sanjoylao.model.UserModel;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public interface IOrderIteractor {
    void getOrdersFromServer(String clausule, OnOrderListener listener, Context c);
    void addItemToOrder(Context c,PlateSizeModel plateSize, UserModel user, OnOrderListener listener);
    void makePushNotification(RelationOrder order);
}
