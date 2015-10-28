package rsantillanc.sanjoylao.ui.mvp.Order;

import java.util.List;

import rsantillanc.sanjoylao.model.BanquetModel;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public class OrderPresenterImpl implements IOrderPresenter, OnOrdersListener {

    private OrderIteractorImpl mIterator;
    private IOrderView mView;

    public OrderPresenterImpl(IOrderView view) {
        this.mIterator = new OrderIteractorImpl();
        this.mView = view;
    }

    private double getTotalPrice(Iterable<? extends Object> orders) {
        double total = 0.0;
        for (Object banquet : orders) {
            total += ((BanquetModel) banquet).getPrice();
        }
        return total;
    }


    @Override
    public void loadOrdersByUserId(String userID) {
        mIterator.getOrdersFromServer(userID, this);
    }

    @Override
    public void onOrdersSuccess(List<Object> orders) {
        double totalPrice = getTotalPrice(orders);

        if (totalPrice > 1500)
            mView.printDiscount(totalPrice);

        //Pass
        mView.printAmount(totalPrice);
        mView.onDataLoaded(orders);
    }

    @Override
    public void onOrdersError(CharSequence error) {

    }
}
