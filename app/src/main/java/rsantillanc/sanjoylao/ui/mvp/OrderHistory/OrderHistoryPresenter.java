package rsantillanc.sanjoylao.ui.mvp.OrderHistory;

import android.content.Context;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.model.PushOrderModel;
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


    public void loadOrderHistory(Context c, String userID) {
        implement.loadAllHistoryOrders(this, c, userID);
    }


    //{On Order History Listener}
    @Override
    public void onFindDataSuccess(List<OrderModel> orders, Context c) {
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


//    public void changeOrderStatus(OrderModel order, Context context, int statusCode, String userID) {
//        implement.upgradeOrder(order, context, statusCode, this, userID);
//    }


    public void loopOrdersAndUpdate(PushOrderModel pushOrder, List<OrderModel> list, boolean isNewIntent) {
        List<OrderModel> orders = list;

        for (int i = 0; i < orders.size(); i++) {
            OrderModel orderItem = orders.get(i);

            if (orderItem.getObjectId().equals(pushOrder.getOrderObjectId())) {
                StatusModel status = orderItem.getStatus();
                status.setCode(pushOrder.getStatusCode());

                switch (pushOrder.getStatusCode()) {
                    case Const.STATUS_CONFIRMED:
                        status.setName(view.getString(R.string.status_confirmed));
                        break;

                    case Const.STATUS_CANCELLED:
                        status.setName(view.getString(R.string.status_cancelled));
                        break;
                }

                //Update
                orderItem.setStatus(status);
                orderItem.setOrderTimePreparation(pushOrder.getEstimatedTime());
                orders.set(i, orderItem);

                if (isNewIntent) {
                    view.updateOrdersAdapter(orders);
                    view.refreshAdapter();
                    CharSequence snack = null;
                    switch (status.getCode()) {
                        case Const.STATUS_CONFIRMED:
                            snack = view.getString(R.string.notification_order_confirmed);
                            break;
                        case Const.STATUS_CANCELLED:
                            snack = view.getString(R.string.notification_order_cancelled);
                            break;
                    }
                    view.showSnack(snack);
                } else
                    view.setupAdapter(orders);

                break;
            }
        }
    }
}
