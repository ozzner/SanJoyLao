package rsantillanc.sanjoylao.ui.mvp.Login;

import rsantillanc.sanjoylao.interfaces.OnLoginListener;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public class LoginPresenterImpl implements ILoginPresenter, OnLoginListener {

    private ILoginView mLoginView;
    private ILoginIteractor mLoginIteractor;

    public LoginPresenterImpl(ILoginView loginView) {
        this.mLoginView = loginView;
        this.mLoginIteractor = new LoginIteractorImpl();
    }


    //-------------------- Callbacks --------------------
    @Override
    public void connectWithFacebook() {
        mLoginView.showLoader();
        mLoginIteractor.loginUsingFacebook(this);
    }

    @Override
    public void connectWithGoogle() {
        mLoginView.showLoader();
        mLoginIteractor.loginUsingGoogle(this);
    }

    @Override
    public void onFacebookFailed(Object obj) {
        mLoginView.hideLoader();
        mLoginView.onError(obj.toString());
    }

    @Override
    public void onGoogleFailed(Object obj) {
        mLoginView.hideLoader();
        mLoginView.onError(obj.toString());
    }

    @Override
    public void onSuccessFacebook(Object obj) {
        mLoginView.hideLoader();
        mLoginView.goToDashboard();
    }

    @Override
    public void onSuccessGoogle(Object obj) {
        mLoginView.hideLoader();
        mLoginView.goToDashboard();
    }
}
