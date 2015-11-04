package rsantillanc.sanjoylao.ui.mvp.Plate;

import android.app.Activity;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rsantillanc.sanjoylao.api.service.ParseAPIService;
import rsantillanc.sanjoylao.model.APIResultPlateModel;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.storage.dao.PlateDao;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.ConstAPI;

/**
 * Created by RenzoD on 29/10/2015.
 */
public class PlateIteractorImpl {

    private final Activity mActivity;
    PlateDao plateDao;

    public PlateIteractorImpl(Activity activity) {
        this.plateDao = new PlateDao(activity);
        this.mActivity = activity;
    }

    public void findPlatesByCategory(String categoryID, final OnPlateListener listener) {

        if (countPlates() == 0) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConstAPI.PARSE_URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Call<APIResultPlateModel> plates = retrofit.create(ParseAPIService.class).getPlatesWhereClausule(getUrlEncoded(makeJson(categoryID)));
            plates.enqueue(new Callback<APIResultPlateModel>() {
                @Override
                public void onResponse(Response<APIResultPlateModel> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        long count = storePlates(response.body().getResultArray());
                         listener.onListFilterSuccess(response.body().getResultArray());

                        Log.e(Const.DEBUG, "oonResponse plates stored: " + count);
                    } else {
                        Log.e(Const.DEBUG, "oonResponse Error: " + response.message());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(Const.DEBUG, "onFailure Throwable: " + t.getMessage());
                }
            });

        } else {
            List<PlateModel> platesFilter = new PlateDao(mActivity).listByCategory(categoryID);
            listener.onListFilterSuccess(platesFilter);
        }

    }

    private long storePlates(ArrayList<PlateModel> resultArray) {
        long rows = 0;
        for (PlateModel plate : resultArray) {
            rows = new PlateDao(mActivity).insert(plate);
        }
        return rows;
    }


    private int countPlates() {
        return new PlateDao(mActivity.getApplicationContext()).count();
    }

    private void makePlateWithPrice() {

    }

    private String makeJson(CharSequence categoryID) {
        String s = "{\"idCategory\":{\"__type\":\"Pointer\",\"className\":\"Category\",\"objectId\":\"" + categoryID + "\"}}";
        return s;
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
