package rsantillanc.sanjoylao.ui.mvp.Order;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.OrderDetailModel;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.storage.sp.SJLPreferences;
import rsantillanc.sanjoylao.ui.custom.dialog.ProcessOrderDialog;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public class OrderPresenterImpl implements IOrderPresenter, OnOrderListener , ProcessOrderDialog.OnProcessOrderClickListener{

    public static final double MIN_PRICE_TO_DISCOUNT = 300.00;
    public static final int DISCOUNT = 12;
    public static final double PERCENT = (100.00 - DISCOUNT) / 100.00;
    double amount;
    int counter;


    private OrderIteractorImpl iteractor;
    private IOrderView view;
    private Activity mActivity;
    public boolean flagSave = false;
    private SJLPreferences preferences;
    private ProcessOrderDialog processOrder;


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
        view.showMessage(error);
        view.hideLoader();
    }

    @Override
    public void onLoadDetails(Context c, final List<OrderDetailModel> orderDetails) {
        preferences.saveCounter(orderDetails.size());
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
    public void onCounterSuccess(Context c, CharSequence ok, long counter) {

    }

    @Override
    public void paymentCorrect(final double amount) {
        view.updateMessageProgressDialog(mActivity.getString(R.string.correct_payment));
        view.enabledPaymentButton(false);
        view.onPaymentSuccess(amount);
    }

    @Override
    public void orderCheckoutSuccess(final CharSequence s, OrderModel order) {
        view.hideLoader();
        view.clearAll();
        view.orderCheckoutSuccess(s, order);
        preferences.saveCounter(0);
    }

    @Override
    public void errorUpdating(CharSequence error) {
        view.showMessage(error);
    }

    public void buildTotalPrice(List<OrderDetailModel> orderDetails) {
        amount = 0.0;
        counter = 0;

        double beforeAmount = preferences.getCurrentAmount();

        for (OrderDetailModel orderDetail : orderDetails) {
            amount = (orderDetail.getPlateSize().getPrice() * orderDetail.getCounter()) + amount;
            counter += orderDetail.getCounter();
        }
        //If is required
        updateFlag(beforeAmount);


        if (amount > MIN_PRICE_TO_DISCOUNT)
            view.printDiscount(amount, getPriceWithDiscount(amount), printPercent());
        else
            view.printAmount(amount);

        //Save value
        preferences.saveCurrentAmount(amount);
        preferences.saveCounter(counter);

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


    public void deleteItemDetail(Context c, OrderDetailModel itemDetail) {
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
    public void sendPushNotification(OrderModel order) {
        iteractor.makePushNotification(order);
    }

    public void showAlertDialogOrder() {
        processOrder = new ProcessOrderDialog();
        processOrder.setListener(this);
        processOrder.show(mActivity.getFragmentManager(), "alert_input_order");
    }


    @Override
    public void onClickSendButton() {
        processPayment();
    }

    @Override
    public void onError(CharSequence sc) {

    }

    public void saveSizeOrders(long size) {
        if (preferences == null)
            preferences = new SJLPreferences(mActivity);

        preferences.saveCounter((int) size);
    }
}
