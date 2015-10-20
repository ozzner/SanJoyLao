package rsantillanc.sanjoylao.ui.mvp.Login;

import android.os.Bundle;
import android.util.Log;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import rsantillanc.sanjoylao.interfaces.OnLoginListener;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public class LoginPresenterImpl implements ILoginPresenter, OnLoginListener {

    private static final String TAG = LoginPresenterImpl.class.getSimpleName() + Const.BLANK_SPACE;
    private ILoginView mLoginView;
    private ILoginIteractor mLoginIteractor;
    private GoogleApiClient mGoogleApi;

    public LoginPresenterImpl(ILoginView loginView) {
        this.mLoginView = loginView;
        this.mLoginIteractor = new LoginIteractorImpl();
    }


    @Override
    public void startConnectWithFacebook() {
        mLoginView.showLoader();
        mLoginIteractor.loginUsingFacebook(this);
    }

    @Override
    public void startConnectWithGoogle() {
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
        mLoginView.onError((obj == null) ? "Es nulo" : "Error desconocido");
    }

    @Override
    public void onSuccessFacebook(Object obj) {
        LoginResult loginResult = ((LoginResult) obj);

        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        buildUserProfile(object);
                        mLoginIteractor.loginUsingFacebook(LoginPresenterImpl.this);
                        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "JSONObject: " + object.toString());

                    }
                });

        //Make params
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void buildUserProfile(Person person) {

        if (person.hasImage()) {
            Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person Image getImageUrl" + person.getImage().getUrl());
        }

        Bundle bundle = new Bundle();
        bundle.putCharSequence("name", person.getDisplayName());
        bundle.putCharSequence("email",Plus.AccountApi.getAccountName(mGoogleApi));
        mLoginView.goToDashboard(bundle);

        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person getDisplayName " + person.getDisplayName());
        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person getUrl " + person.getUrl());
        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person Gender " + person.getGender());
        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person Name " + person.getName());
        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person Birthday " + person.getBirthday());
        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person getAboutMe " + person.getAboutMe());
        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person hasTagline " + person.hasTagline());
        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "AccountName email " + Plus.AccountApi.getAccountName(mGoogleApi));

    }

    private void buildUserProfile(JSONObject object) {
        try {
            String name = object.getString("name").toString();
            String email = object.getString("email").toString();
            Bundle bundle = new Bundle();
            bundle.putCharSequence("name",name);
            bundle.putCharSequence("email",email);
            mLoginView.goToDashboard(bundle);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessGoogle(Person person, GoogleApiClient mGoogleApi) {
        this.mGoogleApi = mGoogleApi;
        buildUserProfile(person);
        mLoginView.hideLoader();
    }
}
