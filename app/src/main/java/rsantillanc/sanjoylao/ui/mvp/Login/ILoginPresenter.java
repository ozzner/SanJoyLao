package rsantillanc.sanjoylao.ui.mvp.Login;

import com.facebook.login.LoginResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.model.people.Person;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public interface ILoginPresenter {
    void startRequestWithFacebookData(LoginResult loginResult);
    void startRequestWithGoogleData(Person person, GoogleApiClient mGoogleApi);

}
