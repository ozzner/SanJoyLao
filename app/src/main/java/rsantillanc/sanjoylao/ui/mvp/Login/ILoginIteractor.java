package rsantillanc.sanjoylao.ui.mvp.Login;

import rsantillanc.sanjoylao.interfaces.OnLoginListener;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public interface ILoginIteractor {
    void loginUsingFacebook(OnLoginListener listener);
    void loginUsingGoogle(OnLoginListener listener);
}
