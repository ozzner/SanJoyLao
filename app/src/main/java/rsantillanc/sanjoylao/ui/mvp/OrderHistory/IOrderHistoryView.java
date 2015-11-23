package rsantillanc.sanjoylao.ui.mvp.OrderHistory;

import java.util.List;

import rsantillanc.sanjoylao.model.OrderModel;

/**
 * Created by RenzoD on 22/11/2015.
 */
public interface IOrderHistoryView {
    void hideLoader();
    void showLoader();
    void injectData(List<OrderModel> data);
    void showMessage(CharSequence error);
}
