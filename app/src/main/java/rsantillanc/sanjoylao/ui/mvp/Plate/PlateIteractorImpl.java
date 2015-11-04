package rsantillanc.sanjoylao.ui.mvp.Plate;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rsantillanc.sanjoylao.api.service.ParseAPIService;
import rsantillanc.sanjoylao.model.APIResultPlateModel;
import rsantillanc.sanjoylao.storage.dao.PlateDao;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.ConstAPI;

/**
 * Created by RenzoD on 29/10/2015.
 */
public class PlateIteractorImpl {

    PlateDao plateDao;

    public void findPlatesByCategory(String jsonFilter) {
        plateDao = new PlateDao();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<APIResultPlateModel> plates = retrofit.create(ParseAPIService.class).getPlatesWhereClausule(getUrlEncoded(jsonFilter));
        plates.enqueue(new Callback<APIResultPlateModel>() {
            @Override
            public void onResponse(Response<APIResultPlateModel> response, Retrofit retrofit) {
                if (response.isSuccess()) {
//                    makePlateWithPrice(response.body().);


                } else {

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(Const.DEBUG, "onFailure Throwable: " + t.getMessage());
            }
        });
    }

    private void makePlateWithPrice() {

    }

    private String getUrlEncoded(String jsonFilter) {
        String encodedData = null;
        try {
            encodedData = URLEncoder.encode(jsonFilter, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedData;
    }
}
