package rsantillanc.sanjoylao.ui.mvp.Login;

import android.app.Activity;
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

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public class LoginPresenterImpl implements ILoginPresenter, OnRegisterListener, OnLoginListener {

    private static final String TAG = LoginPresenterImpl.class.getSimpleName() + Const.BLANK_SPACE;
    private ILoginView mLoginView;
    private ILoginIteractor mLoginIteractor;
    private GoogleApiClient mGoogleApi;
    private boolean flagOauthGoogle;
    private Activity mActivity;

    public LoginPresenterImpl(ILoginView loginView, Activity activity) {
        this.mLoginView = loginView;
        this.mActivity = activity;
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



    /**
     * Build profile from google data.
     *
     * @param person Object google
     */
    private void buildUserProfile(Person person) {
        UserModel oUser = new UserModel();

        if (person.hasImage()) {
            oUser.setUrlProfileImage(person.getImage().getUrl());
            oUser.setHaveProfileImage(true);
        }
        oUser.setFullName(person.getDisplayName());
        oUser.setUsername(person.getDisplayName());
        oUser.setPassword(person.getId());
        oUser.setSocialLogin(Const.LOGIN_GOOGLE);
        oUser.setEmail(Plus.AccountApi.getAccountName(mGoogleApi));
        oUser.setIsEnabled(true);

        Log.e("CURRENT_USER", oUser.toString());
        mLoginIteractor.doSignin(oUser, LoginPresenterImpl.this);
    }

    private void buildUserProfile(JSONObject response) {
        try {
            UserModel user = new UserModel();

            user.setPassword(response.getString("id"));
            user.setUsername(response.getString("name"));
            user.setEmail(response.getString("email"));
            user.setObjectId(response.getString("id"));
            user.setFullName(response.getString("name"));

            JSONObject pictureObject = response.getJSONObject("picture");
            JSONObject pictureData = pictureObject.getJSONObject("data");
            String url = pictureData.getString("url");
            if (url.equals(Const.EMPTY)) {
                user.setUrlProfileImage(url);
                user.setHaveProfileImage(true);
            }

            user.setIsEnabled(true);
            user.setSocialLogin(Const.LOGIN_FACEBOOK);
            Log.e("CURRENT_USER",user.toString());
            mLoginIteractor.doSignin(user, this);

        } catch (JSONException e) {
            mLoginView.onError(e.getMessage());
            mLoginView.hideLoader();
            e.printStackTrace();
        }
    }



//----------------- [ OnRegisterListener]

    @Override
    public void onRegisterSuccess(Object obj) {
        mLoginView.updateLoader(mActivity.getString(R.string.progress_message_connecting));
        mLoginIteractor.doLogin(obj, this);
    }

    @Override
    public void onError(CharSequence sequence) {
        mLoginView.hideLoader();
        mLoginView.onError(sequence);
    }




//----------------- [ OnLoginListener]
    @Override
    public void onLoginSuccess(Object currentUser) {
        mLoginView.hideLoader();
        mLoginView.goToDashboard(currentUser);
        if (flagOauthGoogle)
            mLoginView.closeGoogleConnection();
        else
            mLoginView.closeFacebookConection();
    }

    @Override
    public void onLoginError(CharSequence message) {
        mLoginView.hideLoader();
        mLoginView.onError(message);
    }
}
