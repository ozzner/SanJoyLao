package rsantillanc.sanjoylao.ui.mvp.Order;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.OrderDetailModel;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.storage.sp.SJLPreferences;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public class OrderPresenterImpl implements IOrderPresenter, OnOrderListener {

    public static final double MIN_PRICE_TO_DISCOUNT = 300.00;
    public static final int DISCOUNT = 12;
    public static final double PERCENT = (100.00 - DISCOUNT) / 100.00;
    double amount;


    private OrderIteractorImpl iteractor;
    private IOrderView view;
    private Activity mActivity;
    public boolean flagSave = false;
    private SJLPreferences preferences;

    public OrderPresenterImpl(IOrderView view, Activity activity) {
        this.iteractor = new OrderIteractorImpl();
        this.view = view;
        this.mActivity = activity;
        this.preferences = new SJLPreferences(mActivity.getApplicationContext());
        this.flagSave = false;
    }


    public void loadOrders() {
        iteractor.getOrdersFrom(mActivity, this);
    }


    //{On Order Listener}
    @Override
    public void onOrdersError(Context c, CharSequence error) {
        Toast.makeText(c, error, Toast.LENGTH_SHORT).show();
        view.hideLoader();
    }

    @Override
    public void onLoadDetails(Context c, List<OrderDetailModel> orderDetails) {
        view.hideLoader();
        view.onOrderDetailsLoaded(orderDetails);
        buildTotalPrice(orderDetails);
    }

    public void processOrder(OrderModel order) {
        view.updateMessageProgressDialog(mActivity.getString(R.string.progress_message_doing_order));
        iteractor.checkoutOrder(this, mActivity.getApplicationContext(), order);
    }

    @Override
    public void onDeleteSuccess(CharSequence message) {
        view.onDeleteSuccess(message);
    }

    @Override
    public void onCounterSuccess(Context c, CharSequence ok) {

    }

    @Override
    public void paymentCorrect(double amount) {
        view.hideLoader();
        view.enabledPaymentButton(false);
        view.onPaymentSuccess(amount);
    }

    @Override
    public void orderCheckoutSuccess(CharSequence s) {
        view.hideLoader();
        view.clearAll();
        view.orderCheckoutSuccess(s);
    }

    @Override
    public void errorUpdating(CharSequence error) {
        view.showMessage(error);
    }

    public void buildTotalPrice(List<OrderDetailModel> orderDetails) {
        amount = 0.0;
        double beforeAmount = preferences.getCurrentAmount();

        for (OrderDetailModel orderDetail : orderDetails)
            amount = (orderDetail.getPlateSize().getPrice() * orderDetail.getCounter()) + amount;

        //If is requiered
        updateFlag(beforeAmount);


        if (amount > MIN_PRICE_TO_DISCOUNT)
            view.printDiscount(amount, getPriceWithDiscount(amount), printPercent());
        else
            view.printAmount(amount);

        //Save value
        preferences.saveCurrentAmount(amount);

    }

    private void updateFlag(double beforeAmount) {
        if (beforeAmount != amount)
            flagSave = true;
    }

    private CharSequence printPercent() {
        return DISCOUNT + Const.PERCENT_OPERATOR + "\nDesc.";
    }

    private double getPriceWithDiscount(double total) {
        return total * PERCENT;
    }


    public void deleteAnItemDetail(Context c, OrderDetailModel itemDetail) {
        iteractor.callDeleteDetail(c, itemDetail, this);
    }

    public void processPayment() {
        view.showLoader(mActivity.getString(R.string.progress_message_processing));
        iteractor.sendPayment((amount > MIN_PRICE_TO_DISCOUNT) ? getPriceWithDiscount(amount) : amount, this);
    }

    public void saveChanges(List<OrderDetailModel> details) {
        if (flagSave)
            iteractor.saveAllChanges(amount, details, mActivity.getApplicationContext(), new OrderIteractorImpl.OnSaveListener() {
                @Override
                public void onSuccess() {
                    flagSave = false;
                }
            });
    }

    @Override
    public void sendPushNotification() {
        iteractor.makePushNotification();
    }
}
