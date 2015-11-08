package rsantillanc.sanjoylao.ui.mvp.Order;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rsantillanc.sanjoylao.api.service.ParseAPIService;
import rsantillanc.sanjoylao.model.APIRequestOrderModel;
import rsantillanc.sanjoylao.model.BanquetModel;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.model.ParsePointerModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.storage.dao.OrderDao;
import rsantillanc.sanjoylao.storage.dao.StatusDao;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.ConstAPI;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public class OrderIteractorImpl implements IOrderIteractor {


    @Override
    public void getOrdersFromServer(String clausule, final OnOrdersListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    listener.onOrdersSuccess(new BanquetModel().dummyBanquets());
                } catch (InterruptedException e) {
                    listener.onOrdersError(e.getMessage());
                }
            }
        }).start();
    }


    @Override
    public void addItemToOrder(Context c,PlateSizeModel plateSize, UserModel user) {
        if (checkIfExistActiveOrder(c)) {
            Log.e(Const.DEBUG,"Order Exist!");
        }else
            createOrder(c,plateSize,user);
    }

    private void createOrder(final Context c, PlateSizeModel plateSize, final UserModel user) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIRequestOrderModel req = new APIRequestOrderModel();
        req.setIdUser(new ParsePointerModel(Const.CLASS_USER,user.getObjectId()));
        req.setIdStatus(makeStatusPointer(c));

        Call<JsonObject> call = retrofit.create(ParseAPIService.class).createOrder(req);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                if (response.isSuccess())
                {
                    storeCurrentOrder(c ,user,response);
                }
                else
                    Log.e(Const.DEBUG, "Order error: " + response.errorBody());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(Const.DEBUG, "Order Throwable: " + t.getMessage());

            }
        });
    }

    private void storeCurrentOrder(Context c, UserModel user, Response<JsonObject> response) {
        OrderModel order = new OrderModel();

        JsonElement objectId = response.body().getAsJsonObject().get(ConstAPI.PARSE_ID);
        JsonElement createdAt = response.body().getAsJsonObject().get(ConstAPI.PARSE_CREATED);

        order.setUser(user);
        order.setStatus(new StatusDao(c).getStatusByCode(Const.STATUS_TEMPORAL));
        order.setObjectId(objectId.getAsString());
        order.setCreatedAt(createdAt.getAsString());
        order.setUpdatedAt(createdAt.getAsString());

        long insert = new OrderDao(c).insert(order);
        Log.e(Const.DEBUG,"Order Created Local: " + insert);
    }

    private ParsePointerModel makeStatusPointer(Context c) {
        String id = new StatusDao(c).getIdStatusByCode(Const.STATUS_TEMPORAL);
        return new ParsePointerModel(Const.CLASS_STATUS,id);
    }

    private boolean checkIfExistActiveOrder(Context c) {
        return new OrderDao(c).checkActiveOrder(new StatusDao(c).getIdStatusByCode(Const.STATUS_TEMPORAL));
    }
}
