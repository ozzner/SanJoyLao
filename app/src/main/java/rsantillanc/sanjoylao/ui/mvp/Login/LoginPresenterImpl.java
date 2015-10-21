package rsantillanc.sanjoylao.ui.mvp.Login;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.UserSignInModel;
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
        UserSignInModel signin = new UserSignInModel();

        signin.setFullName(person.getDisplayName());
        signin.setUsername(person.getName().getGivenName());
        signin.setSocialLogin(Const.LOGIN_GOOGLE);
        signin.setEmail(Plus.AccountApi.getAccountName(mGoogleApi));

        if (person.hasImage()) {
            signin.setUrlProfileImage(person.getImage().getUrl());
            signin.setHaveProfileImage(true);
        }
        mLoginIteractor.doSignin(signin, LoginPresenterImpl.this);
    }


    /**
     * Contruye el perfil desde los datos de facebook
     * @param response datos de la respuesta.
     */
    private void buildUserProfile(JSONObject response) {
        try {
            UserSignInModel signin = new UserSignInModel();

            signin.setUsername(response.getString("name"));
            signin.setEmail(response.getString("email"));
            signin.setFullName(response.getString("name"));
            signin.setSocialLogin(Const.LOGIN_FACEBOOK);

            JSONObject pictureObject = response.getJSONObject("picture");
            JSONObject pictureData = pictureObject.getJSONObject("data");
            String url = pictureData.getString("url");

            if (!url.equals(Const.EMPTY)) {
                signin.setUrlProfileImage(url);
                signin.setHaveProfileImage(true);
            }
            mLoginIteractor.doSignin(signin, this);

        } catch (JSONException e) {
            mLoginView.onError(e.getMessage());
            mLoginView.hideLoader();
            e.printStackTrace();
        }
    }


    /**
     * Cierra la sesión ya sea de google o de facebook
     */
    private void closeOauthSession(){
        if (flagOauthGoogle)
            mLoginView.closeGoogleConnection();
        else
            mLoginView.closeFacebookConection();
    }


//----------------- [ OnRegisterListener]

    @Override
    public void onRegisterSuccess(Object obj) {
        mLoginView.updateLoader(mActivity.getString(R.string.progress_message_connecting));
        mLoginIteractor.doLogin(mActivity,obj, this);
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
        closeOauthSession();
    }

    @Override
    public void onLoginError(CharSequence message) {
        mLoginView.hideLoader();
        mLoginView.onError(message);
    }
}
