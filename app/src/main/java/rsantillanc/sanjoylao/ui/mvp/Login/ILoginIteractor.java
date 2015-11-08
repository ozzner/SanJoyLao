package rsantillanc.sanjoylao.ui.mvp.Login;

import android.content.Context;

import rsantillanc.sanjoylao.model.APIRequestSignInModel;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public interface ILoginIteractor {
    void doSignin(APIRequestSignInModel oUser, OnRegisterListener listener);
    void doLogin(Context context, String username, String password, OnLoginListener listener);
    void basicAuthentication(String username, String password, OnLoginListener listener);
    void setSignInUserModel(APIRequestSignInModel signin);
    void sendContext(Context context);
}
