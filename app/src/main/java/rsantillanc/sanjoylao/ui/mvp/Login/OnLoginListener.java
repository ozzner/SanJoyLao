package rsantillanc.sanjoylao.ui.mvp.Login;

import rsantillanc.sanjoylao.model.APIRequestSignInModel;

/**
 * Created by RenzoD on 21/10/2015.
 */
public interface OnLoginListener {
    void onLoginSuccess(Object currentUser);
    void onLoginError(CharSequence message);
    void onBasicAuthenticationSuccess(Object userLogued);
    void onBasicAuthenticationError(APIRequestSignInModel signin);
}
