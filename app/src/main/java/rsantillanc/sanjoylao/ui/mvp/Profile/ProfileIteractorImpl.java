package rsantillanc.sanjoylao.ui.mvp.Profile;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rsantillanc.sanjoylao.api.service.ParseAPIService;
import rsantillanc.sanjoylao.interfaces.OnSaveListener;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.storage.dao.UserDao;
import rsantillanc.sanjoylao.ui.activity.ProfileActivity;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.ConstAPI;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public class ProfileIteractorImpl implements IProfileIteractor {
    private ProfileActivity currentActivity;

    public ProfileIteractorImpl(ProfileActivity activity) {
        this.currentActivity = activity;
    }


    @Override
    public void saveIn(final UserModel userSerializable, OnSaveListener saveListener) {
        //Local
        boolean ok = new UserDao(currentActivity).update(userSerializable) > 0;
        if (ok) saveListener.onSaveSuccess();
        else saveListener.onSaveError("Error!");

        //Remote
        Retrofit rft = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<JsonObject> call = rft.create(ParseAPIService.class).updateUser(buildJsonUser(userSerializable), userSerializable.getObjectId(), userSerializable.getSessionToken());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                if (response.isSuccess() && response != null) {
                    try {
                        userSerializable.setUpdatedAt(response.body().get("updatedAt").getAsString());
                        int update = new UserDao(currentActivity).update(userSerializable);
                        Log.e(Const.DEBUG, "Updated on server and local: " + update);
                    } catch (Exception e) {
                        Log.e(Const.DEBUG, "Error parsing updatedAt");
                        e.printStackTrace();
                    }
                } else
                    Log.e(Const.DEBUG, "Error: " + response.message());

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(Const.DEBUG, "Error onFailure: " + t.getMessage());
            }
        });

    }

    private String buildJsonUser(UserModel user) {
        String s = new Gson().toJson(user);
        Log.e(Const.DEBUG, "User json: " + s);
        return s;
    }
}
