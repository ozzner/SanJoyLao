package rsantillanc.sanjoylao.ui.mvp.Login;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rsantillanc.sanjoylao.api.ConstAPI;
import rsantillanc.sanjoylao.api.ParseAPIService;
import rsantillanc.sanjoylao.model.UserCreatedModel;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.model.UserSignInModel;
import rsantillanc.sanjoylao.storage.dao.UserDao;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.SJLStrings;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public class LoginIteractorImpl implements ILoginIteractor {

    /**
     * Metodo para registrar un usuario en el backend.
     * @param signin Objeto modelo que contiene los datos.
     * @param listener Para recibir los eventos de error o éxito.
     */
    @Override
    public void doSignin(final UserSignInModel signin, final OnRegisterListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<UserCreatedModel> call = retrofit.create(ParseAPIService.class).signUp(signin);
        call.enqueue(new Callback<UserCreatedModel>() {

            @Override
            public void onResponse(Response<UserCreatedModel> response, Retrofit retrofit) {
                if (response.body() != null) {
                 listener.onRegisterSuccess(signin);
                }else
                    listener.onError("No se pudo registrar.");

            }

            @Override
            public void onFailure(Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }


    /**
     * Método para loguerse en el servidor
     * @param obj usuario a loguearse
     * @param listener eventos en caso sea exitoso o haya algún error.
     */
    @Override
    public void doLogin(final Context context,Object obj, final OnLoginListener listener) {
        Gson custom = new GsonBuilder()
                .setDateFormat(SJLStrings.MAIN_DATE_FORMAT)
                .create();

        Retrofit login = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(custom))
                .build();

        UserSignInModel userBuided = ((UserSignInModel) obj);
        Call<UserModel> call = login.create(ParseAPIService.class).login(userBuided.getUsername(), userBuided.getPassword());
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Response<UserModel> response, Retrofit retrofit) {
                if (response.body() != null){
                    listener.onLoginSuccess(response.body());
                    //Save local
                    long rows = new UserDao(context).saveUser(response.body());
                    Log.e(Const.DEBUG,"row: " + rows);
                }
                else
                    listener.onLoginError("No pudo iniciar sesión.");
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onLoginError(t.getMessage());
            }
        });


    }

}
