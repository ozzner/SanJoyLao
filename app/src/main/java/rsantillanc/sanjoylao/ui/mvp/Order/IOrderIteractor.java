package rsantillanc.sanjoylao.ui.mvp.Order;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public interface IOrderIteractor {

    void getOrdersFromServer(String clausule, OnOrdersListener listener);
}
