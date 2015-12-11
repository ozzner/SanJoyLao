package rsantillanc.sanjoylao.ui.mvp.OrderHistory;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.storage.dao.OrderDao;
import rsantillanc.sanjoylao.storage.dao.StatusDao;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by RenzoD on 22/11/2015.
 */
public class OrderHistoryIteractorImpl implements IOrderHistoryIteractor {
    Handler handler;

    @Override
    public void loadAllHistoryOrders(final OnOrderHistoryListener listener, final Context c, final String userID) {
        handler = new Handler(Looper.myLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                List<OrderModel> orders = new OrderDao(c).getOrders(userID, c);
                if (orders != null)
                    listener.onFindDataSuccess(orders, c);
                else
                    listener.onErrorLoad(c.getString(R.string.error_empty_data));
            }
        });
    }

    @Override
    public void updateOrder(Context c, String orderID, String objectId, int statusCode) {
        int i = new OrderDao(c).updateStatus(new StatusDao(c).getStatusByCode(statusCode), orderID);
        Log.e(Const.DEBUG, "upgrade: " + i);
    }


//    @Override
//    public void upgradeOrder(OrderModel order, Context c, int statusCode, OrderHistoryPresenter lis, String userID) {
//        int i = new OrderDao(c).update(new StatusDao(c).getStatusByCode(statusCode), order);
//        Log.e(Const.DEBUG,"upgrade: " + i);
//        if (i>0)
//            lis.onSuccessOrderUpgraded(c,"success!", userID);
//    }


}
