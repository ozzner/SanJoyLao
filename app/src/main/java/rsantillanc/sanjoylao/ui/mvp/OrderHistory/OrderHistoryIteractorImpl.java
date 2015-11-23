package rsantillanc.sanjoylao.ui.mvp.OrderHistory;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.storage.dao.OrderDao;

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
                    listener.onFindDataSuccess(orders);
                else
                    listener.onErrorLoad(c.getString(R.string.error_empty_data));
            }
        });
    }

}
