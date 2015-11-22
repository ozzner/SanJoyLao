package rsantillanc.sanjoylao.ui.mvp.Order;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.OrderDetailModel;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public class OrderPresenterImpl implements IOrderPresenter, OnOrderListener {

    public static final double MIN_PRICE_TO_DISCOUNT = 300.00;
    public static final int DISCOUNT = 12;
    public static final double PERCENT = (100.00 - DISCOUNT) / 100.00;
    double amount = 0.0;


    private OrderIteractorImpl mIterator;
    private IOrderView mView;
    private Activity mActivity;

    public OrderPresenterImpl(IOrderView view, Activity actitvity) {
        this.mIterator = new OrderIteractorImpl();
        this.mView = view;
        this.mActivity = actitvity;
    }


    public void loadOrders() {
        mIterator.getOrdersFrom(mActivity, this);
    }


    //{On Order Listener}
    @Override
    public void onOrdersError(Context c, CharSequence error) {
        Toast.makeText(c, error, Toast.LENGTH_SHORT).show();
        mView.hideLoader();
    }

    @Override
    public void onLoadDetails(Context c, List<OrderDetailModel> orderDetails) {
//        Toast.makeText(c, "Success loading orders detail size: " + orderDetails.size(), Toast.LENGTH_SHORT).show();
        mView.hideLoader();
        mView.onOrderDetailsLoaded(orderDetails);
        buildTotalPrice(orderDetails);
    }

    @Override
    public void onDeleteSuccess(CharSequence message) {
        mView.onDeleteSuccess(message);
    }

    @Override
    public void onCounterSuccess(Context c, CharSequence ok) {

    }

    @Override
    public void paymentCorrect(double amount) {
        mView.hideLoader();
        mView.onPaymentSuccess(amount);
    }

    public void buildTotalPrice(List<OrderDetailModel> orderDetails) {
        for (OrderDetailModel orderDetail : orderDetails)
            amount = (orderDetail.getPlateSize().getPrice() * orderDetail.getCounter()) + amount;

        if (amount > MIN_PRICE_TO_DISCOUNT)
            mView.printDiscount(amount, getPriceWithDiscount(amount), printPercent());
        else
            mView.printAmount(amount);


    }

    private CharSequence printPercent() {
        return DISCOUNT + Const.PERCENT_OPERATOR + "\nDesc.";
    }

    private double getPriceWithDiscount(double total) {
        return total * PERCENT;
    }


    public void deleteAnItemDetail(Context c, OrderDetailModel itemDetail) {
        mIterator.callDeleteDetail(c, itemDetail, this);
    }

    public void processPayment() {
        mView.showLoader(mActivity.getString(R.string.progress_message_processing));
        mIterator.sendPayment((amount > MIN_PRICE_TO_DISCOUNT) ? getPriceWithDiscount(amount) : amount,this);
    }
}
