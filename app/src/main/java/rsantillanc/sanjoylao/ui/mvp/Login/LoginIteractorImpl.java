package rsantillanc.sanjoylao.ui.mvp.Login;

import android.util.Log;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rsantillanc.sanjoylao.api.ConstAPI;
import rsantillanc.sanjoylao.api.ParseAPIService;
import rsantillanc.sanjoylao.model.BasicAutentication;
import rsantillanc.sanjoylao.model.UserModel;

/**
 * Created by rsantillanc on 14/10/2015.
 */
public class LoginIteractorImpl implements ILoginIteractor {

    @Override
    public void registerUserOnBackend(final UserModel oUser, final OnLoginListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<UserModel> call = retrofit.create(ParseAPIService.class).signingUp(new BasicAutentication(oUser.getFullName(),oUser.getEmail()));
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Response<UserModel> response, Retrofit retrofit) {
                Log.e("onResponse Retrofit ", "response message" + response.message());
                Log.e("onResponse Retrofit ", "response message" + response.body());
                Log.e("onResponse Retrofit ", "retrofit " + retrofit.toString());
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("onFailureRetrofit ", "Throwable " + t.getMessage());
                listener.onError(t.getMessage());

            }
        });
    }

}
