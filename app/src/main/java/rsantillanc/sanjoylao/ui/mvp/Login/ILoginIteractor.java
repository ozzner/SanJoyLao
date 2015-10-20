package rsantillanc.sanjoylao.ui.mvp.Login;

import rsantillanc.sanjoylao.interfaces.OnLoginListener;
import rsantillanc.sanjoylao.model.UserModel;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public interface ILoginIteractor {
    void registerUserOnBackend(UserModel oUser, OnLoginListener listener);
}
