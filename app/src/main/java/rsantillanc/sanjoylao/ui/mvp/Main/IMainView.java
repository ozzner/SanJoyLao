package rsantillanc.sanjoylao.ui.mvp.Main;

/**
 * Created by rsantillanc on 20/10/2015.
 */
public interface IMainView {
    void goToLogin();
    void goToProfile();
    void goToOrders();
    void showLoader();
    void hideLoader();
    void uptateTitle(CharSequence title);
    void closeMenu();
    void openMenu();
}
