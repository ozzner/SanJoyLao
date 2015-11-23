package rsantillanc.sanjoylao.ui.mvp.OrderHistory;

import java.util.List;

import rsantillanc.sanjoylao.model.OrderModel;

/**
 * Created by RenzoD on 22/11/2015.
 */
public interface OnOrderHistoryListener {
    void onFindDataSuccess(List<OrderModel> orders);
    void onErrorLoad(CharSequence error);
}
