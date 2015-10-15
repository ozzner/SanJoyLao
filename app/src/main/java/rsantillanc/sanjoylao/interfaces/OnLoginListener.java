package rsantillanc.sanjoylao.interfaces;

/**
 * Created by RenzoD on 14/10/2015.
 */
public interface OnLoginListener {
    void onFacebookFailed(Object obj);
    void onGoogleFailed(Object obj);
    void onSuccessFacebook(Object obj);
    void onSuccessGoogle(Object obj);
}
