package rsantillanc.sanjoylao.ui.mvp.Profile;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public interface IProfileView {
    void showLoader();
    void hideLoader();
    void valitateFields();
    void enabledEdition();
    void disabledEdition();
    void statusOnline();
    void statusOffline();
    void updateUserProfile();
}
