package rsantillanc.sanjoylao.ui.mvp.Login;

import rsantillanc.sanjoylao.model.UserModel;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public interface ILoginIteractor {
    void doSignin(UserModel oUser, OnRegisterListener listener);
    void doLogin(Object obj,OnLoginListener listener);
}
