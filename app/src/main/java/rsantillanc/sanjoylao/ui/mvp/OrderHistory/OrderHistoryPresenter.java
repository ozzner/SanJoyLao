package rsantillanc.sanjoylao.ui.mvp.OrderHistory;

import android.content.Context;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.model.StatusModel;
import rsantillanc.sanjoylao.ui.activity.OrderHistoryActivity;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by RenzoD on 22/11/2015.
 */
public class OrderHistoryPresenter implements IOrderHistoryPresenter, OnOrderHistoryListener {

    private OrderHistoryActivity view;
    private OrderHistoryIteractorImpl implement;
    private Context _context;

    public OrderHistoryPresenter(Context _context, OrderHistoryActivity view) {
        this._context = _context;
        this.view = view;
        implement = new OrderHistoryIteractorImpl();
    }


    public void loadOrderHistory(String userID) {
        implement.loadAllHistoryOrders(this, _context, userID);
    }


    //{On Order History Listener}
    @Override
    public void onFindDataSuccess(List<OrderModel> orders) {
        view.injectData(orders);
    }

    @Override
    public void onErrorLoad(CharSequence error) {
        view.showMessage(error);
    }

    @Override
    public void onSuccessOrderUpgraded(Context c, String s, String userID) {
        if (OrderHistoryActivity.isActive)
            view.refresh(c, userID);
    }


    public void changeOrderStatus(OrderModel order, Context context, int statusCode, String userID) {
        implement.upgradeOrder(order, context, statusCode, this, userID);
    }


    public void loopOrdersAndUpdate(OrderModel serialOrder, List<OrderModel> orders) {
        for (int i = 0; i < orders.size(); i++) {
            OrderModel orderItem = orders.get(i);

            if (orderItem.getObjectId().equals(serialOrder.getObjectId())) {
                StatusModel status = orderItem.getStatus();
                status.setCode(Const.STATUS_CONFIRMED);
                status.setName(view.getString(R.string.status_confirmed));

                //update
                orderItem.setStatus(status);
                orders.set(i, orderItem);
                view.updateOrdersAdapter(orders);
                view.refreshAdapter();
                view.showSnack();
                break;
            }
        }
    }
}
