package rsantillanc.sanjoylao.ui.mvp.Order;

import android.app.Activity;

import java.util.List;

import rsantillanc.sanjoylao.model.FeastModel;
import rsantillanc.sanjoylao.model.OrderDetailModel;
import rsantillanc.sanjoylao.model.OrderModel;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public class OrderPresenterImpl implements IOrderPresenter, OnOrdersListener {

    private OrderIteractorImpl mIterator;
    private IOrderView mView;
    private Activity mActivity;

    public OrderPresenterImpl(IOrderView view, Activity actitvity) {
        this.mIterator = new OrderIteractorImpl();
        this.mView = view;
        this.mActivity = actitvity;
    }

    private double getTotalPrice(Iterable<? extends Object> orders) {
        double total = 0.0;
        for (Object banquet : orders) {
            total += ((FeastModel) banquet).getPrice();
        }
        return total;
    }


    @Override
    public void loadOrdersByUserId(String userID) {
        mIterator.getOrdersFromServer(userID, this);
    }


    @Override
    public void onOrderCreated(final OrderModel orders) {
//        final double totalPrice = orders.getPrice();
//
//        mActivity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//                if (totalPrice > 1500)
//                    mView.printDiscount(totalPrice);
//
//                //Pass
//                mView.printAmount(totalPrice);
//                mView.onDataLoaded(orders);
//                mView.hideLoader();
//            }
//
//        });

    }

    @Override
    public void onOrderLoaded() {

    }

    @Override
    public void onOrdersError(CharSequence error) {

    }

    @Override
    public void onLoadDetails(List<OrderDetailModel> orderDetails) {

    }
}
