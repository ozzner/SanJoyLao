package rsantillanc.sanjoylao.ui.mvp.Order;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.LocalRestaurantModel;
import rsantillanc.sanjoylao.model.OrderDetailModel;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.model.OrderTypeModel;
import rsantillanc.sanjoylao.model.RelationOrder;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.storage.sp.SJLPreferences;
import rsantillanc.sanjoylao.ui.activity.OrderActivity;
import rsantillanc.sanjoylao.ui.custom.dialog.ProcessOrderDialog;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public class OrderPresenterImpl implements IOrderPresenter, OnOrderListener, ProcessOrderDialog.OnProcessOrderClickListener {

    public static final double MIN_PRICE_TO_DISCOUNT = 300.00;
    public static final int DISCOUNT = 12;
    public static final double PERCENT = (100.00 - DISCOUNT) / 100.00;
    public static final int ACTION_LOCALS = 1;
    public static final int ACTION_ORDER_TYPE = 2;
    double amount;
    int counter;


    private OrderIteractorImpl iteractor;
    private OrderActivity view;
    private Activity mActivity;
    public boolean flagSave = false;
    private SJLPreferences preferences;
    private ProcessOrderDialog processOrder;


    public OrderPresenterImpl(OrderActivity view, Activity activity) {
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

    public void processOrder(RelationOrder buildOrder) {
        view.updateMessageProgressDialog(mActivity.getString(R.string.progress_message_doing_order));
        iteractor.checkoutOrder(this, mActivity.getApplicationContext(), buildOrder);
    }

    @Override
    public void onDeleteSuccess(CharSequence message) {
        view.onDeleteSuccess(message);
    }

    @Override
    public void onCounterSuccess(Context c, CharSequence ok, long counter) {

    }

    @Override
    public void paymentCorrect( final double amount) {
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

    public void processPayment(RelationOrder buildOrder) {
        view.showLoader(mActivity.getString(R.string.progress_message_processing));
        iteractor.sendPayment(buildOrder,(amount > MIN_PRICE_TO_DISCOUNT) ? getPriceWithDiscount(amount) : amount, this);
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

    public void showAlertDialogOrder(UserModel currentUser, RelationOrder buildOrder, boolean isDelivery) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.EXTRA_USER, currentUser);
        bundle.putSerializable(Const.EXTRA_BUILD_ORDER, buildOrder);
        bundle.putBoolean(Const.EXTRA_PARAM_IS_DELIVERY, isDelivery);
        processOrder = new ProcessOrderDialog(bundle);
        processOrder.setListener(this);
        processOrder.show(view.getSupportFragmentManager(), "alert_input_order");
    }


    @Override
    public void onClickSendButton(RelationOrder buildOrder) {
        view.buildOrderSuccess(buildOrder);
        processPayment(buildOrder);
    }

    @Override
    public void onError(CharSequence sc) {

    }

    public void saveSizeOrders(long size) {
        if (preferences == null)
            preferences = new SJLPreferences(mActivity);

        preferences.saveCounter((int) size);
    }

    public void loadLocals() {
        new AsyncTaskProcess().execute(ACTION_LOCALS);
    }

    public void loadOrdersType() {
        new AsyncTaskProcess().execute(ACTION_ORDER_TYPE);
    }


    /**
     * AsyncTask
     **/

    class AsyncTaskProcess extends AsyncTask<Object, Object, Object> {
        private Object object;


        @Override
        protected Object doInBackground(Object... objects) {
            int type = (int) objects[0];

            switch (type) {
                case ACTION_LOCALS:
                    object = iteractor.getLocals(mActivity);
                    break;
                case ACTION_ORDER_TYPE:
                    object = iteractor.getOrderTypes(mActivity);
                    break;
            }

            return object;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);

            if (result instanceof ArrayList<?>) {
                Object object = ((ArrayList<?>) result).get(0);

                if (object instanceof LocalRestaurantModel) {
                    view.localsLoaded(((ArrayList<LocalRestaurantModel>) result));

                } else if (object instanceof OrderTypeModel) {
                    view.ordersTypeLoaded(((ArrayList<OrderTypeModel>) result));
                }
            }

        }
    }
}
