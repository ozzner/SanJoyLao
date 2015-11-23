package rsantillanc.sanjoylao.ui.mvp.OrderHistory;

import android.content.Context;

import java.util.List;

import rsantillanc.sanjoylao.model.OrderModel;

/**
 * Created by RenzoD on 22/11/2015.
 */
public class OrderHistoryPresenter implements IOrderHistoryPresenter, OnOrderHistoryListener {

    private IOrderHistoryView view;
    private OrderHistoryIteractorImpl implement;
    private Context _context;

    public OrderHistoryPresenter(Context _context, IOrderHistoryView view) {
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
}
