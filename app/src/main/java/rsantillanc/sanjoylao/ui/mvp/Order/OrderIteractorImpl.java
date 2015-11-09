package rsantillanc.sanjoylao.ui.mvp.Order;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Converter;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rsantillanc.sanjoylao.api.deserializer.ParseAPIDeserializer;
import rsantillanc.sanjoylao.api.service.ParseAPIService;
import rsantillanc.sanjoylao.model.APIRequestOrderDetailModel;
import rsantillanc.sanjoylao.model.APIRequestOrderModel;
import rsantillanc.sanjoylao.model.OrderDetailModel;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.model.ParsePointerModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.storage.dao.OrderDao;
import rsantillanc.sanjoylao.storage.dao.StatusDao;
import rsantillanc.sanjoylao.util.Const;
import rsantillanc.sanjoylao.util.ConstAPI;
import rsantillanc.sanjoylao.util.SJLStrings;

/**
 * Created by rsantillanc on 27/10/2015.
 */
public class OrderIteractorImpl implements IOrderIteractor {


    @Override
    public void getOrdersFromServer(String orderID, final OnOrdersListener listener, final Context c) {
        Retrofit fit = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(customConverter(OrderDetailModel.class))
                .build();

        Call<OrderDetailModel> call = fit.create(ParseAPIService.class).getOrdersDetails(SJLStrings.getUrlEncoded(makeJsonFilter(orderID)));
        call.enqueue(new Callback<OrderDetailModel>() {
            @Override
            public void onResponse(Response<OrderDetailModel> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    List<OrderDetailModel> list = new ArrayList();
                    list.addAll((Collection<? extends OrderDetailModel>) response.body());
                    listener.onLoadDetails(c,list);
                    Log.e(Const.DEBUG,"onResponse RESULT ordersDetail: " + list.size());
                }else{
                    Log.e(Const.DEBUG,"onResponse RESULT ordersDetail: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(Const.DEBUG,"onFailure Throwable: ordersDetail" + t.getMessage());
            }
        });
    }

    private String makeJsonFilter(String orderID) {
        String s = "{\"idOrder\":{\"__type\":\"Pointer\",\"className\":\"Order\",\"objectId\":\"" + orderID + "\"}}";
        return s;
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
                        listener.onOrdersError(c, "No se creó la orden. Intente nuevamente.");
                } else {
                    listener.onOrdersError(c, "Error. No se pudo agregar.");
                    Log.e(Const.DEBUG, "onResponse Error. Detalle vacio.");

                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(Const.DEBUG, "Order Throwable: " + t.getMessage());
                listener.onOrdersError(c, t.getMessage());
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

                    long row = createOrderDetailOnDevice(c, orderID, orderDetailID.getAsString(), createdAt.getAsString(), plateSize);
                    Log.e(Const.DEBUG, "onResponse onCreated correct: " + row);

                    if (row > 0)
                        listener.onLoadDetails(c, new OrderDao(c).getOrderDetailsByOrderID(orderID));
                    else
                        listener.onOrdersError(c, "Error, no se pudo agregar.");

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


    public void getOrdersFrom(Context c, OnOrdersListener listener) {

        //From device
        if (checkIfExistActiveOrder(c)) {
            listener.onLoadDetails(c,
                    new OrderDao(c).getOrderDetailsByOrderID(
                            new OrderDao(c).getActiveOrderIfExist(
                                    new StatusDao(c).getIdStatusByCode(Const.STATUS_TEMPORAL))
                                    .getObjectId()));
        //From server
        }else
           getOrdersFromServer(
                   new OrderDao(c).getActiveOrderIfExist(
                           new StatusDao(c).getIdStatusByCode(Const.STATUS_TEMPORAL))
                           .getObjectId(), listener, c);

    }

    public Converter.Factory customConverter(Type type) {

        Gson build = new GsonBuilder()
                .registerTypeAdapter(type, new ParseAPIDeserializer<>())
                .create();
        return GsonConverterFactory.create(build);

    }

}
