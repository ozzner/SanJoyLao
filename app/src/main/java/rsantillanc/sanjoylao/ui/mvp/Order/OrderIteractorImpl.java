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
import rsantillanc.sanjoylao.model.APIRequestOrderDetailModel;
import rsantillanc.sanjoylao.model.APIRequestOrderModel;
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
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(500);
////                    listener.onOrderCreated(new FeastModel().dummyBanquets());
//                } catch (InterruptedException e) {
//                    listener.onOrdersError(e.getMessage());
//                }
//            }
//        }).start();
    }


    @Override
    public void addItemToOrder(Context c, PlateSizeModel plateSize, UserModel user, OnOrdersListener listener) {
        if (checkIfExistActiveOrder(c)) {
            OrderModel currentOrder = new OrderDao(c).getActiveOrderIfExist(new StatusDao(c).getIdStatusByCode(Const.STATUS_TEMPORAL));
            addItemDetail(c, currentOrder.getObjectId(), plateSize, listener);
        } else
            createOrder(c, plateSize, user, listener);
    }


    private void createOrder(final Context c, final PlateSizeModel plateSize, final UserModel user, final OnOrdersListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIRequestOrderModel req = new APIRequestOrderModel();
        req.setIdUser(new ParsePointerModel(Const.CLASS_USER, user.getObjectId()));
        req.setIdStatus(makeStatusPointer(c));
        req.setPrice(plateSize.getPrice());

        Call<JsonObject> call = retrofit.create(ParseAPIService.class).createOrder(req);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    final JsonElement orderID = response.body().getAsJsonObject().get(ConstAPI.PARSE_ID);
                    long row = storeCurrentOrder(c, user, plateSize, response);
                    if (row > 0)
                        addItemDetail(c, orderID.getAsString(), plateSize, listener);
                    else
                        listener.onOrdersError(c,"No se creó la orden. Intente nuevamente.");
                } else{
                    listener.onOrdersError(c, "Error. No se pudo agregar.");
                    Log.e(Const.DEBUG, "onResponse Error. Detalle vacio.");

                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(Const.DEBUG, "Order Throwable: " + t.getMessage());
                listener.onOrdersError(c,t.getMessage());
            }
        });
    }

    private void addItemDetail(final Context c, final String orderID, final PlateSizeModel plateSize, final OnOrdersListener listener) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIRequestOrderDetailModel detailModel = new APIRequestOrderDetailModel();
        detailModel.setIdOrder(new ParsePointerModel(Const.CLASS_ORDER, orderID));
        detailModel.setIdPlateSize(new ParsePointerModel(Const.CLASS_PLATE_SIZE, plateSize.getObjectId()));

        Call<JsonObject> call = retrofit.create(ParseAPIService.class).createOrderDetail(detailModel);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    final JsonElement orderDetailID = response.body().getAsJsonObject().get(ConstAPI.PARSE_ID);
                    final JsonElement createdAt = response.body().getAsJsonObject().get(ConstAPI.PARSE_CREATED);

                    long row = createOrderDetailOnDevice(c, orderID,  orderDetailID.getAsString(), createdAt.getAsString(), plateSize);
                    if (row > 0)
                        listener.onLoadDetails(c, new OrderDao(c).getOrderDetailsByOrderID(orderID));
                    else
                        listener.onOrdersError(c,"Error, no se pudo agregar.");

                } else
                    Log.e(Const.DEBUG, "onResponse  OrderDetailModel created: " + response.errorBody());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(Const.DEBUG, "onFailure Throwable: " + t.getMessage());

            }
        });


    }

    private long createOrderDetailOnDevice(Context c, String orderID, String orderDetailID, String createdAt, PlateSizeModel plateSize) {
        return new OrderDao(c).insertDetail(orderID, orderDetailID, createdAt, plateSize);
    }

    private long storeCurrentOrder(Context c, UserModel user, PlateSizeModel plateSize, Response<JsonObject> response) {
        OrderModel order = new OrderModel();

        JsonElement objectId = response.body().getAsJsonObject().get(ConstAPI.PARSE_ID);
        JsonElement createdAt = response.body().getAsJsonObject().get(ConstAPI.PARSE_CREATED);

        order.setUser(user);
        order.setStatus(new StatusDao(c).getStatusByCode(Const.STATUS_TEMPORAL));
        order.setObjectId(objectId.getAsString());
        order.setCreatedAt(createdAt.getAsString());
        order.setUpdatedAt(createdAt.getAsString());
        order.setPrice(plateSize.getPrice());

        long insert = new OrderDao(c).insert(order);
        Log.e(Const.DEBUG, "Order Created Local: " + insert);

        return insert;
    }

    private ParsePointerModel makeStatusPointer(Context c) {
        String id = new StatusDao(c).getIdStatusByCode(Const.STATUS_TEMPORAL);
        return new ParsePointerModel(Const.CLASS_STATUS, id);
    }

    private boolean checkIfExistActiveOrder(Context c) {
        return new OrderDao(c).checkActiveOrder(new StatusDao(c).getIdStatusByCode(Const.STATUS_TEMPORAL));
    }
}
