package rsantillanc.sanjoylao.ui.mvp.Plate;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rsantillanc.sanjoylao.api.service.ParseAPIService;
import rsantillanc.sanjoylao.model.APIResultPlateModel;
import rsantillanc.sanjoylao.model.OrderDetailModel;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.model.RelationPlateSizeModel;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.storage.dao.PlateDao;
import rsantillanc.sanjoylao.storage.dao.PlateSizeDao;
import rsantillanc.sanjoylao.ui.mvp.Order.OnOrderListener;
import rsantillanc.sanjoylao.ui.mvp.Order.OrderIteractorImpl;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.ConstAPI;
import rsantillanc.sanjoylao.util.SJLStrings;

/**
 * Created by RenzoD on 29/10/2015.
 */
public class PlateIteractorImpl implements OnOrderListener {

    private final Activity mActivity;
    private final Context _context;
    PlateDao plateDao;

    public PlateIteractorImpl(Activity activity) {
        this.plateDao = new PlateDao(activity);
        this.mActivity = activity;
        _context = null;
    }

    public PlateIteractorImpl(Context c) {
        _context = c;
        mActivity = null;
    }

    public void findPlatesByCategory(String categoryID, final OnPlateListener listener) {
        /**
         * Hace falta valida que sucede si no hay data local
         */
        if (countPlates() == 0) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConstAPI.PARSE_URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Call<APIResultPlateModel> plates = retrofit.create(ParseAPIService.class).getPlatesWhereClausule(SJLStrings.getUrlEncoded(makeJson(categoryID)));
            plates.enqueue(new Callback<APIResultPlateModel>() {
                @Override
                public void onResponse(Response<APIResultPlateModel> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        long count = 0;
                        for (RelationPlateSizeModel relation : response.body().getResultArray()) {
                            count = storePlate(relation.getCurrentPlate());
                        }
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
            List<RelationPlateSizeModel> relations = new PlateDao(mActivity).listByCategory(categoryID);
            listener.onListFilterSuccess(relations);
        }

    }

    private long storePlate(PlateModel plate) {
        return new PlateDao(mActivity).insert(plate);
    }


    private int countPlates() {
        return new PlateDao(mActivity.getApplicationContext()).count();
    }



    private String makeJson(CharSequence categoryID) {
        String s = "{\"idCategory\":{\"__type\":\"Pointer\",\"className\":\"Category\",\"objectId\":\"" + categoryID + "\"}}";
        return s;
    }


    public void addPlate(Context c, PlateSizeModel plateSize, UserModel user) {
        OrderIteractorImpl iteractor = new OrderIteractorImpl();
        iteractor.addItemToOrder(c, plateSize, user, this);
    }


    public List<PlateSizeModel> getSizesByPlateID(String plateID) {
        return new PlateSizeDao(_context).listByPlateID(plateID);
    }


    @Override
    public void onLoadDetails(Context c, List<OrderDetailModel> orderDetails) {
        PlatePresenterImpl presenter = new PlatePresenterImpl();
        presenter.onPlateAddSucess(c,orderDetails.size());
    }

    @Override
    public void onCounterSuccess(Context c, CharSequence ok) {
        PlatePresenterImpl presenter = new PlatePresenterImpl();
        presenter.onPlateCounterSuccess(c,ok);
    }

    @Override
    public void onOrdersError(Context c, CharSequence error) {

    }

    @Override
    public void onDeleteSuccess(CharSequence message) {

    }
    @Override
    public void paymentCorrect(double amount) {

    }

    @Override
    public void orderCheckoutSuccess(CharSequence s) {

    }

    @Override
    public void errorUpdating(CharSequence error) {

    }
}
