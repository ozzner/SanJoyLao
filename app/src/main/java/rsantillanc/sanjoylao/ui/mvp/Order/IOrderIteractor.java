package rsantillanc.sanjoylao.ui.mvp.Order;

import android.content.Context;

import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.model.UserModel;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public interface IOrderIteractor {

    void getOrdersFromServer(String clausule, OnOrdersListener listener, Context c);

    void addItemToOrder(Context c,PlateSizeModel plateSize, UserModel user, OnOrdersListener listener);
}
