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

import java.util.List;

import rsantillanc.sanjoylao.R;
import rsantillanc.sanjoylao.model.UserSignInModel;
import rsantillanc.sanjoylao.storage.dao.UserDao;
import rsantillanc.sanjoylao.storage.sp.SJLPreferences;
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
    private SJLPreferences sp;


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
        mLoginView.showLoader();
        flagOauthGoogle = true;
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
        signin.setUsername(Plus.AccountApi.getAccountName(mGoogleApi));
        signin.setSocialLogin(Const.LOGIN_GOOGLE);
        signin.setEmail(Plus.AccountApi.getAccountName(mGoogleApi));

        if (person.hasImage()) {
            signin.setUrlProfileImage(person.getImage().getUrl());
            signin.setHaveProfileImage(true);
        }

        if (checkIfUserExist(signin.getEmail())) {
            mLoginView.goToDashboard(getUser(signin.getEmail()).get(0));
            enabledUser(signin.getEmail());
            closeOauthSession();
        } else {
            mLoginView.updateLoader(mActivity.getString(R.string.progress_message_starting));
            mLoginIteractor.basicAuthentication(signin.getUsername(), signin.getPassword(), this);
            mLoginIteractor.setSignInUserModel(signin);
        }

    }


    /**
     * Contruye el perfil desde los datos de facebook
     *
     * @param response datos de la respuesta.
     */
    private void buildUserProfile(JSONObject response) {
        try {
            UserSignInModel signin = new UserSignInModel();

            signin.setUsername(response.getString("email"));
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

            if (checkIfUserExist(signin.getEmail())) {
                mLoginView.goToDashboard(getUser(signin.getEmail()).get(0));
                enabledUser(signin.getEmail());
                closeOauthSession();
            } else {
                mLoginView.updateLoader(mActivity.getString(R.string.progress_message_starting));
                mLoginIteractor.basicAuthentication(signin.getUsername(), signin.getPassword(), this);
                mLoginIteractor.setSignInUserModel(signin);
            }

        } catch (JSONException e) {
            mLoginView.onError(e.getMessage());
            mLoginView.hideLoader();
            e.printStackTrace();
        }
    }


    /**
     * Cierra la sesión ya sea de google o de facebook
     */
    private void closeOauthSession() {
        if (flagOauthGoogle)
            mLoginView.closeGoogleConnection();
        else
            mLoginView.closeFacebookConection();
    }

    private boolean checkIfUserExist(String email) {
        return new UserDao(mActivity).checkUserByEmail(email);
    }

    private List<Object> getUser(String email) {
        return new UserDao(mActivity).getUserByEmail(email);
    }


    /**
     * Método que permite iniciar sesión rápidamente con el usuario activo
     * de la base de datos local (SQLite).
     */
    public void getActiveUser() {
        try {
            Object user = new UserDao(mActivity).getCurrentUser(Const.USER_ENABLED).get(0);
            if (user != null)
                mLoginView.goToDashboard(user);
        } catch (Exception ex) {
            Log.e(Const.DEBUG, "Error obteniendo usuarios activos", ex);
        }
    }

    public void enabledUser(String email){
        new UserDao(mActivity).login(email);
    }

//----------------- [ OnRegisterListener]

    @Override
    public void onRegisterSuccess(Object obj) {
        mLoginView.updateLoader(mActivity.getString(R.string.progress_message_starting));
        mLoginIteractor.doLogin(mActivity, ((UserSignInModel) obj).getUsername(), ((UserSignInModel) obj).getPassword(), this);
    }

    @Override
    public void onError(CharSequence sequence) {
        mLoginView.hideLoader();
        mLoginView.onError(sequence);
        closeOauthSession();
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
        closeOauthSession();
    }

    @Override
    public void onBasicAuthenticationSuccess(Object userLogued) {
        mLoginView.hideLoader();
        mLoginView.goToDashboard(userLogued);
        closeOauthSession();
    }

    @Override
    public void onBasicAuthenticationError(UserSignInModel signin) {
        if (signin != null) {
            mLoginView.updateLoader(mActivity.getString(R.string.progress_message_registering));
            mLoginIteractor.doSignin(signin, this);
        }else {
            mLoginView.onError("Error al procesar.");
        }
    }


}
