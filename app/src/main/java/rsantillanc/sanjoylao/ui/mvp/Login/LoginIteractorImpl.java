package rsantillanc.sanjoylao.ui.mvp.Login;

import rsantillanc.sanjoylao.interfaces.OnLoginListener;
import rsantillanc.sanjoylao.model.UserModel;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public class LoginIteractorImpl implements ILoginIteractor {

    @Override
    public void registerUserOnBackend(final UserModel oUser, final OnLoginListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                    listener.onSuccess(oUser);
                } catch (InterruptedException e) {
                    listener.onError(e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
