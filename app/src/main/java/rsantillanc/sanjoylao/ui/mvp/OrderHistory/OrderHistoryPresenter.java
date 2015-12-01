package rsantillanc.sanjoylao.ui.mvp.OrderHistory;

import android.content.Context;

import java.util.List;

import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.ui.activity.OrderHistoryActivity;

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


    public void changeOrderStatus(OrderModel order, Context context, int statusCode, String userID) {
        implement.upgradeOrder(order, context, statusCode, this, userID);
    }


}
