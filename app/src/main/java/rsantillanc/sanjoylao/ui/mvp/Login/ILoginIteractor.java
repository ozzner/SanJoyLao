package rsantillanc.sanjoylao.ui.mvp.Login;

import android.content.Context;

import rsantillanc.sanjoylao.model.APISignInModel;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public interface ILoginIteractor {
    void doSignin(APISignInModel oUser, OnRegisterListener listener);
    void doLogin(Context context, String username, String password, OnLoginListener listener);
    void basicAuthentication(String username, String password, OnLoginListener listener);
    void setSignInUserModel(APISignInModel signin);
    void sendContext(Context context);
}
