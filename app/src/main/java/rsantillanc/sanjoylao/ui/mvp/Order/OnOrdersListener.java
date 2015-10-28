package rsantillanc.sanjoylao.ui.mvp.Order;

import java.util.List;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public interface OnOrdersListener {
    void onOrdersSuccess(List<Object> orders);
    void onOrdersError(CharSequence error);
}
