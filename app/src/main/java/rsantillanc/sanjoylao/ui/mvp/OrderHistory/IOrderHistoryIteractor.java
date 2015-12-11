package rsantillanc.sanjoylao.ui.mvp.OrderHistory;

import android.content.Context;

/**
 * Created by RenzoD on 22/11/2015.
 */
public interface IOrderHistoryIteractor {
    void loadAllHistoryOrders(OnOrderHistoryListener listener, Context c, String userID);
    void updateOrder(Context _context, String orderID, String objectId, int statusCode);
}
