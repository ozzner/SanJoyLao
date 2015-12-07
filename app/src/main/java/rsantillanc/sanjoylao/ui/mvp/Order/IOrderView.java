package rsantillanc.sanjoylao.ui.mvp.Order;

import java.util.ArrayList;
import java.util.List;

import rsantillanc.sanjoylao.model.LocalRestaurantModel;
import rsantillanc.sanjoylao.model.OrderDetailModel;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.model.OrderTypeModel;
import rsantillanc.sanjoylao.model.RelationOrder;

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
    void orderCheckoutSuccess(CharSequence s, OrderModel order);
    void localsLoaded(ArrayList<LocalRestaurantModel> locals);
    void ordersTypeLoaded(ArrayList<OrderTypeModel> ordersTypeList);
    void buildOrderSuccess(RelationOrder buildOrder);
}
