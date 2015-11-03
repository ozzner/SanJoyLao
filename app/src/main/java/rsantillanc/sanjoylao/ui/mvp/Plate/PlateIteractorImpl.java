package rsantillanc.sanjoylao.ui.mvp.Plate;

import android.util.Log;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rsantillanc.sanjoylao.api.ConstAPI;
import rsantillanc.sanjoylao.api.ParseAPIService;
import rsantillanc.sanjoylao.model.APIResultPlateModel;
import rsantillanc.sanjoylao.util.Const;

/**
 * Created by RenzoD on 29/10/2015.
 */
public class PlateIteractorImpl {

    public void findPlatesByCategory(CharSequence jsonFilter) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<List<APIResultPlateModel>> plates = retrofit.create(ParseAPIService.class).getPlatesWhereClausule(jsonFilter);
        plates.enqueue(new Callback<List<APIResultPlateModel>>() {
            @Override
            public void onResponse(Response<List<APIResultPlateModel>> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    Log.e(Const.DEBUG,"onResponse SIZE: " + response.body().size());
                }else {

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(Const.DEBUG,"onFailure Throwable: " + t.getMessage());
            }
        });
    }
}
