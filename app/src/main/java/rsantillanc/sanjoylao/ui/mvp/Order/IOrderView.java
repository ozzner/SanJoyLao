package rsantillanc.sanjoylao.ui.mvp.Order;

import java.util.List;

import rsantillanc.sanjoylao.model.OrderDetailModel;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public interface IOrderView {
    void hideLoader();
    void onOrderDetailsLoaded(List<OrderDetailModel> orders);
    void printAmount(double amount);
    void printDiscount(double amount,double amountWithDiscount, CharSequence percent);
    void onDeleteSuccess(CharSequence message);
    void onPaymentSuccess(double amount);
    void showLoader(CharSequence message);
    void enabledPaymentButton(boolean on);
    void updateMessageProgressDialog(CharSequence message);
    void clearAll();

    void showMessage(CharSequence sc);

    void orderCheckoutSuccess(CharSequence s);
}
