package rsantillanc.sanjoylao.interfaces;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.model.people.Person;

/**
 * Created by RenzoD on 14/10/2015.
 */
public interface OnLoginListener {
    void onFacebookFailed(Object obj);
    void onGoogleFailed(Object obj);
    void onSuccessFacebook(Object obj);
    void onSuccessGoogle(Person obj, GoogleApiClient mGoogleApi);
}
