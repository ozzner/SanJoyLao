package rsantillanc.sanjoylao.ui.mvp.Order;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.parse.ParsePush;

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
import rsantillanc.sanjoylao.model.StatusModel;
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

    private Handler handler = new Handler(Looper.myLooper());


    @Override
    public void getOrdersFromServer(String orderID, final OnOrderListener listener, final Context c) {
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

                    if (list.isEmpty())
                        listener.onOrdersError(c, "No hay pedidos.");
                    else
                        listener.onLoadDetails(c, list);
                    long l = 0;
                    for (OrderDetailModel detail : list) {
                        l = new OrderDao(c).insertDetail(detail);
                    }
                    Log.e(Const.DEBUG, "onRespons ordersDetail rows affected: " + l);
                } else {
                    listener.onOrdersError(c, "Error loading details");
                    Log.e(Const.DEBUG, "onResponse RESULT ordersDetail: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onOrdersError(c, "Hubo un error al cargar la data.");
                Log.e(Const.DEBUG, "onFailure Throwable: ordersDetail" + t.getMessage());
            }
        });
    }

    private String makeJsonFilter(String orderID) {
        String s = "{\"idOrder\":{\"__type\":\"Pointer\",\"className\":\"Order\",\"objectId\":\"" + orderID + "\"}}";
        return s;
    }


    @Override
    public void addItemToOrder(Context c, PlateSizeModel plateSize, UserModel user, OnOrderListener listener) {
        if (checkIfExistActiveOrder(c)) {
            OrderModel currentOrder = new OrderDao(c).getActiveOrderIfExist(new StatusDao(c).getIdStatusByCode(Const.STATUS_TEMPORAL));

            if (checkIfExistItemDetail(c, currentOrder.getObjectId(), plateSize.getObjectId())) {
                updateCounterItemOrder(c, new OrderDao(c).getOrderDetail(currentOrder.getObjectId(), plateSize.getObjectId()), listener, true);
            } else
                addItemDetail(c, currentOrder.getObjectId(), plateSize, listener);

        } else
            createOrder(c, plateSize, user, listener);
    }

    @Override
    public void makePushNotification() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ParsePush push = new ParsePush();
                push.setChannel(Const.SJL_CHANNEL);
                push.setMessage("Su orden ha esta lista.");
                push.sendInBackground();
            }
        }, 30000);

    }

    private void updateCounterItemOrder(final Context c, final OrderDetailModel orderDetail, final OnOrderListener listener, boolean isIncrement) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<JsonObject> call = retrofit.create(ParseAPIService.class).updateCounterItemOrder(orderDetail.getObjectId(), makeBodyCounter(isIncrement));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    listener.onCounterSuccess(c, "¡Agregado!");
                    Log.e(Const.DEBUG, "onResponse success " + response.body().toString());
                } else {
                    listener.onOrdersError(c, response.message());
                    Log.e(Const.DEBUG, "onResponse error " + response.message());
                }
                int i = updateCounterItemOrderOnDevice(orderDetail, c);
                Log.e(Const.DEBUG, "update local: " + i);
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onOrdersError(c, t.getMessage());
                Log.e(Const.DEBUG, "onFailure error " + t.getMessage());
            }
        });
    }

    private int updateCounterItemOrderOnDevice(OrderDetailModel orderDetail, Context c) {
        return new OrderDao(c).updateCounter(orderDetail);
    }

    private JsonObject makeBodyCounter(boolean isIncrement) {
        JsonObject father = new JsonObject();
        JsonObject son = new JsonObject();
        son.addProperty("__op", "Increment");
        son.addProperty("amount", isIncrement ? 1 : -1);
        father.add("counter", son);
        return father;
    }

    private boolean checkIfExistItemDetail(Context c, String orderID, String plateSizeID) {
        return new OrderDao(c).checkIfExistItemOrder(orderID, plateSizeID);
    }


    private void createOrder(final Context c, final PlateSizeModel plateSize, final UserModel user, final OnOrderListener listener) {
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

    private void addItemDetail(final Context c, final String orderID, final PlateSizeModel plateSize, final OnOrderListener listener) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIRequestOrderDetailModel detailModel = new APIRequestOrderDetailModel();
        detailModel.setIdOrder(new ParsePointerModel(Const.CLASS_ORDER, orderID));
        detailModel.setIdPlateSize(new ParsePointerModel(Const.CLASS_PLATE_SIZE, plateSize.getObjectId()));
        detailModel.setCounter(Const.DEFAULT_COUNTER);

        Call<JsonObject> call = retrofit.create(ParseAPIService.class).createOrderDetail(detailModel);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    final JsonElement orderDetailID = response.body().getAsJsonObject().get(ConstAPI.PARSE_ID);
                    final JsonElement createdAt = response.body().getAsJsonObject().get(ConstAPI.PARSE_CREATED);

                    long row = createOrderDetailOnDevice(c, orderDetailID.getAsString(), new OrderDao(c).getOrderByID(orderID), createdAt.getAsString(), plateSize);
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


    public void callDeleteDetail(final Context c, final OrderDetailModel itemDetail, final OnOrderListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<JsonObject> call = retrofit.create(ParseAPIService.class).deleteDetail(itemDetail.getObjectId());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Log.e(Const.DEBUG, "onResponse correct deleted: " + response.body().toString());
                    listener.onDeleteSuccess("¡Eliminado!");
                    int on = new OrderDao(c).deleteDetail(itemDetail.getObjectId());
                    Log.e(Const.DEBUG, "OrderDeleted on device: " + (on > 0));
                } else {
                    Log.e(Const.DEBUG, "onResponse isSuccess?: " + response.isSuccess());
                    listener.onOrdersError(c, "Server after");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onOrdersError(c, "Error on server.");
                Log.e(Const.DEBUG, "onFailure: " + t.getMessage());
            }
        });
    }

    private long createOrderDetailOnDevice(Context c, String orderDetailID, OrderModel order, String createdAt, PlateSizeModel plateSize) {
        return new OrderDao(c).insertDetail(new OrderDetailModel(orderDetailID, null, order, plateSize, createdAt, createdAt, Const.DEFAULT_COUNTER));
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


    public void getOrdersFrom(final Context c, final OnOrderListener listener) {


        if (countOrder(c) <= 0) {
            listener.onOrdersError(c, "No hay pedidos");
            return;
        }

        if (checkIfExistActiveOrder(c)) {

            if (countDetails(c) >= 1) {

                //From device
                listener.onLoadDetails(c,
                        new OrderDao(c).getOrderDetailsByOrderID(
                                new OrderDao(c).getActiveOrderIfExist(
                                        new StatusDao(c).getIdStatusByCode(Const.STATUS_TEMPORAL))
                                        .getObjectId()));


            } else
                //From server
                getOrdersFromServer(
                        new OrderDao(c).getActiveOrderIfExist(
                                new StatusDao(c).getIdStatusByCode(Const.STATUS_TEMPORAL))
                                .getObjectId(), listener, c);

        } else {
            listener.onLoadDetails(c, new ArrayList<OrderDetailModel>());
        }


    }

    private int countDetails(Context c) {
        return new OrderDao(c).countDetails();
    }

    private int countOrder(Context c) {
        return new OrderDao(c).count();
    }

    public Converter.Factory customConverter(Type type) {

        Gson build = new GsonBuilder()
                .registerTypeAdapter(type, new ParseAPIDeserializer<>())
                .create();
        return GsonConverterFactory.create(build);

    }


    public void sendPayment(final double amount, final OnOrderListener listener) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.paymentCorrect(amount);
            }
        }, 4200);
    }

    public void checkoutOrder(final OnOrderListener listener, final Context c, final OrderModel order) {
        final StatusModel status = new StatusDao(c).getStatusByCode(Const.STATUS_CONFIRMED);

        //Local
        handler.post(new Runnable() {
            @Override
            public void run() {
                int i = new OrderDao(c).updateOrderStatus(status, order);
                if (i > 0)
                    Log.e(Const.DEBUG, "Order Updated correct. ");
                else
                    Log.e(Const.DEBUG, "Order Updated error!. ");

            }
        });

        //Server
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstAPI.PARSE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<JsonObject> call = retrofit.create(ParseAPIService.class).updateOrderStatus(buildBodyJson(status), order.getObjectId());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                if (response != null) {
                    Log.e(Const.DEBUG, "Order Updated server OK!. ");
                    listener.orderCheckoutSuccess("Correct!");
                } else {
                    listener.errorUpdating("Error inténtelo de nuevo");
                    Log.e(Const.DEBUG, "Order Updated server error!. ");
                }

            }

            @Override
            public void onFailure(Throwable t) {
                listener.errorUpdating("Inténtelo más tarde.");
            }
        });


    }

    private JsonObject buildBodyJson(StatusModel status) {
        JsonObject json = new JsonObject();
        json.addProperty("idStatus", status.getObjectId());
        return json;
    }

    public void saveAllChanges(final double currentAmount, final List<OrderDetailModel> details, final Context c, final OnSaveListener save) {

        new Thread(new Runnable() {

            @Override
            public void run() {

                if (details.size() > 0) {

                    int x = 0;
                    for (OrderDetailModel detail : details)
                        x += new OrderDao(c).updateCounter(detail);
                    Log.e(Const.DEBUG, "update counters: " + x);

                    OrderModel currentOrder = details.get(0).getOrder();
                    currentOrder.setPrice(currentAmount);
                    int i = new OrderDao(c).updatePrice(currentOrder);
                    if (i > 0)
                        save.onSuccess();

                    Log.e(Const.DEBUG, "update price: " + i);
                }

            }
        }).start();
    }

    public interface OnSaveListener {
        void onSuccess();
    }
}
