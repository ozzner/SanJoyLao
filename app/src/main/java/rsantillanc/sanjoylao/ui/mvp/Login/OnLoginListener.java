package rsantillanc.sanjoylao.ui.mvp.Login;

/**
 * Created by RenzoD on 21/10/2015.
 */
public interface OnLoginListener {
    void onLoginSuccess(Object currentUser);
    void onLoginError(CharSequence message);
}
