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
import rsantillanc.sanjoylao.model.APIRequestSignInModel;
import rsantillanc.sanjoylao.storage.dao.UserDao;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public class LoginPresenterImpl implements ILoginPresenter, OnRegisterListener, OnLoginListener {

    private static final String TAG = LoginPresenterImpl.class.getSimpleName() + Const.BLANK_SPACE;
    private static final int SIZE = 200;
    private ILoginView mLoginView;
    private ILoginIteractor mLoginIteractor;
    private GoogleApiClient mGoogleApi;
    private boolean flagOauthGoogle;
    private Activity mActivity;



    public LoginPresenterImpl(ILoginView loginView, Activity activity) {
        this.mLoginView = loginView;
        this.mActivity = activity;
        this.mLoginIteractor = new LoginIteractorImpl();
        this.mLoginIteractor.sendContext(mActivity.getApplicationContext());
    }


    /**
     * Método que es llamado luego de ser exitoso el acceso a traves de la API de facebook.
     * @param loginResult Objecto propio de Facebook que contiene el Acceso para RESTFull
     */
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

    /**
     * Metodo que se manda a llamar cuando el proceso de google+ ha sido exitoso.
     * @param person Objecto google plus que contiene los datos de el usuario
     * @param mGoogleApi Cliente para iniciar o cerrar la conexion en google. (Cliente)
     */
    @Override
    public void startRequestWithGoogleData(Person person, GoogleApiClient mGoogleApi) {
        mLoginView.showLoader();
        flagOauthGoogle = true;
        this.mGoogleApi = mGoogleApi;
        buildUserProfile(person);
    }


    /**
     * Build profile from google data.
     * @param person Object google
     */
    private void buildUserProfile(Person person) {
        APIRequestSignInModel signin = new APIRequestSignInModel();

        signin.setFullName(person.getDisplayName());
        signin.setUsername(Plus.AccountApi.getAccountName(mGoogleApi));
        signin.setSocialLogin(Const.LOGIN_GOOGLE);
        signin.setEmail(Plus.AccountApi.getAccountName(mGoogleApi));

        if (person.hasImage()) {
            signin.setUrlProfileImage(customSizeURLGooglePlus(person.getImage().getUrl()));
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
     * @param response datos de la respuesta.
     */
    private void buildUserProfile(JSONObject response) {
        try {
            APIRequestSignInModel signin = new APIRequestSignInModel();

            signin.setUsername(response.getString("email"));
            signin.setEmail(response.getString("email"));
            signin.setFullName(response.getString("name"));
            signin.setSocialLogin(Const.LOGIN_FACEBOOK);

            JSONObject pictureObject = response.getJSONObject("picture");
            JSONObject pictureData = pictureObject.getJSONObject("data");
            String url = pictureData.getString("url");

            if (!url.equals(Const.EMPTY)) {
                signin.setUrlProfileImage(customSizeURLFacebook(response.getString("id")));
                signin.setHaveProfileImage(true);
            }

            if (checkIfUserExist(signin.getEmail())) {
                mLoginView.goToDashboard(getUser(signin.getEmail()).get(0));
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
     * Por defecto el tamaño de la imagen es de 50x50, este metodo permite
     * obtener una imagen con un tamaño typo=large
     * @param userID id que te otorga facebook y lo concatenas con graph de facebook.
     * @return String con imagen a tamaño deseado.
     */
    private String customSizeURLFacebook(String userID) {
        return "https://graph.facebook.com/" + userID + "/picture?type=large";
    }

    /**
     * Por defecto el tamaño de la imagen es de 50x50, este metodo permite
     * obtener una imagen con un tamaño deseado.
     * @param url String que contiene la dirección de la imagen.
     * @return String con imagen a tamaño deseado.
     */
    private String customSizeURLGooglePlus(String url) {
        return url.substring(0,url.length()-2) + SIZE;
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

    /**
     * Método que sirve para verificar la existencia de un contacto a traves de su email.
     * @param email String para hacer filtro
     * @return Booleano, si es true existe.
     */
    private boolean checkIfUserExist(String email) {
        return new UserDao(mActivity).checkUserByEmail(email);
    }


    /**
     * Obtiene un usuario por su email.
     * @param email String que sirva como clave de busqueda.
     * @return Lista de usuarios (Solamente un usuario)
     */
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

    /**
     * Método que sirve para activar el flas isEnabled para un próximo inicio de sesión rápido
     * @param email
     */
    public void enabledUser(String email){
        new UserDao(mActivity).login(email);
    }




    //----------------- [OnRegisterListener]


    @Override
    public void onRegisterSuccess(Object obj) {
        mLoginView.updateLoader(mActivity.getString(R.string.progress_message_starting));
        mLoginIteractor.doLogin(mActivity, ((APIRequestSignInModel) obj).getUsername(), ((APIRequestSignInModel) obj).getPassword(), this);
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
    public void onBasicAuthenticationError(APIRequestSignInModel signin) {
        if (signin != null) {
            mLoginView.updateLoader(mActivity.getString(R.string.progress_message_registering));
            mLoginIteractor.doSignin(signin, this);
        }else {
            mLoginView.onError("Error al procesar.");
        }
    }


}
