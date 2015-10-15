package rsantillanc.sanjoylao.ui.mvp.Login;

import android.os.Handler;

import rsantillanc.sanjoylao.interfaces.OnLoginListener;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public class LoginIteractorImpl implements ILoginIteractor {

    @Override
    public void loginUsingFacebook(final OnLoginListener listener) {
        // Mock login. I'm creating a handler to delay the answer a couple of seconds
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                listener.onSuccessFacebook("Correct facebook!");
            }
        }, 2000);
    }

    @Override
    public void loginUsingGoogle(final OnLoginListener listener) {
// Mock login. I'm creating a handler to delay the answer a couple of seconds
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                listener.onSuccessGoogle("Correct google!");
            }
        }, 2500);
    }
}
