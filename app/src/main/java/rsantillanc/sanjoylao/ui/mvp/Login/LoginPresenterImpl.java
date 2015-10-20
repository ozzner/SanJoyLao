package rsantillanc.sanjoylao.ui.mvp.Login;

import android.os.Bundle;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public class LoginPresenterImpl implements ILoginPresenter, OnLoginListener {

    private static final String TAG = LoginPresenterImpl.class.getSimpleName() + Const.BLANK_SPACE;
    private ILoginView mLoginView;
    private ILoginIteractor mLoginIteractor;
    private GoogleApiClient mGoogleApi;
    private boolean flagOauthGoogle;

    public LoginPresenterImpl(ILoginView loginView) {
        this.mLoginView = loginView;
        this.mLoginIteractor = new LoginIteractorImpl();
    }


    @Override
    public void startRequestWithFacebookData(LoginResult loginResult) {
        flagOauthGoogle = false;
        mLoginView.showLoader();
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        buildUserProfile(object);
                    }
                });

        //Make params
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }


    @Override
    public void startRequestWithGoogleData(Person person, GoogleApiClient mGoogleApi) {
        flagOauthGoogle = true;
        mLoginView.showLoader();
        this.mGoogleApi = mGoogleApi;
        buildUserProfile(person);
    }



    @Override
    public void onSuccess(Object obj) {
        mLoginView.hideLoader();
        mLoginView.goToDashboard(obj);
        if (flagOauthGoogle)
            mLoginView.closeGoogleConnection();
        else
            mLoginView.closeFacebookConection();

    }

    @Override
    public void onError(CharSequence sequence) {
        mLoginView.hideLoader();
        mLoginView.onError(sequence);
    }

    /**
     * Build profile from google data.
     *
     * @param person Object google
     */
    private void buildUserProfile(Person person) {
        UserModel oUser = new UserModel();

        if (person.hasImage()) {
            oUser.setUrlProfileImage(person.getImage().getUrl());
//            oUser.setProfileImage(person.getImage());
            oUser.setHaveProfileImage(true);
        }
        oUser.setFullName(person.getDisplayName());
        oUser.setEmail(Plus.AccountApi.getAccountName(mGoogleApi));
        mLoginIteractor.registerUserOnBackend(oUser, LoginPresenterImpl.this);

//        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person getDisplayName " + person.getDisplayName());
//        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person getUrl " + person.getUrl());
//        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person Gender " + person.getGender());
//        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person Name " + person.getName());
//        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person Birthday " + person.getBirthday());
//        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person getAboutMe " + person.getAboutMe());
//        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "Person hasTagline " + person.hasTagline());
//        Log.d(Const.DEBUG_GOOGLE_PLUS, TAG + "AccountName email " + Plus.AccountApi.getAccountName(mGoogleApi));

//        mLoginView.closeGoogleConnection();
//        mLoginView.goToDashboard();
    }

    private void buildUserProfile(JSONObject response) {
        try {
            UserModel user = new UserModel();

            user.setFullName(response.getString("name"));
            user.setEmail(response.getString("email"));
            user.setObjectId(response.getString("id"));

            JSONObject pictureObject = response.getJSONObject("picture");
            JSONObject pictureData = pictureObject.getJSONObject("data");
            String url = pictureData.getString("url");
            if (url.equals(Const.EMPTY)) {
                user.setUrlProfileImage(url);
                user.setHaveProfileImage(true);
                user.setProfileImage(null);
            }

            user.setIsEnabled(true);
            user.setSocialLogin(Const.LOGIN_FACEBOOK);
            mLoginIteractor.registerUserOnBackend(user, this);
//            mLoginView.closeGoogleConnection();
//            mLoginView.goToDashboard(bundle);

        } catch (JSONException e) {
            mLoginView.onError(e.getMessage());
            mLoginView.hideLoader();
            e.printStackTrace();
        }
    }

}
