package rsantillanc.sanjoylao.ui.mvp.Login;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rsantillanc.sanjoylao.api.ConstAPI;
import rsantillanc.sanjoylao.api.ParseAPIService;
import rsantillanc.sanjoylao.model.APISignInModel;
import rsantillanc.sanjoylao.model.APIUserCreatedModel;
import rsantillanc.sanjoylao.model.UserModel;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public class LoginIteractorImpl implements ILoginIteractor {

    @Override
    public void doSignin(final UserModel oUser, final OnRegisterListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Call<APIUserCreatedModel> call = retrofit.create(ParseAPIService.class).signUp(new APISignInModel("prueba3","",2,"abc@abc.com"));
        call.enqueue(new Callback<APIUserCreatedModel>() {
            @Override
            public void onResponse(Response<APIUserCreatedModel> response, Retrofit retrofit) {
                if (response.body() != null) {
                    oUser.setObjectId(response.body().getObjectId());
                    oUser.setCreatedAt(response.body().getCreatedAt());
                    oUser.setUpdatedAt(response.body().getCreatedAt());
                    oUser.setSessionToken(response.body().getSessionToken());
                    listener.onRegisterSuccess(oUser);
                }else
                    listener.onError("Error vuelva a intentarlo.");
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
    public void doLogin(Object obj, final OnLoginListener listener) {
        Retrofit login = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserModel userBuided = ((UserModel) obj);
        Call<UserModel> call = login.create(ParseAPIService.class).login(userBuided.getUsername(), userBuided.getPassword());
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Response<UserModel> currentUser, Retrofit retrofit) {
                if (currentUser.body() != null)
                    listener.onLoginSuccess(currentUser);

            }

            @Override
            public void onFailure(Throwable t) {
                listener.onLoginError(t.getMessage());
            }
        });


    }

}
