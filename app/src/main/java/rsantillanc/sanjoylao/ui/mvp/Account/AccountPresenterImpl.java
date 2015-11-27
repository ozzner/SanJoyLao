package rsantillanc.sanjoylao.ui.mvp.Account;

import android.app.Activity;

/**
 * Created by RenzoD on 26/11/2015.
 */
public class AccountPresenterImpl implements IAccountPresenter{
    private IAccountView view;
    private Activity activity;


    public AccountPresenterImpl(Activity activity, IAccountView view) {
        this.view = view;
        this.activity = activity;
    }


    @Override
    public void makeCalendar() {

    }
}
