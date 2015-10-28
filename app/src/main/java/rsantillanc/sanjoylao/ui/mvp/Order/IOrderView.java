package rsantillanc.sanjoylao.ui.mvp.Order;

import java.util.List;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public interface IOrderView {
    void showLoader();
    void hideLoader();
    void openItem(Object item);
    void upPrice();
    void downPrice();
    void deleteItem(int position);
    void showPaymentMethod();
    void onDataLoaded(List<Object> orders);
    void printAmount(double amount);
    void printDiscount(double amount);
}
