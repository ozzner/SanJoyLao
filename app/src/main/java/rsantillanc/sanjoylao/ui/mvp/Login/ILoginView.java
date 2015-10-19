package rsantillanc.sanjoylao.ui.mvp.Login;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public interface ILoginView {
    void showLoader();
    void hideLoader();
    void onError(CharSequence s);
    void goToDashboard(Object obj);
    void closeGoogleConnection();
    void closeFacebookConection();
}
