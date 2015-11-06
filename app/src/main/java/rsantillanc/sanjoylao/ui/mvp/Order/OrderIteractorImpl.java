package rsantillanc.sanjoylao.ui.mvp.Order;

import rsantillanc.sanjoylao.model.BanquetModel;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public class OrderIteractorImpl implements IOrderIteractor {


    @Override
    public void getOrdersFromServer(String clausule, final OnOrdersListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    listener.onOrdersSuccess(new BanquetModel().dummyBanquets());
                } catch (InterruptedException e) {
                    listener.onOrdersError(e.getMessage());
                }
            }
        }).start();
    }
}
