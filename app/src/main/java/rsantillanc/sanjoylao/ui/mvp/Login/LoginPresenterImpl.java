package rsantillanc.sanjoylao.ui.mvp.Login;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public class LoginPresenterImpl implements ILoginPresenter{

    private ILoginView mLoginView;
    private ILoginIteractor mLoginIteractor;

    public LoginPresenterImpl(ILoginView loginView) {
        this.mLoginView = loginView;
        this.mLoginIteractor = new LoginIteractorImpl();
    }



//-------------------- Callbacks --------------------
    @Override
    public void connectWithFacebook() {
        mLoginView.showMessage("Login facebook correct!");
    }

    @Override
    public void connectWithGoogle() {
        mLoginView.showMessage("Login google correct!");
        mLoginView.goToDashboard();
    }
}
